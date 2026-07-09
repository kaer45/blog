
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
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import Layout from '@/components/Layout.vue'
import ArticleCard from '@/components/ArticleCard.vue'
import Sidebar from '@/components/Sidebar.vue'
import { articles, categories } from '@/data/mockData'

const route = useRoute()
const categoryId = computed(() => Number(route.params.id))

const category = computed(() => {
  return categories.find(c => c.id === categoryId.value)
})

const categoryArticles = computed(() => {
  return articles.filter(a => a.categoryId === categoryId.value && a.isPublished)
})
</script>
