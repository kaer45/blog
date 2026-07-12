
<template>
  <Layout>
    <div class="max-w-2xl mx-auto">
      <h1 class="page-title">设置</h1>
      
      <div class="card p-8 mb-6">
        <h3 class="text-lg font-bold mb-4">基本信息</h3>
        
        <form class="space-y-6" @submit.prevent="handleSubmit">
          <div class="form-group">
            <label class="form-label">用户名</label>
            <input 
              v-model="form.username"
              type="text" 
              class="form-input" 
              disabled
            />
          </div>

          <div class="form-group">
            <label class="form-label">昵称</label>
            <input 
              v-model="form.nickname"
              type="text" 
              class="form-input" 
              placeholder="请输入昵称"
            />
          </div>

          <div class="form-group">
            <label class="form-label">邮箱</label>
            <input 
              v-model="form.email"
              type="email" 
              class="form-input" 
              placeholder="请输入邮箱"
              disabled
            />
          </div>

          <div class="form-group">
            <label class="form-label">简介</label>
            <textarea 
              v-model="form.bio"
              class="form-input textarea" 
              placeholder="请输入简介..."
            ></textarea>
          </div>

          <button type="submit" class="btn btn-primary" :disabled="loading">
            {{ loading ? '保存中...' : '保存修改' }}
          </button>
        </form>
      </div>

      <div class="card p-8 mb-6">
        <h3 class="text-lg font-bold mb-4">密码修改</h3>
        
        <form class="space-y-6">
          <div class="form-group">
            <label class="form-label">当前密码</label>
            <input 
              v-model="passwordForm.currentPassword"
              type="password" 
              class="form-input" 
              placeholder="请输入当前密码"
            />
          </div>

          <div class="form-group">
            <label class="form-label">新密码</label>
            <input 
              v-model="passwordForm.newPassword"
              type="password" 
              class="form-input" 
              placeholder="请输入新密码"
            />
          </div>

          <div class="form-group">
            <label class="form-label">确认新密码</label>
            <input 
              v-model="passwordForm.confirmPassword"
              type="password" 
              class="form-input" 
              placeholder="请再次输入新密码"
            />
          </div>

          <button type="submit" class="btn btn-primary">
            修改密码
          </button>
        </form>
      </div>

      <div class="card p-8">
        <h3 class="text-lg font-bold mb-4">通知设置</h3>
        
        <div class="space-y-4">
          <label class="flex items-center">
            <input type="checkbox" checked class="mr-3" />
            <span>文章被评论时通知我</span>
          </label>
          <label class="flex items-center">
            <input type="checkbox" checked class="mr-3" />
            <span>收到新粉丝时通知我</span>
          </label>
          <label class="flex items-center">
            <input type="checkbox" class="mr-3" />
            <span>每周精选文章推送</span>
          </label>
        </div>

        <button class="btn btn-primary mt-6">
          保存设置
        </button>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Layout from '@/components/Layout.vue'
import { useUserStore } from '@/stores/user'
import { userApi } from '@/api/index'

const userStore = useUserStore()

const form = ref({
  username: '',
  nickname: '',
  email: '',
  bio: ''
})

const passwordForm = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const loading = ref(false)

onMounted(async () => {
  try {
    const response = await userApi.getProfile()
    if (response.code === 200) {
      const userData = response.data
      form.value = {
        username: userData.username || '',
        nickname: userData.nickname || '',
        email: userData.email || '',
        bio: userData.bio || ''
      }
      userStore.login(userData)
    }
  } catch (error) {
    console.error('加载用户资料失败:', error)
  }
})

const handleSubmit = async () => {
  loading.value = true
  try {
    const response = await userApi.updateProfile({
      nickname: form.value.nickname,
      bio: form.value.bio
    })
    if (response.code === 200) {
      userStore.login(response.data)
      alert('资料更新成功')
    }
  } catch (error) {
    console.error('更新资料失败:', error)
    alert('更新失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>
