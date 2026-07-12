
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
    const response = await articleApi.list({ categoryId: categoryId.value })
    if (response.code === 200) {
      categoryArticles.value = response.data || []
    }
  } catch (error) {
    console.error('加载文章失败:', error)
  }
}

onMounted(() => {
  loadCategory()
  loadArticles()
})
</script>
