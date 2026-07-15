
<template>
  <div class="card p-6 mb-6 hover:shadow-md transition-shadow cursor-pointer" @click="goToDetail">
    <div class="flex items-center justify-between mb-3">
      <span class="tag">{{ categoryName }}</span>
      <span class="text-sm text-gray-500">{{ formatDate(article.createdAt) }}</span>
    </div>
    <h2 class="text-xl font-bold text-gray-900 mb-2 line-clamp-2">{{ article.title }}</h2>
    <p class="text-gray-600 mb-4 line-clamp-3">{{ article.summary || article.content.substring(0, 150) }}</p>
    <div class="flex items-center justify-between">
      <div class="flex items-center space-x-4">
        <img 
          :src="authorAvatar" 
          :alt="article.authorName" 
          class="w-8 h-8 rounded-full"
        />
        <span class="text-sm text-gray-600">{{ article.authorName || '作者' }}</span>
      </div>
      <div class="flex items-center space-x-4 text-sm text-gray-500">
        <span v-if="isLoggedIn" class="flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
          </svg>
          {{ article.viewCount || 0 }}
        </span>
        <span class="flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
          </svg>
          {{ article.likeCount || 0 }}
        </span>
        <span class="flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"></path>
          </svg>
          {{ article.commentCount || 0 }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const props = defineProps({
  article: {
    type: Object,
    required: true
  },
  categoryName: {
    type: String,
    default: '未分类'
  }
})

const router = useRouter()
const userStore = useUserStore()

const isLoggedIn = computed(() => userStore.isLoggedIn)

const authorAvatar = computed(() => {
  if (props.article.authorAvatar) {
    return props.article.authorAvatar
  }
  return 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + (props.article.authorName || 'User')
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const goToDetail = () => {
  router.push(`/article/${props.article.id}`)
}
</script>
