
<template>
  <Layout>
    <div class="max-w-4xl mx-auto">
      <div class="flex items-center justify-between mb-6">
        <h1 class="page-title">{{ isEdit ? '编辑文章' : '写文章' }}</h1>
        <div class="flex items-center space-x-4">
          <button @click="saveDraft" class="btn btn-secondary">
            保存草稿
          </button>
          <button @click="previewArticle" class="btn btn-outline">
            预览
          </button>
          <button @click="publishArticle" class="btn btn-primary">
            {{ isEdit && !isDraft ? '更新文章' : '发布文章' }}
          </button>
        </div>
      </div>

      <div class="card p-6 mb-6">
        <div class="form-group">
          <input 
            v-model="form.title"
            type="text" 
            class="form-input text-2xl font-bold" 
            placeholder="输入文章标题..."
          />
        </div>

        <div class="flex items-center space-x-4 mb-6">
          <div class="flex-1">
            <label class="form-label">分类</label>
            <select v-model="form.categoryId" class="form-input">
              <option value="">选择分类</option>
              <option v-for="category in categories" :key="category.id" :value="category.id">
                {{ category.name }}
              </option>
            </select>
          </div>
          <div class="flex-1">
            <label class="form-label">标签</label>
            <input 
              v-model="form.tags"
              type="text" 
              class="form-input" 
              placeholder="多个标签用逗号分隔"
            />
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">摘要</label>
          <textarea 
            v-model="form.summary"
            class="form-input textarea" 
            placeholder="输入文章摘要..."
          ></textarea>
        </div>
      </div>

      <div class="card p-6">
        <div class="flex border-b mb-4">
          <button 
            @click="activeTab = 'editor'"
            :class="['px-4 py-2 font-medium', activeTab === 'editor' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
          >
            编辑器
          </button>
          <button 
            @click="activeTab = 'preview'"
            :class="['px-4 py-2 font-medium', activeTab === 'preview' ? 'text-primary-600 border-b-2 border-primary-600' : 'text-gray-500']"
          >
            预览
          </button>
        </div>

        <div v-if="activeTab === 'editor'" class="relative">
          <textarea 
            v-model="form.content"
            class="w-full h-[600px] p-4 font-mono text-sm border border-gray-200 rounded-lg resize-none focus:outline-none focus:border-primary-500"
            placeholder="开始编写文章...支持 Markdown 语法"
          ></textarea>
          <div class="absolute bottom-4 right-4 text-sm text-gray-400">
            {{ form.content.length }} 字
          </div>
        </div>

        <div v-else class="markdown-body p-4 bg-gray-50 rounded-lg min-h-[600px]">
          <div v-if="!form.content" class="flex items-center justify-center h-[600px] text-gray-400">
            预览区域
          </div>
          <div v-else v-html="renderedContent"></div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { marked } from 'marked'
import katex from 'katex'
import Layout from '@/components/Layout.vue'
import { categoryApi, articleApi } from '@/api/index'

marked.setOptions({
  gfm: true,
  breaks: true
})

const route = useRoute()
const router = useRouter()

const isEdit = ref(!!route.params.id)
const isDraft = ref(false)
const activeTab = ref('editor')
const categories = ref([])
const loading = ref(false)

const form = ref({
  title: '',
  summary: '',
  content: '',
  categoryId: '',
  tags: ''
})

const renderMath = (html) => {
  html = html.replace(/\$\$(.+?)\$\$/g, (_, formula) => {
    try {
      return katex.renderToString(formula.trim(), {
        throwOnError: false,
        displayMode: true
      })
    } catch (e) {
      return '$$' + formula + '$$'
    }
  })
  html = html.replace(/\$([^$]+)\$/g, (_, formula) => {
    try {
      return katex.renderToString(formula.trim(), {
        throwOnError: false,
        displayMode: false
      })
    } catch (e) {
      return '$' + formula + '$'
    }
  })
  return html
}

const renderedContent = computed(() => {
  if (!form.value.content) return ''
  let html = marked(form.value.content)
  html = renderMath(html)
  return html
})

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

const loadArticle = async () => {
  if (!isEdit.value) return
  
  loading.value = true
  try {
    const response = await articleApi.getById(route.params.id)
    if (response.code === 200) {
      const article = response.data
      form.value.title = article.title
      form.value.summary = article.summary
      form.value.content = article.content
      form.value.categoryId = article.categoryId
      isDraft.value = article.isPublished === false
    }
  } catch (error) {
    console.error('加载文章失败:', error)
  } finally {
    loading.value = false
  }
}

const saveDraft = async () => {
  if (!form.value.title) {
    alert('请输入文章标题')
    return
  }
  
  loading.value = true
  try {
    if (isEdit.value) {
      await articleApi.update(route.params.id, {
        ...form.value,
        isPublished: false
      })
    } else {
      await articleApi.create({
        ...form.value,
        isPublished: false
      })
    }
    alert('草稿保存成功！')
  } catch (error) {
    alert('保存失败')
  } finally {
    loading.value = false
  }
}

const previewArticle = () => {
  activeTab.value = 'preview'
}

const publishArticle = async () => {
  if (!form.value.title) {
    alert('请输入文章标题')
    return
  }
  if (!form.value.content) {
    alert('请输入文章内容')
    return
  }
  
  loading.value = true
  try {
    if (isEdit.value) {
      if (isDraft.value) {
        await articleApi.update(route.params.id, {
          ...form.value,
          isPublished: false
        })
        await articleApi.publish(route.params.id)
        alert('文章发布成功！')
      } else {
        await articleApi.update(route.params.id, {
          ...form.value,
          isPublished: true
        })
        alert('文章更新成功！')
      }
    } else {
      await articleApi.create({
        ...form.value,
        isPublished: true
      })
      alert('文章发布成功！')
    }
    router.push('/')
  } catch (error) {
    alert('发布失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadCategories()
  loadArticle()
})
</script>
