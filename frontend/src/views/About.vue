
<template>
  <Layout>
    <div class="max-w-2xl mx-auto">
      <h1 class="page-title">关于我</h1>
      
      <div class="card p-8 mb-6">
        <div class="flex items-center gap-6 mb-6">
          <img 
            src="https://api.dicebear.com/7.x/avataaars/svg?seed=About" 
            alt="Avatar" 
            class="w-20 h-20 rounded-full"
          />
          <div>
            <h2 class="text-xl font-bold">博客作者</h2>
            <p class="text-gray-600">热爱技术，喜欢分享</p>
          </div>
        </div>
        
        <div class="space-y-4">
          <p>欢迎来到我的个人博客！</p>
          <p>我是一名热爱技术的开发者，喜欢探索新技术，分享编程心得。在这里，你可以看到我关于前端开发、后端技术、生活感悟等方面的文章。</p>
          <p>我相信技术可以改变世界，希望通过我的分享，能够帮助到更多的开发者。</p>
        </div>
      </div>

      <div class="card p-6 mb-6">
        <h3 class="text-lg font-bold mb-4">技术栈</h3>
        <div class="flex flex-wrap gap-2">
          <span class="tag">Vue.js</span>
          <span class="tag">React</span>
          <span class="tag">Spring Boot</span>
          <span class="tag">Node.js</span>
          <span class="tag">TypeScript</span>
          <span class="tag">MySQL</span>
          <span class="tag">MongoDB</span>
          <span class="tag">Docker</span>
        </div>
      </div>

      <div class="card p-6">
        <h3 class="text-lg font-bold mb-4">联系方式</h3>
        <div class="space-y-2">
          <div class="flex items-center">
            <svg class="w-5 h-5 text-gray-500 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
            </svg>
            <span>email@example.com</span>
          </div>
          <div class="flex items-center">
            <svg class="w-5 h-5 text-gray-500 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
            </svg>
            <span>北京市</span>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import Layout from '@/components/Layout.vue'
</script>
