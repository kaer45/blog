
<template>
  <aside class="space-y-6">
    <div class="card p-6">
      <h3 class="text-lg font-bold mb-4">关于我</h3>
      <div class="flex flex-col items-center text-center">
        <img 
          :src="avatar" 
          alt="Avatar" 
          class="w-20 h-20 rounded-full mb-3"
        />
        <h4 class="font-semibold">{{ nickname }}</h4>
        <p class="text-gray-500 text-sm mt-2">{{ bio }}</p>
        <div class="flex space-x-4 mt-4">
          <a href="#" class="text-gray-400 hover:text-primary-600">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
              <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.7-2.782.604-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.03 1.531 1.03.892 1.529 2.341 1.087 2.91.831.092-.646.35-1.086.636-1.336-2.22-.253-4.555-1.11-4.555-4.943 0-1.091.39-1.984 1.029-2.683-.103-.253-.446-1.27.098-2.647 0 0 .84-.268 2.75 1.026A9.578 9.578 0 0112 6.844c.85.004 1.705.114 2.504.336 1.909-1.294 2.747-1.026 2.747-1.026.546 1.377.203 2.394.1 2.647.64.699 1.028 1.592 1.028 2.683 0 3.842-2.339 4.687-4.566 4.935.359.309.678.919.678 1.852 0 1.336-.012 2.415-.012 2.743 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" clip-rule="evenodd"></path>
            </svg>
          </a>
          <a href="#" class="text-gray-400 hover:text-primary-600">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
              <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"></path>
            </svg>
          </a>
          <a href="#" class="text-gray-400 hover:text-primary-600">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
              <path d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.07-4.85.07-3.204 0-3.584-.012-4.849-.07-3.26-.149-4.771-1.699-4.919-4.92-.058-1.265-.07-1.644-.07-4.849 0-3.204.013-3.583.07-4.849.149-3.227 1.664-4.771 4.919-4.919 1.266-.057 1.645-.069 4.849-.069zm0-2.163c-3.259 0-3.667.014-4.947.072-4.358.2-6.78 2.618-6.98 6.98-.059 1.281-.073 1.689-.073 4.948 0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98 1.281.058 1.689.072 4.948.072 3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98-1.281-.059-1.69-.073-4.949-.073zm0 5.838c-3.403 0-6.162 2.759-6.162 6.162s2.759 6.163 6.162 6.163 6.162-2.759 6.162-6.163c0-3.403-2.759-6.162-6.162-6.162zm0 10.162c-2.209 0-4-1.79-4-4 0-2.209 1.791-4 4-4s4 1.791 4 4c0 2.21-1.791 4-4 4zm6.406-11.845c-.796 0-1.441.645-1.441 1.44s.645 1.44 1.441 1.44c.795 0 1.439-.645 1.439-1.44s-.644-1.44-1.439-1.44z"></path>
            </svg>
          </a>
        </div>
      </div>
    </div>

    <div class="card p-6">
      <h3 class="text-lg font-bold mb-4">分类</h3>
      <ul class="space-y-2">
        <li v-for="category in categories" :key="category.id">
          <router-link 
            :to="`/categories/${category.id}`" 
            class="flex items-center justify-between text-gray-600 hover:text-primary-600 transition-colors"
          >
            <span>{{ category.name }}</span>
            <span class="text-sm bg-gray-100 px-2 py-0.5 rounded-full">{{ category.articleCount || 0 }}</span>
          </router-link>
        </li>
      </ul>
    </div>

    <div class="card p-6">
      <h3 class="text-lg font-bold mb-4">热门标签</h3>
      <div class="flex flex-wrap gap-2">
        <span 
          v-for="tag in tags" 
          :key="tag" 
          class="tag cursor-pointer hover:bg-primary-100 transition-colors"
          @click="goToTag(tag)"
        >
          {{ tag }}
        </span>
      </div>
    </div>

    <div class="card p-6">
      <h3 class="text-lg font-bold mb-4">最新文章</h3>
      <ul class="space-y-3">
        <li v-for="article in latestArticles" :key="article.id">
          <router-link 
            :to="`/article/${article.id}`" 
            class="block text-gray-600 hover:text-primary-600 transition-colors text-sm line-clamp-1"
          >
            {{ article.title }}
          </router-link>
        </li>
      </ul>
    </div>
  </aside>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { categoryApi, articleApi } from '@/api/index'

const router = useRouter()

const avatar = 'https://api.dicebear.com/7.x/avataaars/svg?seed=Author'
const nickname = '博客作者'
const bio = '热爱技术，喜欢分享。欢迎关注我的博客！'

const categories = ref([])
const tags = ref([
  'Vue3', 'React', 'TypeScript', 'Spring Boot', 'MySQL', 'Redis', 
  'Docker', 'Git', 'VS Code', 'Node.js', '前端', '后端', '算法', '面试'
])
const latestArticles = ref([])

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

const loadLatestArticles = async () => {
  try {
    const response = await articleApi.list()
    if (response.code === 200) {
      latestArticles.value = (response.data || []).slice(0, 5)
    }
  } catch (error) {
    console.error('加载最新文章失败:', error)
  }
}

const goToTag = (tag) => {
  router.push(`/tag/${tag}`)
}

onMounted(() => {
  loadCategories()
  loadLatestArticles()
})
</script>
