
<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-primary-50 to-blue-100">
    <div class="card p-8 w-full max-w-md">
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">欢迎回来</h1>
        <p class="text-gray-600">登录你的账号</p>
      </div>

      <form @submit.prevent="handleLogin" class="space-y-6">
        <div class="form-group">
          <label class="form-label">用户名</label>
          <input 
            v-model="form.username"
            type="text" 
            class="form-input" 
            placeholder="请输入用户名"
          />
        </div>

        <div class="form-group">
          <label class="form-label">密码</label>
          <input 
            v-model="form.password"
            type="password" 
            class="form-input" 
            placeholder="请输入密码"
          />
        </div>

        <div class="flex items-center justify-between">
          <label class="flex items-center">
            <input type="checkbox" class="mr-2" />
            <span class="text-sm text-gray-600">记住我</span>
          </label>
          <a href="#" class="text-sm text-primary-600 hover:underline">忘记密码？</a>
        </div>

        <button type="submit" class="btn btn-primary w-full">
          登录
        </button>
      </form>

      <div class="mt-6 text-center">
        <p class="text-gray-600">
          还没有账号？
          <router-link to="/register" class="text-primary-600 hover:underline">立即注册</router-link>
        </p>
      </div>

      <div class="mt-8">
        <div class="relative">
          <div class="absolute inset-0 flex items-center">
            <div class="w-full border-t border-gray-200"></div>
          </div>
          <div class="relative flex justify-center text-sm">
            <span class="px-4 bg-white text-gray-500">或者</span>
          </div>
        </div>

        <div class="mt-4 grid grid-cols-2 gap-4">
          <button class="btn btn-secondary flex items-center justify-center space-x-2">
            <svg class="w-5 h-5" viewBox="0 0 24 24">
              <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
              <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
              <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
            </svg>
            <span>Google</span>
          </button>
          <button class="btn btn-secondary flex items-center justify-center space-x-2">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
              <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
            </svg>
            <span>GitHub</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const form = ref({
  username: '',
  password: ''
})

const handleLogin = () => {
  if (!form.value.username || !form.value.password) {
    alert('请填写用户名和密码')
    return
  }
  
  userStore.login({
    id: 1,
    username: form.value.username,
    nickname: '用户',
    email: 'user@example.com',
    avatar: '',
    bio: ''
  })
  
  router.push('/')
}
</script>
