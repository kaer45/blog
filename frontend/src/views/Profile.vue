<template>
  <Layout>
    <div class="max-w-4xl mx-auto">
      <div class="card p-8 mb-6">
        <div class="flex flex-col md:flex-row items-center gap-6">
          <div class="relative">
            <img 
              :src="user.avatar || 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + user.username" 
              :alt="user.nickname" 
              class="w-24 h-24 rounded-full object-cover"
            />
            <label class="absolute bottom-0 right-0 bg-primary-600 text-white p-2 rounded-full hover:bg-primary-700 transition-colors cursor-pointer">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
              </svg>
              <input type="file" accept="image/*" class="hidden" @change="handleAvatarUpload" />
            </label>
          </div>

          <div class="flex-1 text-center md:text-left">
            <h1 class="text-2xl font-bold">{{ user.nickname || user.username }}</h1>
            <p class="text-gray-500 mt-1">{{ user.username }}</p>
            <p class="text-gray-600 mt-3">{{ user.bio || '暂无简介' }}</p>
            <div class="flex items-center space-x-4 mt-4 justify-center md:justify-start">
              <a v-if="user.githubUrl" :href="user.githubUrl" target="_blank" class="text-gray-400 hover:text-primary-600">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                  <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.03 1.531 1.03.892 1.529 2.341 1.087 2.91.831.092-.646.35-1.086.636-1.336-2.22-.253-4.555-1.11-4.555-4.943 0-1.091.39-1.984 1.029-2.683-.103-.253-.446-1.27.098-2.647 0 0 .84-.269 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.295 2.747-1.026 2.747-1.026.546 1.377.203 2.394.1 2.647.64.699 1.028 1.592 1.028 2.683 0 3.842-2.339 4.687-4.566 4.935.359.309.678.919.678 1.852 0 1.336-.012 2.415-.012 2.743 0 .268.18.578.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" clip-rule="evenodd"></path>
                </svg>
              </a>
              <a href="#" class="text-gray-400 hover:text-primary-600">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 2C6.477 2 2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.879V14.89h-2.54V12h2.54V9.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.989C18.343 21.129 22 16.99 22 12c0-5.523-4.477-10-10-10z"></path>
                </svg>
              </a>
            </div>
          </div>

          <div class="flex space-x-2">
            <router-link to="/settings" class="btn btn-secondary">编辑资料</router-link>
          </div>
        </div>

        <div class="grid grid-cols-4 gap-4 mt-8">
          <div class="text-center">
            <div class="text-2xl font-bold text-primary-600">{{ userArticles.length }}</div>
            <div class="text-sm text-gray-500">文章</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-primary-600">{{ stats.followerCount }}</div>
            <div class="text-sm text-gray-500">粉丝</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-primary-600">{{ stats.followingCount }}</div>
            <div class="text-sm text-gray-500">关注</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-primary-600">{{ likedArticles.length }}</div>
            <div class="text-sm text-gray-500">收藏</div>
          </div>
        </div>
      </div>

      <div class="flex border-b mb-6">
        <button 
          @click="activeTab = 'articles'"
          :class="['px-4 py-2 font-medium', activeTab === 'articles' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
        >
          文章 ({{ userArticles.length }})
        </button>
        <button 
          @click="activeTab = 'drafts'"
          :class="['px-4 py-2 font-medium', activeTab === 'drafts' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
        >
          草稿 ({{ drafts.length }})
        </button>
        <button 
          @click="activeTab = 'likes'"
          :class="['px-4 py-2 font-medium', activeTab === 'likes' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
        >
          收藏 ({{ likedArticles.length }})
        </button>
      </div>

      <div v-if="activeTab === 'articles'" class="space-y-4">
        <div v-for="article in userArticles" :key="article.id" class="card p-4 flex items-center justify-between">
          <div class="flex-1">
            <h3 class="font-semibold text-gray-900">{{ article.title }}</h3>
            <div class="flex items-center space-x-4 mt-2 text-sm text-gray-500">
              <span>{{ formatDate(article.createdAt) }}</span>
              <span>{{ article.viewCount || 0 }} 阅读</span>
              <span>{{ article.commentCount || 0 }} 评论</span>
            </div>
          </div>
          <div class="flex items-center space-x-2">
            <router-link :to="`/write/${article.id}`" class="btn btn-secondary text-sm">编辑</router-link>
            <button @click="deleteArticle(article.id)" class="btn btn-danger text-sm">删除</button>
          </div>
        </div>
        <div v-if="userArticles.length === 0" class="text-center py-16 text-gray-500">
          暂无文章
        </div>
      </div>

      <div v-if="activeTab === 'drafts'" class="space-y-4">
        <div v-for="draft in drafts" :key="draft.id" class="card p-4 flex items-center justify-between">
          <div class="flex-1">
            <h3 class="font-semibold text-gray-900">{{ draft.title || '无标题' }}</h3>
            <div class="flex items-center space-x-4 mt-2 text-sm text-gray-500">
              <span>{{ formatDate(draft.createdAt) }}</span>
              <span class="tag">草稿</span>
            </div>
          </div>
          <div class="flex items-center space-x-2">
            <router-link :to="`/write/${draft.id}`" class="btn btn-secondary text-sm">编辑</router-link>
            <button @click="deleteArticle(draft.id)" class="btn btn-danger text-sm">删除</button>
          </div>
        </div>
        <div v-if="drafts.length === 0" class="text-center py-16 text-gray-500">
          暂无草稿
        </div>
      </div>

      <div v-if="activeTab === 'likes'" class="space-y-4">
        <div v-for="article in likedArticles" :key="article.id" class="card p-4 flex items-center justify-between">
          <div class="flex-1">
            <h3 class="font-semibold text-gray-900">{{ article.title }}</h3>
            <div class="flex items-center space-x-4 mt-2 text-sm text-gray-500">
              <span>{{ formatDate(article.createdAt) }}</span>
              <span>{{ article.viewCount || 0 }} 阅读</span>
            </div>
          </div>
          <div class="flex items-center space-x-2">
            <router-link :to="`/article/${article.id}`" class="btn btn-secondary text-sm">查看</router-link>
            <button @click="unlikeArticle(article.id)" class="btn btn-outline text-sm text-red-500">取消收藏</button>
          </div>
        </div>
        <div v-if="likedArticles.length === 0" class="text-center py-16 text-gray-500">
          暂无收藏
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Layout from '@/components/Layout.vue'
import { useUserStore } from '@/stores/user'
import { articleApi, userApi, uploadApi, followApi, articleLikeApi } from '@/api/index'

const router = useRouter()
const userStore = useUserStore()
const activeTab = ref('articles')
const userArticles = ref([])
const drafts = ref([])
const likedArticles = ref([])
const stats = ref({ followerCount: 0, followingCount: 0 })
const loading = ref(false)

const user = computed(() => userStore.user)

onMounted(async () => {
  await loadProfile()
  await loadArticles()
  await loadDrafts()
  await loadLikedArticles()
  await loadStats()
})

const loadProfile = async () => {
  try {
    const profileResponse = await userApi.getProfile()
    if (profileResponse.code === 200) {
      userStore.login(profileResponse.data)
    }
  } catch (error) {
    console.error('加载用户资料失败:', error)
  }
}

const loadArticles = async () => {
  try {
    const response = await articleApi.list({ authorId: userStore.user.id })
    if (response.code === 200) {
      userArticles.value = response.data || []
    }
  } catch (error) {
    console.error('加载文章失败:', error)
  }
}

const loadDrafts = async () => {
  try {
    const response = await articleApi.list({ draft: true })
    if (response.code === 200) {
      drafts.value = response.data || []
    }
  } catch (error) {
    console.error('加载草稿失败:', error)
  }
}

const loadLikedArticles = async () => {
  try {
    const response = await articleLikeApi.getLikedArticles()
    if (response.code === 200) {
      likedArticles.value = response.data || []
    }
  } catch (error) {
    console.error('加载收藏失败:', error)
  }
}

const loadStats = async () => {
  try {
    const response = await followApi.getStats(userStore.user.id)
    if (response.code === 200) {
      stats.value = response.data || { followerCount: 0, followingCount: 0 }
    }
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

const handleAvatarUpload = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  loading.value = true
  try {
    const response = await uploadApi.upload(file)
    if (response.code === 200) {
      await userApi.updateProfile({ avatar: response.data })
      await loadProfile()
      alert('头像上传成功')
    }
  } catch (error) {
    console.error('头像上传失败:', error)
    alert('头像上传失败')
  } finally {
    loading.value = false
  }
}

const deleteArticle = async (articleId) => {
  if (!confirm('确定要删除这篇文章吗？')) return
  
  try {
    const response = await articleApi.delete(articleId)
    if (response.code === 200) {
      await loadArticles()
      await loadDrafts()
      alert('删除成功')
    }
  } catch (error) {
    console.error('删除文章失败:', error)
    alert('删除失败')
  }
}

const unlikeArticle = async (articleId) => {
  try {
    const response = await articleLikeApi.unlike(articleId)
    if (response.code === 200) {
      await loadLikedArticles()
      alert('取消收藏成功')
    }
  } catch (error) {
    console.error('取消收藏失败:', error)
    alert('取消收藏失败')
  }
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN')
}
</script>