<template>
  <Layout>
    <div class="flex flex-col lg:flex-row gap-8">
      <div class="flex-1">
        <div class="mb-8">
          <h1 class="page-title">{{ category?.name }}</h1>
          <p class="text-gray-600">{{ category?.description }}</p>
        </div>

        <div v-if="categoryArticles.length > 0">
          <ArticleCard 
            v-for="article in categoryArticles" 
            :key="article.id" 
            :article="article"
            :category-name="category?.name || '未分类'"
          />
        </div>

        <div v-else class="text-center py-16">
          <svg class="mx-auto h-16 w-16 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
          </svg>
          <h3 class="text-lg font-medium text-gray-900 mb-2">暂无文章</h3>
          <p class="text-gray-500">该分类下还没有文章</p>
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

      <div class="lg:w-80">
        <Sidebar />
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import Layout from '@/components/Layout.vue'
import ArticleCard from '@/components/ArticleCard.vue'
import Sidebar from '@/components/Sidebar.vue'
import { categoryApi, articleApi } from '@/api/index'

const route = useRoute()
const categoryId = computed(() => Number(route.params.id))

const category = ref(null)
const categoryArticles = ref([])
const currentPage = ref(1)
const totalPages = ref(1)
const total = ref(0)
const pageSize = ref(5)
const jumpPage = ref(1)

const loadCategory = async () => {
  try {
    const response = await categoryApi.getById(categoryId.value)
    if (response.code === 200) {
      category.value = response.data
    }
  } catch (error) {
    console.error('加载分类失败:', error)
  }
}

const loadArticles = async () => {
  try {
    const response = await articleApi.list({ categoryId: categoryId.value, page: currentPage.value, size: pageSize.value })
    if (response.code === 200) {
      categoryArticles.value = response.data?.data || []
      totalPages.value = response.data?.totalPages || 1
      total.value = response.data?.total || 0
    }
  } catch (error) {
    console.error('加载文章失败:', error)
  }
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

onMounted(() => {
  loadCategory()
  loadArticles()
})
</script>