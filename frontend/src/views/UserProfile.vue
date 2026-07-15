<template>
  <Layout>
    <div class="max-w-4xl mx-auto">
      <div class="card p-8 mb-6">
        <div class="flex flex-col md:flex-row items-center gap-6">
          <div class="relative">
            <img 
              :src="(user.avatar ? user.avatar : 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + user.username)" 
              :alt="user.nickname" 
              class="w-24 h-24 rounded-full object-cover"
            />
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
            </div>
          </div>
        </div>

        <div class="grid grid-cols-3 gap-4 mt-8">
          <div class="text-center">
            <div class="text-2xl font-bold text-primary-600">{{ total }}</div>
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
        </div>
      </div>

      <div class="space-y-4">
        <div v-for="article in userArticles" :key="article.id" class="card p-4">
          <div class="flex items-center space-x-3 mb-3">
            <router-link :to="`/categories/${article.categoryId}`" class="tag">{{ getCategoryName(article.categoryId) }}</router-link>
            <span class="text-sm text-gray-500">{{ formatDate(article.createdAt) }}</span>
          </div>
          <router-link :to="`/article/${article.id}`" class="block">
            <h3 class="font-semibold text-lg text-gray-900 hover:text-primary-600">{{ article.title }}</h3>
          </router-link>
          <p class="text-gray-600 mt-2 line-clamp-2">{{ article.summary }}</p>
          <div class="flex items-center space-x-4 mt-3 text-sm text-gray-500">
            <span>{{ article.viewCount || 0 }} 阅读</span>
            <span>{{ article.commentCount || 0 }} 评论</span>
          </div>
        </div>
        <div v-if="userArticles.length === 0" class="text-center py-16 text-gray-500">
          暂无文章
        </div>
      </div>

      <div v-if="total > 0" class="flex flex-wrap items-center justify-center mt-8 space-x-4">
        <span class="text-gray-600">共 {{ total }} 篇文章</span>
        
        <div class="flex items-center space-x-2">
          <span class="text-gray-600">每页</span>
          <select 
            v-model="pageSize" 
            @change="handlePageSizeChange"
            class="border border-gray-300 rounded-md px-3 py-1.5 text-sm focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
          >
            <option :value="5">5篇</option>
            <option :value="10">10篇</option>
            <option :value="20">20篇</option>
          </select>
        </div>

        <button 
          @click="prevPage" 
          :disabled="currentPage <= 1"
          class="btn btn-secondary disabled:opacity-50 disabled:cursor-not-allowed"
        >
          上一页
        </button>
        
        <span class="text-gray-600">第 {{ currentPage }} / {{ totalPages }} 页</span>
        
        <button 
          @click="nextPage" 
          :disabled="currentPage >= totalPages"
          class="btn btn-secondary disabled:opacity-50 disabled:cursor-not-allowed"
        >
          下一页
        </button>

        <div class="flex items-center space-x-2">
          <span class="text-gray-600">跳转到</span>
          <input 
            v-model.number="jumpPage" 
            type="number" 
            :min="1" 
            :max="totalPages"
            class="border border-gray-300 rounded-md px-3 py-1.5 text-sm w-20 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
            placeholder="页码"
            @keyup.enter="jumpToPage"
          />
          <button 
            @click="jumpToPage" 
            class="btn btn-secondary"
          >
            跳转
          </button>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import Layout from '@/components/Layout.vue'
import { articleApi, userApi, categoryApi, followApi } from '@/api/index'

const route = useRoute()
const username = ref(route.params.username)
const user = ref({})
const userArticles = ref([])
const categories = ref([])
const stats = ref({ followerCount: 0, followingCount: 0 })
const currentPage = ref(1)
const totalPages = ref(1)
const total = ref(0)
const pageSize = ref(5)
const jumpPage = ref(1)

onMounted(async () => {
  await loadUser()
  await loadArticles()
  await loadCategories()
  await loadStats()
})

const loadUser = async () => {
  try {
    const response = await userApi.getByUsername(username.value)
    if (response.code === 200) {
      user.value = response.data || {}
    }
  } catch (error) {
    console.error('加载用户资料失败:', error)
  }
}

const loadArticles = async () => {
  try {
    const response = await articleApi.list({ authorId: user.value.id, page: currentPage.value, size: pageSize.value })
    if (response.code === 200) {
      userArticles.value = response.data?.data || []
      totalPages.value = response.data?.totalPages || 1
      total.value = response.data?.total || 0
    }
  } catch (error) {
    console.error('加载文章失败:', error)
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

const loadStats = async () => {
  try {
    const response = await followApi.getStats(user.value.id)
    if (response.code === 200) {
      stats.value = response.data || { followerCount: 0, followingCount: 0 }
    }
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

const getCategoryName = (categoryId) => {
  const category = categories.value.find(c => c.id === categoryId)
  return category ? category.name : '未分类'
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN')
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadArticles()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadArticles()
  }
}

const handlePageSizeChange = () => {
  currentPage.value = 1
  loadArticles()
}

const jumpToPage = () => {
  const targetPage = parseInt(jumpPage.value)
  if (targetPage >= 1 && targetPage <= totalPages.value) {
    currentPage.value = targetPage
    loadArticles()
  } else {
    alert(`请输入1到${totalPages.value}之间的页码`)
  }
}
</script>