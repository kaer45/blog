
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  const user = ref({
    id: null,
    username: '',
    nickname: '',
    email: '',
    avatar: '',
    bio: '',
    githubUrl: ''
  })

  const isLoggedIn = computed(() => user.value.id !== null)

  const login = (userData) => {
    user.value = { 
      id: userData.id,
      username: userData.username,
      nickname: userData.nickname || userData.username,
      email: userData.email,
      avatar: userData.avatar || '',
      bio: userData.bio || '',
      githubUrl: userData.githubUrl || ''
    }
    localStorage.setItem('user', JSON.stringify(user.value))
  }

  const logout = () => {
    user.value = {
      id: null,
      username: '',
      nickname: '',
      email: '',
      avatar: '',
      bio: '',
      githubUrl: ''
    }
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  const initUser = () => {
    const storedUser = localStorage.getItem('user')
    if (storedUser) {
      try {
        user.value = JSON.parse(storedUser)
      } catch (e) {
        console.error('Failed to parse stored user:', e)
      }
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
