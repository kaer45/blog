
<template>
  <aside class="space-y-6">
    <div class="card p-6">
      <h3 class="text-lg font-bold mb-4">关于我</h3>
      <div class="flex flex-col items-center text-center">
        <img 
          :src="user.avatar || 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + user.username" 
          :alt="user.nickname || user.username" 
          class="w-20 h-20 rounded-full mb-3 object-cover"
        />
        <h4 class="font-semibold">{{ user.nickname || user.username }}</h4>
        <p class="text-gray-500 text-sm mt-2">{{ user.bio || '热爱技术，喜欢分享。欢迎关注我的博客！' }}</p>
        <div class="flex space-x-4 mt-4">
          <a v-if="user.githubUrl" :href="user.githubUrl" target="_blank" class="text-gray-400 hover:text-primary-600">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
              <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.7-2.782.604-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.03 1.531 1.03.892 1.529 2.341 1.087 2.91.831.092-.646.35-1.086.636-1.336-2.22-.253-4.555-1.11-4.555-4.943 0-1.091.39-1.984 1.029-2.683-.103-.253-.446-1.27.098-2.647 0 0 .84-.268 2.75 1.026A9.578 9.578 0 0112 6.844c.85.004 1.705.114 2.504.336 1.909-1.294 2.747-1.026 2.747-1.026.546 1.377.203 2.394.1 2.647.64.699 1.028 1.592 1.028 2.683 0 3.842-2.339 4.687-4.566 4.935.359.309.678.919.678 1.852 0 1.336-.012 2.415-.012 2.743 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" clip-rule="evenodd"></path>
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
import { categoryApi, articleApi, userApi } from '@/api/index'

const router = useRouter()

const user = ref({
  id: null,
  username: '',
  nickname: '',
  email: '',
  avatar: '',
  bio: '',
  githubUrl: ''
})

const categories = ref([])
const tags = ref([
  'Vue3', 'React', 'TypeScript', 'Spring Boot', 'MySQL', 'Redis', 
  'Docker', 'Git', 'VS Code', 'Node.js', '前端', '后端', '算法', '面试'
])
const latestArticles = ref([])

const loadUserInfo = async () => {
  try {
    const response = await userApi.getPublicProfile()
    if (response.code === 200) {
      user.value = response.data || {}
    }
  } catch (error) {
    console.error('加载用户信息失败:', error)
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
  loadUserInfo()
  loadCategories()
  loadLatestArticles()
})
</script>
