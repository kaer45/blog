
<template>
  <Layout>
    <div class="max-w-4xl mx-auto">
      <div class="card p-8 mb-6">
        <div class="flex flex-col md:flex-row items-center gap-6">
          <div class="relative">
            <img 
              :src="user.avatar || 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + user.username" 
              :alt="user.nickname" 
              class="w-24 h-24 rounded-full"
            />
            <button class="absolute bottom-0 right-0 bg-primary-600 text-white p-2 rounded-full hover:bg-primary-700 transition-colors">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
              </svg>
            </button>
          </div>

          <div class="flex-1 text-center md:text-left">
            <h1 class="text-2xl font-bold">{{ user.nickname || user.username }}</h1>
            <p class="text-gray-500 mt-1">{{ user.username }}</p>
            <p class="text-gray-600 mt-3">{{ user.bio || '暂无简介' }}</p>
            <div class="flex items-center space-x-4 mt-4 justify-center md:justify-start">
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

          <button class="btn btn-outline">
            编辑资料
          </button>
        </div>
      </div>

      <div class="grid grid-cols-3 gap-4 mb-6">
        <div class="card p-4 text-center">
          <div class="text-2xl font-bold text-primary-600">{{ userArticles.length }}</div>
          <div class="text-sm text-gray-500">文章</div>
        </div>
        <div class="card p-4 text-center">
          <div class="text-2xl font-bold text-primary-600">{{ 128 }}</div>
          <div class="text-sm text-gray-500">粉丝</div>
        </div>
        <div class="card p-4 text-center">
          <div class="text-2xl font-bold text-primary-600">{{ 56 }}</div>
          <div class="text-sm text-gray-500">关注</div>
        </div>
      </div>

      <div class="flex border-b mb-6">
        <button 
          @click="activeTab = 'articles'"
          :class="['px-4 py-2 font-medium', activeTab === 'articles' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
        >
          文章 ({{ userArticles.length }})
        </button>
        <button 
          @click="activeTab = 'drafts'"
          :class="['px-4 py-2 font-medium', activeTab === 'drafts' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
        >
          草稿 ({{ 2 }})
        </button>
        <button 
          @click="activeTab = 'likes'"
          :class="['px-4 py-2 font-medium', activeTab === 'likes' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
        >
          收藏 ({{ 15 }})
        </button>
      </div>

      <div v-if="activeTab === 'articles'" class="space-y-4">
        <div v-for="article in userArticles" :key="article.id" class="card p-4 flex items-center justify-between">
          <div class="flex-1">
            <h3 class="font-semibold text-gray-900">{{ article.title }}</h3>
            <div class="flex items-center space-x-4 mt-2 text-sm text-gray-500">
              <span>{{ formatDate(article.createdAt) }}</span>
              <span>{{ article.viewCount || 0 }} 阅读</span>
              <span>{{ article.commentCount || 0 }} 评论</span>
            </div>
          </div>
          <div class="flex items-center space-x-2">
            <button class="btn btn-secondary text-sm">编辑</button>
            <button class="btn btn-secondary text-sm">删除</button>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'drafts'" class="text-center py-16 text-gray-500">
        暂无草稿
      </div>

      <div v-if="activeTab === 'likes'" class="text-center py-16 text-gray-500">
        暂无收藏
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed } from 'vue'
import Layout from '@/components/Layout.vue'
import { useUserStore } from '@/stores/user'
import { articles } from '@/data/mockData'

const userStore = useUserStore()
const activeTab = ref('articles')

const user = computed(() => userStore.user)

const userArticles = computed(() => {
  return articles.filter(a => a.authorId === user.value.id)
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN')
}
</script>
