<template>
  <Layout>
    <div class="max-w-2xl mx-auto">
      <h1 class="page-title">联系我</h1>
      
      <div class="card p-8 mb-6">
        <h3 class="text-lg font-bold mb-4">给我留言</h3>
        
        <form @submit.prevent="handleSubmit" class="space-y-6">
          <div class="form-group">
            <label class="form-label">姓名 <span class="text-red-500">*</span></label>
            <input 
              v-model="form.name"
              type="text" 
              class="form-input" 
              placeholder="请输入你的姓名"
            />
            <p v-if="errors.name" class="text-red-500 text-sm mt-1">{{ errors.name }}</p>
          </div>

          <div class="form-group">
            <label class="form-label">邮箱 <span class="text-red-500">*</span></label>
            <input 
              v-model="form.email"
              type="email" 
              class="form-input" 
              placeholder="请输入你的邮箱"
            />
            <p v-if="errors.email" class="text-red-500 text-sm mt-1">{{ errors.email }}</p>
          </div>

          <div class="form-group">
            <label class="form-label">主题</label>
            <input 
              v-model="form.subject"
              type="text" 
              class="form-input" 
              placeholder="请输入留言主题"
            />
          </div>

          <div class="form-group">
            <label class="form-label">内容 <span class="text-red-500">*</span></label>
            <textarea 
              v-model="form.message"
              class="form-input textarea" 
              placeholder="请输入留言内容..."
              rows="5"
            ></textarea>
            <p v-if="errors.message" class="text-red-500 text-sm mt-1">{{ errors.message }}</p>
          </div>

          <button 
            type="submit" 
            class="btn btn-primary w-full"
            :disabled="loading"
          >
            <span v-if="loading" class="flex items-center">
              <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              发送中...
            </span>
            <span v-else>发送留言</span>
          </button>
        </form>
      </div>

      <div class="card p-6">
        <h3 class="text-lg font-bold mb-4">其他联系方式</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="flex items-center">
            <svg class="w-8 h-8 text-primary-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
            </svg>
            <div>
              <p class="font-medium">邮箱</p>
              <p class="text-gray-500 text-sm">email@example.com</p>
            </div>
          </div>
          <div class="flex items-center">
            <svg class="w-8 h-8 text-primary-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
            </svg>
            <div>
              <p class="font-medium">地址</p>
              <p class="text-gray-500 text-sm">北京市</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, reactive } from 'vue'
import Layout from '@/components/Layout.vue'
import { contactApi } from '@/api/index'

const form = reactive({
  name: '',
  email: '',
  subject: '',
  message: ''
})

const errors = reactive({
  name: '',
  email: '',
  message: ''
})

const loading = ref(false)

const validateForm = () => {
  errors.name = ''
  errors.email = ''
  errors.message = ''
  
  let isValid = true
  
  if (!form.name.trim()) {
    errors.name = '请输入姓名'
    isValid = false
  }
  
  if (!form.email.trim()) {
    errors.email = '请输入邮箱'
    isValid = false
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = '请输入有效的邮箱地址'
    isValid = false
  }
  
  if (!form.message.trim()) {
    errors.message = '请输入留言内容'
    isValid = false
  }
  
  return isValid
}

const handleSubmit = async () => {
  if (!validateForm()) {
    return
  }
  
  loading.value = true
  try {
    const response = await contactApi.create({
      name: form.name.trim(),
      email: form.email.trim(),
      subject: form.subject.trim(),
      message: form.message.trim()
    })
    
    if (response.code === 200) {
      alert('留言发送成功！我会尽快回复你。')
      form.name = ''
      form.email = ''
      form.subject = ''
      form.message = ''
    } else {
      alert('留言发送失败，请稍后重试')
    }
  } catch (error) {
    console.error('留言发送失败:', error)
    alert('留言发送失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.textarea {
  resize: vertical;
  min-height: 120px;
}
</style>