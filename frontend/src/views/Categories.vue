<template>
  <Layout>
    <div class="flex flex-col lg:flex-row gap-8">
      <div class="flex-1">
        <div class="flex items-center justify-between mb-8">
          <h1 class="page-title">文章分类</h1>
          <button 
            v-if="isLoggedIn"
            @click="openModal()" 
            class="btn btn-primary"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
            </svg>
            新建分类
          </button>
        </div>
        
        <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
          <div 
            v-for="category in categories" 
            :key="category.id" 
            class="card p-6 cursor-pointer hover:shadow-md transition-shadow relative group"
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
            
            <div 
              v-if="isLoggedIn"
              class="absolute top-2 right-2 flex space-x-1 opacity-0 group-hover:opacity-100 transition-opacity"
              @click.stop
            >
              <button 
                @click="openModal(category)" 
                class="p-1.5 bg-white rounded-full shadow-md hover:bg-primary-50 text-gray-600 hover:text-primary-600"
              >
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                </svg>
              </button>
              <button 
                @click="deleteCategory(category.id)" 
                class="p-1.5 bg-white rounded-full shadow-md hover:bg-red-50 text-gray-600 hover:text-red-600"
              >
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                </svg>
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="lg:w-80">
        <Sidebar />
      </div>
    </div>

    <div 
      v-if="showModal" 
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div class="card p-6 w-full max-w-md">
        <h2 class="text-xl font-bold mb-4">{{ editingCategory ? '编辑分类' : '新建分类' }}</h2>
        
        <form @submit.prevent="handleSubmit" class="space-y-4">
          <div class="form-group">
            <label class="form-label">分类名称 <span class="text-red-500">*</span></label>
            <input 
              v-model="form.name"
              type="text" 
              class="form-input" 
              placeholder="请输入分类名称"
            />
            <p v-if="errors.name" class="text-red-500 text-sm mt-1">{{ errors.name }}</p>
          </div>

          <div class="form-group">
            <label class="form-label">分类描述</label>
            <textarea 
              v-model="form.description"
              class="form-input textarea" 
              placeholder="请输入分类描述"
              rows="3"
            ></textarea>
          </div>

          <div class="form-group">
            <label class="form-label">分类图标</label>
            <div class="flex flex-wrap gap-2">
              <button 
                v-for="icon in icons" 
                :key="icon"
                type="button"
                @click="form.icon = icon"
                :class="['w-10 h-10 rounded-lg flex items-center justify-center text-xl border-2 transition-colors', form.icon === icon ? 'border-primary-600 bg-primary-50' : 'border-gray-200 hover:border-primary-300']"
              >
                {{ icon }}
              </button>
            </div>
          </div>

          <div class="flex justify-end space-x-2 mt-6">
            <button type="button" @click="closeModal" class="btn btn-secondary">取消</button>
            <button type="submit" class="btn btn-primary">
              {{ editingCategory ? '保存修改' : '创建分类' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Layout from '@/components/Layout.vue'
import Sidebar from '@/components/Sidebar.vue'
import { categoryApi } from '@/api/index'

const router = useRouter()
const categories = ref([])
const showModal = ref(false)
const editingCategory = ref(null)
const isLoggedIn = ref(!!localStorage.getItem('token'))

const icons = ['📝', '💻', '🎨', '📚', '🚀', '🔥', '💡', '🔧', '⚡', '🎯']

const form = ref({
  name: '',
  description: '',
  icon: '📝'
})

const errors = ref({
  name: ''
})

const goToCategory = (categoryId) => {
  router.push(`/categories/${categoryId}`)
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

const openModal = (category = null) => {
  editingCategory.value = category
  if (category) {
    form.value = {
      name: category.name,
      description: category.description || '',
      icon: category.icon || '📝'
    }
  } else {
    form.value = {
      name: '',
      description: '',
      icon: '📝'
    }
  }
  errors.value = { name: '' }
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
  editingCategory.value = null
}

const validateForm = () => {
  errors.value.name = ''
  if (!form.value.name.trim()) {
    errors.value.name = '请输入分类名称'
    return false
  }
  return true
}

const handleSubmit = async () => {
  if (!validateForm()) {
    return
  }

  try {
    if (editingCategory.value) {
      const response = await categoryApi.update(editingCategory.value.id, {
        name: form.value.name.trim(),
        description: form.value.description.trim()
      })
      if (response.code === 200) {
        alert('分类更新成功')
      }
    } else {
      const response = await categoryApi.create({
        name: form.value.name.trim(),
        description: form.value.description.trim(),
        icon: form.value.icon,
        articleCount: 0
      })
      if (response.code === 200) {
        alert('分类创建成功')
      }
    }
    closeModal()
    await loadCategories()
  } catch (error) {
    console.error('操作失败:', error)
    alert('操作失败，请稍后重试')
  }
}

const deleteCategory = async (categoryId) => {
  if (!confirm('确定要删除这个分类吗？该分类下的文章将变为未分类状态。')) {
    return
  }

  try {
    const response = await categoryApi.delete(categoryId)
    if (response.code === 200) {
      alert('分类删除成功')
      await loadCategories()
    }
  } catch (error) {
    console.error('删除分类失败:', error)
    alert('删除失败，请稍后重试')
  }
}

onMounted(() => {
  loadCategories()
})
</script>

<style scoped>
.textarea {
  resize: vertical;
  min-height: 80px;
}
</style>
