<template>
  <Layout>
    <div class="max-w-2xl mx-auto">
      <h1 class="page-title">关于我</h1>
      
      <div class="card p-8 mb-6">
        <div class="flex flex-col md:flex-row items-center gap-6 mb-6">
          <img 
            :src="user.avatar || 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + user.username" 
            :alt="user.nickname || user.username" 
            class="w-24 h-24 rounded-full object-cover"
          />
          <div class="text-center md:text-left">
            <h2 class="text-2xl font-bold">{{ user.nickname || user.username }}</h2>
            <p class="text-gray-600">{{ user.username }}</p>
          </div>
        </div>
        
        <div class="space-y-4">
          <p v-if="user.bio">{{ user.bio }}</p>
          <p v-else>欢迎来到我的个人博客！我是一名热爱技术的开发者，喜欢探索新技术，分享编程心得。</p>
        </div>

        <div class="flex items-center space-x-4 mt-6 justify-center md:justify-start">
          <a v-if="user.githubUrl" :href="user.githubUrl" target="_blank" class="text-gray-400 hover:text-primary-600">
            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">
              <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.03 1.531 1.03.892 1.529 2.341 1.087 2.91.831.092-.646.35-1.086.636-1.336-2.22-.253-4.555-1.11-4.555-4.943 0-1.091.39-1.984 1.029-2.683-.103-.253-.446-1.27.098-2.647 0 0 .84-.269 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.295 2.747-1.026 2.747-1.026.546 1.377.203 2.394.1 2.647.64.699 1.028 1.592 1.028 2.683 0 3.842-2.339 4.687-4.566 4.935.359.309.678.919.678 1.852 0 1.336-.012 2.415-.012 2.743 0 .268.18.578.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" clip-rule="evenodd"></path>
            </svg>
          </a>
        </div>
      </div>

      <div class="card p-6 mb-6">
        <h3 class="text-lg font-bold mb-4">技术栈</h3>
        <div class="flex flex-wrap gap-2">
          <span class="tag">Vue.js</span>
          <span class="tag">React</span>
          <span class="tag">Spring Boot</span>
          <span class="tag">Node.js</span>
          <span class="tag">TypeScript</span>
          <span class="tag">MySQL</span>
          <span class="tag">MongoDB</span>
          <span class="tag">Docker</span>
          <span class="tag">Tailwind CSS</span>
          <span class="tag">Git</span>
        </div>
      </div>

      <div class="card p-6">
        <h3 class="text-lg font-bold mb-4">联系方式</h3>
        <div class="space-y-3">
          <div class="flex items-center">
            <svg class="w-5 h-5 text-gray-500 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
            </svg>
            <span>{{ user.email || 'email@example.com' }}</span>
          </div>
          <div class="flex items-center">
            <svg class="w-5 h-5 text-gray-500 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
            </svg>
            <span>北京市</span>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Layout from '@/components/Layout.vue'
import { userApi } from '@/api/index'

const user = ref({
  id: null,
  username: '',
  nickname: '',
  email: '',
  avatar: '',
  bio: '',
  githubUrl: ''
})

onMounted(async () => {
  await loadUserInfo()
})

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
</script>
