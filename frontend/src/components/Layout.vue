
<template>
  <div class="min-h-screen flex flex-col">
    <header class="bg-white shadow-sm sticky top-0 z-50">
      <div class="container mx-auto px-4 py-4 flex items-center justify-between">
        <div class="flex items-center">
          <router-link to="/" class="text-2xl font-bold text-primary-600">
            我的博客
          </router-link>
        </div>
        
        <nav class="hidden md:flex items-center space-x-6">
          <router-link to="/" class="text-gray-600 hover:text-primary-600 transition-colors">
            首页
          </router-link>
          <router-link to="/categories" class="text-gray-600 hover:text-primary-600 transition-colors">
            分类
          </router-link>
          <router-link to="/about" class="text-gray-600 hover:text-primary-600 transition-colors">
            关于
          </router-link>
          <router-link to="/contact" class="text-gray-600 hover:text-primary-600 transition-colors">
            联系
          </router-link>
        </nav>

        <div class="flex items-center space-x-4">
          <router-link v-if="!isLoggedIn" to="/login" class="btn btn-secondary">
            登录
          </router-link>
          <router-link v-if="!isLoggedIn" to="/register" class="btn btn-primary">
            注册
          </router-link>
          
          <div v-if="isLoggedIn" class="flex items-center space-x-3">
            <router-link to="/write" class="btn btn-primary">
              写文章
            </router-link>
            <div class="relative user-menu-container">
              <button @click.stop="toggleUserMenu" class="flex items-center space-x-2">
                <img 
                  :src="currentUser.avatar || 'https://api.dicebear.com/7.x/avataaars/svg?seed=' + currentUser.username" 
                  :alt="currentUser.nickname" 
                  class="w-8 h-8 rounded-full"
                />
                <span class="text-sm font-medium">{{ currentUser.nickname || currentUser.username }}</span>
              </button>
              <div v-if="showUserMenu" class="absolute right-0 top-full mt-2 w-48 bg-white rounded-lg shadow-lg py-2 z-50">
                <router-link to="/profile" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                  个人中心
                </router-link>
                <router-link to="/settings" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                  设置
                </router-link>
                <hr class="my-2" />
                <button @click.stop="logout" class="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100">
                  退出登录
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>

    <main class="flex-1 container mx-auto px-4 py-8">
      <slot />
    </main>

    <footer class="bg-gray-800 text-white py-8">
      <div class="container mx-auto px-4">
        <div class="flex flex-col md:flex-row items-center justify-between">
          <div class="mb-4 md:mb-0">
            <p class="text-lg font-bold">我的博客</p>
            <p class="text-gray-400 text-sm mt-1">记录生活，分享技术</p>
          </div>
          <div class="flex space-x-4">
            <a href="#" class="text-gray-400 hover:text-white transition-colors">GitHub</a>
            <a href="#" class="text-gray-400 hover:text-white transition-colors">微博</a>
            <a href="#" class="text-gray-400 hover:text-white transition-colors">邮箱</a>
          </div>
          <div class="text-gray-400 text-sm mt-4 md:mt-0">
            © 2024 我的博客. All rights reserved.
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const showUserMenu = ref(false)

const isLoggedIn = computed(() => userStore.isLoggedIn)
const currentUser = computed(() => userStore.user)

const logout = () => {
  userStore.logout()
  showUserMenu.value = false
}

const handleClickOutside = (event) => {
  const userMenuContainer = document.querySelector('.user-menu-container')
  if (userMenuContainer && !userMenuContainer.contains(event.target)) {
    showUserMenu.value = false
  }
}

const toggleUserMenu = () => {
  showUserMenu.value = !showUserMenu.value
}

onMounted(() => {
  userStore.initUser()
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>
