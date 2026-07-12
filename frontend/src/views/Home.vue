
<template>
  <Layout>
    <div class="flex flex-col lg:flex-row gap-8">
      <div class="flex-1">
        <div class="mb-8">
          <h1 class="text-4xl font-bold text-gray-900 mb-2">欢迎来到我的博客</h1>
          <p class="text-gray-600 text-lg">记录生活点滴，分享技术心得</p>
        </div>

        <div v-if="articles.length > 0">
          <ArticleCard 
            v-for="article in articles" 
            :key="article.id" 
            :article="article"
            :category-name="getCategoryName(article.categoryId)"
          />
        </div>

        <div v-else class="text-center py-16">
          <svg class="mx-auto h-16 w-16 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
          </svg>
          <h3 class="text-lg font-medium text-gray-900 mb-2">暂无文章</h3>
          <p class="text-gray-500">快去写一篇文章吧！</p>
        </div>

        <div class="flex items-center justify-center mt-8 space-x-4">
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
        </div>
      </div>

      <div class="lg:w-80">
        <Sidebar />
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Layout from '@/components/Layout.vue'
import ArticleCard from '@/components/ArticleCard.vue'
import Sidebar from '@/components/Sidebar.vue'
import { articleApi, categoryApi } from '@/api/index'

const articles = ref([])
const categories = ref([])
const currentPage = ref(1)
const totalPages = ref(1)
const loading = ref(false)

const loadArticles = async () => {
  loading.value = true
  try {
    const response = await articleApi.list()
    if (response.code === 200) {
      articles.value = response.data || []
      totalPages.value = Math.ceil(articles.value.length / 5)
    }
  } catch (error) {
    console.error('加载文章失败:', error)
  } finally {
    loading.value = false
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

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

onMounted(() => {
  loadArticles()
  loadCategories()
})
</script>
