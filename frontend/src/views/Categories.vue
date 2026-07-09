
<template>
  <Layout>
    <div class="flex flex-col lg:flex-row gap-8">
      <div class="flex-1">
        <h1 class="page-title">文章分类</h1>
        
        <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
          <div 
            v-for="category in categories" 
            :key="category.id" 
            class="card p-6 cursor-pointer hover:shadow-md transition-shadow"
            @click="goToCategory(category.id)"
          >
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-lg font-bold">{{ category.name }}</h3>
              <span class="text-2xl">{{ category.icon }}</span>
            </div>
            <p class="text-gray-600 text-sm mb-4">{{ category.description }}</p>
            <div class="flex items-center justify-between">
              <span class="text-sm text-gray-500">{{ category.articleCount }} 篇文章</span>
              <svg class="w-5 h-5 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
              </svg>
            </div>
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
import { useRouter } from 'vue-router'
import Layout from '@/components/Layout.vue'
import Sidebar from '@/components/Sidebar.vue'
import { categories } from '@/data/mockData'

const router = useRouter()

const goToCategory = (categoryId) => {
  router.push(`/categories/${categoryId}`)
}
</script>
