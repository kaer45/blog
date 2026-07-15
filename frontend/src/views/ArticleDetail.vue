
<template>
  <Layout>
    <div class="flex flex-col lg:flex-row gap-8">
      <div class="flex-1">
        <div v-if="article" class="card p-8 mb-6">
          <div class="flex items-center justify-between mb-4">
            <span class="tag">{{ getCategoryName(article.categoryId) }}</span>
            <span class="text-sm text-gray-500">{{ formatDate(article.createdAt) }}</span>
          </div>
          
          <h1 class="text-3xl font-bold text-gray-900 mb-6">{{ article.title }}</h1>
          
          <div class="flex items-center space-x-4 mb-6">
            <router-link :to="`/user/${article.authorName}`">
              <img 
                :src="authorAvatar" 
                :alt="article.authorName" 
                class="w-12 h-12 rounded-full cursor-pointer hover:opacity-80 transition-opacity"
              />
            </router-link>
            <div>
              <router-link :to="`/user/${article.authorName}`" class="font-semibold hover:text-primary-600">
                {{ article.authorNickname || article.authorName || '作者' }}
              </router-link>
              <p class="text-sm text-gray-500">{{ formatDate(article.createdAt) }}</p>
            </div>
          </div>

          <div class="markdown-body" v-html="renderedContent"></div>

          <div class="flex items-center justify-between mt-8 pt-6 border-t">
            <div class="flex items-center space-x-6">
              <button @click="toggleLike" class="flex items-center text-gray-500 hover:text-red-500 transition-colors">
                <svg class="w-5 h-5 mr-1" :class="{ 'fill-current text-red-500': isLiked }" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                </svg>
                <span>{{ likeCount }}</span>
              </button>
              <span class="flex items-center text-gray-500">
                <svg class="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"></path>
                </svg>
                <span>{{ article.commentCount || 0 }}</span>
              </span>
              <span v-if="isLoggedIn" class="flex items-center text-gray-500">
                <svg class="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
                </svg>
                <span>{{ article.viewCount || 0 }}</span>
              </span>
            </div>
            <div class="flex items-center space-x-2">
              <button @click="shareArticle" class="btn btn-outline flex items-center">
                <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z"></path>
                </svg>
                分享
              </button>
              <button @click="toggleFavorite" class="btn btn-outline flex items-center" :class="{ 'bg-yellow-50 text-yellow-600': isFavorited }">
                <svg class="w-4 h-4 mr-1" :class="{ 'fill-current': isFavorited }" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                </svg>
                {{ isFavorited ? '已收藏' : '收藏' }}
              </button>
            </div>
          </div>
        </div>

        <div class="card p-6 mb-6">
          <h3 class="text-xl font-bold mb-4">评论 ({{ comments.length }})</h3>
          
          <div v-if="comments.length > 0" class="space-y-4">
            <div v-for="comment in comments" :key="comment.id" class="comment">
              <div class="flex items-center space-x-3 mb-2">
                <router-link :to="`/user/${comment.authorName}`">
                  <img 
                    :src="getCommentAvatar(comment)" 
                    :alt="comment.authorName" 
                    class="w-8 h-8 rounded-full cursor-pointer hover:opacity-80 transition-opacity"
                  />
                </router-link>
                <div>
                  <router-link :to="`/user/${comment.authorName}`" class="font-semibold hover:text-primary-600">
                    {{ comment.authorNickname || comment.authorName }}
                  </router-link>
                  <span class="text-sm text-gray-500 ml-2">{{ formatDate(comment.createdAt) }}</span>
                </div>
              </div>
              <p class="text-gray-700">{{ comment.content }}</p>
              <div class="flex items-center space-x-4 mt-2">
                <button @click="showReplyBox(comment, comment)" class="text-sm text-gray-500 hover:text-primary-600">回复</button>
                <button @click="toggleCommentLike(comment)" class="flex items-center text-sm text-gray-500 hover:text-red-500">
                  <svg class="w-4 h-4 mr-1" :class="{ 'fill-current text-red-500': commentLikedMap[comment.id] }" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                  </svg>
                  <span>{{ comment.likeCount || 0 }}</span>
                </button>
                <button v-if="comment.children && comment.children.length > 0" @click="toggleCollapse(comment.id)" class="text-sm text-primary-600 hover:text-primary-700">
                  {{ collapsedMap[comment.id] ? `展开${comment.children.length}条回复` : '收起回复' }}
                </button>
              </div>
              <div v-if="replyTargetComment?.id === comment.id" class="mt-3 pl-10">
                <textarea 
                  v-model="replyContent"
                  class="form-input textarea"
                  :placeholder="`回复 ${comment.authorNickname || comment.authorName}...`"
                ></textarea>
                <div class="flex justify-end space-x-2 mt-2">
                  <button @click="cancelReply" class="btn btn-outline">取消</button>
                  <button @click="submitReply" class="btn btn-primary">回复</button>
                </div>
              </div>
              <div v-if="comment.children && comment.children.length > 0 && !collapsedMap[comment.id]" class="mt-4 ml-6 pl-4 border-l-2 border-gray-100 space-y-3">
                <div v-for="reply in comment.children" :key="reply.id">
                  <div class="flex items-center space-x-3 mb-2">
                    <router-link :to="`/user/${reply.authorName}`">
                      <img 
                        :src="getCommentAvatar(reply)" 
                        :alt="reply.authorName" 
                        class="w-7 h-7 rounded-full cursor-pointer hover:opacity-80 transition-opacity"
                      />
                    </router-link>
                    <div class="flex items-center flex-wrap">
                      <router-link :to="`/user/${reply.authorName}`" class="font-semibold hover:text-primary-600">
                        {{ reply.authorNickname || reply.authorName }}
                      </router-link>
                      <span v-if="reply.replyToNickname || reply.replyToName" class="text-sm text-gray-500 mx-1">
                        回复 <span class="text-primary-600">@{{ reply.replyToNickname || reply.replyToName }}</span>
                      </span>
                      <span class="text-sm text-gray-500 ml-2">{{ formatDate(reply.createdAt) }}</span>
                    </div>
                  </div>
                  <p class="text-gray-700">{{ reply.content }}</p>
                  <div class="flex items-center space-x-4 mt-2">
                    <button @click="showReplyBox(reply, comment)" class="text-sm text-gray-500 hover:text-primary-600">回复</button>
                    <button @click="toggleCommentLike(reply)" class="flex items-center text-sm text-gray-500 hover:text-red-500">
                      <svg class="w-4 h-4 mr-1" :class="{ 'fill-current text-red-500': commentLikedMap[reply.id] }" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                      </svg>
                      <span>{{ reply.likeCount || 0 }}</span>
                    </button>
                  </div>
                  <div v-if="replyTargetComment?.id === reply.id" class="mt-3 pl-8">
                    <textarea 
                      v-model="replyContent"
                      class="form-input textarea"
                      :placeholder="`回复 ${reply.authorNickname || reply.authorName}...`"
                    ></textarea>
                    <div class="flex justify-end space-x-2 mt-2">
                      <button @click="cancelReply" class="btn btn-outline">取消</button>
                      <button @click="submitReply" class="btn btn-primary">回复</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-else class="text-center py-8 text-gray-500">
            暂无评论，快来抢沙发吧！
          </div>

          <div v-if="isLoggedIn" class="mt-6 pt-6 border-t">
            <h4 class="font-semibold mb-4">发表评论</h4>
            <textarea 
              v-model="commentContent"
              class="form-input textarea"
              placeholder="写下你的评论..."
            ></textarea>
            <button @click="submitComment" class="btn btn-primary mt-4">
              提交评论
            </button>
          </div>
          <div v-else class="mt-6 pt-6 border-t text-center">
            <router-link to="/login" class="text-primary-600">登录后发表评论</router-link>
          </div>
        </div>
      </div>

      <div class="lg:w-80">
        <Sidebar />
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { marked } from 'marked'
import hljs from 'highlight.js'
import katex from 'katex'
import Layout from '@/components/Layout.vue'
import Sidebar from '@/components/Sidebar.vue'
import { articleApi, commentApi, categoryApi, articleLikeApi, articleFavoriteApi, commentLikeApi } from '@/api/index'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()
const articleId = ref(Number(route.params.id))

const article = ref(null)
const comments = ref([])
const categories = ref([])
const loading = ref(false)

marked.setOptions({
  highlight: function(code, lang) {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(code, { language: lang }).value
    }
    return hljs.highlightAuto(code).value
  },
  breaks: true,
  gfm: true
})

const renderMath = (html) => {
  html = html.replace(/\$\$(.+?)\$\$/g, (_, formula) => {
    try {
      return katex.renderToString(formula.trim(), {
        throwOnError: false,
        displayMode: true
      })
    } catch (e) {
      return '$$' + formula + '$$'
    }
  })
  html = html.replace(/\$([^$]+)\$/g, (_, formula) => {
    try {
      return katex.renderToString(formula.trim(), {
        throwOnError: false,
        displayMode: false
      })
    } catch (e) {
      return '$' + formula + '$'
    }
  })
  return html
}

const isLoggedIn = computed(() => userStore.isLoggedIn)

const authorAvatar = computed(() => {
  if (article.value?.authorAvatar) {
    return article.value.authorAvatar
  }
  return 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + (article.value?.authorName || 'User')
})

const renderedContent = computed(() => {
  if (!article.value) return ''
  let html = marked(article.value.content)
  html = renderMath(html)
  return html
})

const isLiked = ref(false)
const likeCount = ref(0)
const isFavorited = ref(false)
const commentContent = ref('')
const commentLikedMap = ref({})
const replyTargetComment = ref(null)
const replyRootComment = ref(null)
const replyContent = ref('')
const collapsedMap = ref({})

const loadArticle = async () => {
  loading.value = true
  try {
    const response = await articleApi.getById(articleId.value)
    if (response.code === 200) {
      article.value = response.data
    }
    
    try {
      const countResponse = await articleLikeApi.countLikes(articleId.value)
      likeCount.value = countResponse.data || 0
    } catch (e) {
      console.error('加载点赞数失败:', e)
      likeCount.value = article.value?.likeCount || 0
    }
    
    if (isLoggedIn.value) {
      try {
        const likedResponse = await articleLikeApi.checkLiked(articleId.value)
        isLiked.value = likedResponse.data || false
        const favoritedResponse = await articleFavoriteApi.checkFavorited(articleId.value)
        isFavorited.value = favoritedResponse.data || false
      } catch (e) {
        console.error('加载点赞/收藏状态失败:', e)
      }
    }
  } catch (error) {
    console.error('加载文章失败:', error)
  } finally {
    loading.value = false
  }
}

const loadComments = async () => {
  try {
    const response = await commentApi.listByArticle(articleId.value)
    if (response.code === 200) {
      comments.value = response.data || []
      
      if (isLoggedIn.value) {
        for (const comment of comments.value) {
          try {
            const likedResponse = await commentLikeApi.checkLiked(comment.id)
            commentLikedMap.value[comment.id] = likedResponse.data || false
          } catch (e) {
            commentLikedMap.value[comment.id] = false
          }
          if (comment.children) {
            for (const reply of comment.children) {
              try {
                const likedResponse = await commentLikeApi.checkLiked(reply.id)
                commentLikedMap.value[reply.id] = likedResponse.data || false
              } catch (e) {
                commentLikedMap.value[reply.id] = false
              }
            }
          }
        }
      }
    }
  } catch (error) {
    console.error('加载评论失败:', error)
  }
}

const loadCategories = async () => {
  try {
    const response = await categoryApi.list()
    if (response.code === 200) {
      categories.value = response.data || []
    }
  } catch (error) {
    console.error('加载分类失败:', error)
  }
}

const getCategoryName = (categoryId) => {
  const category = categories.value.find(c => c.id === categoryId)
  return category ? category.name : '未分类'
}

const getCommentAvatar = (comment) => {
  if (comment.authorAvatar) {
    return comment.authorAvatar
  }
  return 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + (comment.authorName || 'Commenter')
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

const toggleLike = async () => {
  if (!isLoggedIn.value) {
    alert('请先登录')
    return
  }
  try {
    if (isLiked.value) {
      await articleLikeApi.unlike(articleId.value)
      likeCount.value -= 1
    } else {
      await articleLikeApi.like(articleId.value)
      likeCount.value += 1
    }
    isLiked.value = !isLiked.value
  } catch (error) {
    console.error('点赞失败:', error)
    alert('点赞失败')
  }
}

const shareArticle = async () => {
  const url = window.location.href
  try {
    await navigator.clipboard.writeText(url)
    alert('链接已复制到剪贴板！')
  } catch (error) {
    const textarea = document.createElement('textarea')
    textarea.value = url
    document.body.appendChild(textarea)
    textarea.select()
    document.execCommand('copy')
    document.body.removeChild(textarea)
    alert('链接已复制到剪贴板！')
  }
}

const toggleFavorite = async () => {
  if (!isLoggedIn.value) {
    alert('请先登录')
    return
  }
  try {
    if (isFavorited.value) {
      await articleFavoriteApi.unfavorite(articleId.value)
    } else {
      await articleFavoriteApi.favorite(articleId.value)
    }
    isFavorited.value = !isFavorited.value
  } catch (error) {
    console.error('收藏失败:', error)
    alert('收藏失败')
  }
}

const submitComment = async () => {
  if (!commentContent.value.trim()) {
    alert('请输入评论内容')
    return
  }
  
  try {
    const response = await commentApi.create({
      articleId: articleId.value,
      authorId: userStore.user.id,
      content: commentContent.value
    })
    
    if (response.code === 200) {
      alert('评论提交成功！')
      commentContent.value = ''
      loadComments()
    } else {
      alert(response.message || '评论失败')
    }
  } catch (error) {
    alert('评论失败，请检查网络')
  }
}

const toggleCommentLike = async (comment) => {
  if (!isLoggedIn.value) {
    alert('请先登录')
    return
  }
  try {
    if (commentLikedMap.value[comment.id]) {
      await commentLikeApi.unlike(comment.id)
      if (comment.likeCount) comment.likeCount -= 1
    } else {
      await commentLikeApi.like(comment.id)
      comment.likeCount = (comment.likeCount || 0) + 1
    }
    commentLikedMap.value[comment.id] = !commentLikedMap.value[comment.id]
  } catch (error) {
    console.error('评论点赞失败:', error)
    alert('点赞失败')
  }
}

const showReplyBox = (comment, rootComment) => {
  replyTargetComment.value = comment
  replyRootComment.value = rootComment
  replyContent.value = ''
}

const cancelReply = () => {
  replyTargetComment.value = null
  replyRootComment.value = null
  replyContent.value = ''
}

const toggleCollapse = (commentId) => {
  collapsedMap.value[commentId] = !collapsedMap.value[commentId]
}

const submitReply = async () => {
  if (!replyContent.value.trim()) {
    alert('请输入回复内容')
    return
  }
  
  try {
    const response = await commentApi.create({
      articleId: articleId.value,
      authorId: userStore.user.id,
      parentId: replyRootComment.value.id,
      replyToUserId: replyTargetComment.value.authorId,
      content: replyContent.value
    })
    
    if (response.code === 200) {
      alert('回复提交成功！')
      cancelReply()
      loadComments()
    } else {
      alert(response.message || '回复失败')
    }
  } catch (error) {
    alert('回复失败，请检查网络')
  }
}

onMounted(() => {
  loadArticle()
  loadComments()
  loadCategories()
})
</script>
