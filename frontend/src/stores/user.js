
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  const user = ref({
    id: null,
    username: '',
    nickname: '',
    email: '',
    avatar: '',
    bio: ''
  })

  const isLoggedIn = computed(() => user.value.id !== null)

  const login = (userData) => {
    user.value = { ...userData }
    localStorage.setItem('token', 'mock-token')
    localStorage.setItem('user', JSON.stringify(userData))
  }

  const logout = () => {
    user.value = {
      id: null,
      username: '',
      nickname: '',
      email: '',
      avatar: '',
      bio: ''
    }
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  const initUser = () => {
    const storedUser = localStorage.getItem('user')
    if (storedUser) {
      user.value = JSON.parse(storedUser)
    }
  }

  return {
    user,
    isLoggedIn,
    login,
    logout,
    initUser
  }
})
