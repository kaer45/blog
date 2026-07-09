
<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-primary-50 to-blue-100">
    <div class="card p-8 w-full max-w-md">
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">创建账号</h1>
        <p class="text-gray-600">开始你的博客之旅</p>
      </div>

      <form @submit.prevent="handleRegister" class="space-y-6">
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
          <label class="form-label">邮箱</label>
          <input 
            v-model="form.email"
            type="email" 
            class="form-input" 
            placeholder="请输入邮箱"
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

        <div class="form-group">
          <label class="form-label">确认密码</label>
          <input 
            v-model="form.confirmPassword"
            type="password" 
            class="form-input" 
            placeholder="请再次输入密码"
          />
        </div>

        <div class="form-group">
          <label class="form-label">昵称（可选）</label>
          <input 
            v-model="form.nickname"
            type="text" 
            class="form-input" 
            placeholder="请输入昵称"
          />
        </div>

        <div class="flex items-start">
          <input type="checkbox" id="agree" class="mr-2 mt-1" v-model="form.agree" />
          <label for="agree" class="text-sm text-gray-600">
            我已阅读并同意
            <a href="#" class="text-primary-600 hover:underline">服务条款</a>
            和
            <a href="#" class="text-primary-600 hover:underline">隐私政策</a>
          </label>
        </div>

        <button type="submit" class="btn btn-primary w-full">
          注册
        </button>
      </form>

      <div class="mt-6 text-center">
        <p class="text-gray-600">
          已有账号？
          <router-link to="/login" class="text-primary-600 hover:underline">立即登录</router-link>
        </p>
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
  email: '',
  password: '',
  confirmPassword: '',
  nickname: '',
  agree: false
})

const handleRegister = () => {
  if (!form.value.username || !form.value.email || !form.value.password) {
    alert('请填写必填项')
    return
  }
  
  if (form.value.password !== form.value.confirmPassword) {
    alert('两次输入的密码不一致')
    return
  }
  
  if (!form.value.agree) {
    alert('请同意服务条款和隐私政策')
    return
  }
  
  userStore.login({
    id: 1,
    username: form.value.username,
    nickname: form.value.nickname || form.value.username,
    email: form.value.email,
    avatar: '',
    bio: ''
  })
  
  router.push('/')
}
</script>
