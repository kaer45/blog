
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue')
  },
  {
    path: '/article/:id',
    name: 'ArticleDetail',
    component: () => import('@/views/ArticleDetail.vue')
  },
  {
    path: '/categories',
    name: 'Categories',
    component: () => import('@/views/Categories.vue')
  },
  {
    path: '/categories/:id',
    name: 'CategoryDetail',
    component: () => import('@/views/CategoryDetail.vue')
  },
  {
    path: '/tag/:name',
    name: 'TagDetail',
    component: () => import('@/views/Home.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/views/About.vue')
  },
  {
    path: '/contact',
    name: 'Contact',
    component: () => import('@/views/Contact.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/write',
    name: 'WriteArticle',
    component: () => import('@/views/WriteArticle.vue')
  },
  {
    path: '/write/:id',
    name: 'EditArticle',
    component: () => import('@/views/WriteArticle.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/Profile.vue')
  },
  {
    path: '/user/:username',
    name: 'UserProfile',
    component: () => import('@/views/UserProfile.vue')
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('@/views/Settings.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem('token')
  if (to.path === '/write' && !isLoggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router
