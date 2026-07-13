<template>
  <Layout>
    <div class="max-w-2xl mx-auto">
      <h1 class="page-title">设置</h1>
      
      <div class="card p-8 mb-6">
        <h3 class="text-lg font-bold mb-4">基本信息</h3>
        
        <form class="space-y-6" @submit.prevent="handleSubmit">
          <div class="flex items-center gap-6">
            <div class="relative">
              <img 
                :src="form.avatar || 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + form.username" 
                :alt="form.nickname" 
                class="w-20 h-20 rounded-full object-cover"
              />
              <label class="absolute bottom-0 right-0 bg-primary-600 text-white p-2 rounded-full hover:bg-primary-700 transition-colors cursor-pointer">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                </svg>
                <input type="file" accept="image/*" class="hidden" @change="handleAvatarUpload" />
              </label>
            </div>
            <div>
              <p class="text-sm text-gray-500">点击图标更换头像</p>
              <p class="text-xs text-gray-400 mt-1">支持 JPG、PNG 格式，大小不超过 2MB</p>
            </div>
          </div>

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

          <div class="form-group">
            <label class="form-label">GitHub 链接</label>
            <input 
              v-model="form.githubUrl"
              type="url" 
              class="form-input" 
              placeholder="https://github.com/your-username"
            />
          </div>

          <button type="submit" class="btn btn-primary" :disabled="loading">
            {{ loading ? '保存中...' : '保存修改' }}
          </button>
        </form>
      </div>

      <div class="card p-8 mb-6">
        <h3 class="text-lg font-bold mb-4">密码修改</h3>
        
        <form class="space-y-6" @submit.prevent="handlePasswordSubmit">
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
import { userApi, uploadApi } from '@/api/index'

const userStore = useUserStore()

const form = ref({
  username: '',
  nickname: '',
  email: '',
  bio: '',
  avatar: '',
  githubUrl: ''
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
        bio: userData.bio || '',
        avatar: userData.avatar || '',
        githubUrl: userData.githubUrl || ''
      }
      userStore.login(userData)
    }
  } catch (error) {
    console.error('加载用户资料失败:', error)
  }
})

const handleAvatarUpload = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  loading.value = true
  try {
    const response = await uploadApi.upload(file)
    if (response.code === 200) {
      form.value.avatar = response.data
      await userApi.updateProfile({ avatar: response.data })
      userStore.login({ ...userStore.user, avatar: response.data })
      alert('头像上传成功')
    }
  } catch (error) {
    console.error('头像上传失败:', error)
    alert('头像上传失败')
  } finally {
    loading.value = false
  }
}

const handleSubmit = async () => {
  loading.value = true
  try {
    const response = await userApi.updateProfile({
      nickname: form.value.nickname,
      bio: form.value.bio,
      avatar: form.value.avatar,
      githubUrl: form.value.githubUrl
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

const handlePasswordSubmit = async () => {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    alert('两次输入的密码不一致')
    return
  }
  alert('密码修改功能暂未实现')
}
</script>