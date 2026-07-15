import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

api.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export const authApi = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data)
}

export const articleApi = {
  list: (params) => api.get('/articles', { params }),
  getById: (id) => api.get(`/articles/${id}`),
  create: (data) => api.post('/articles', data),
  update: (id, data) => api.put(`/articles/${id}`, data),
  delete: (id) => api.delete(`/articles/${id}`),
  publish: (id) => api.post(`/articles/${id}/publish`),
  draft: (id) => api.post(`/articles/${id}/draft`)
}

export const categoryApi = {
  list: () => api.get('/categories'),
  getById: (id) => api.get(`/categories/${id}`),
  create: (data) => api.post('/categories', data),
  update: (id, data) => api.put(`/categories/${id}`, data),
  delete: (id) => api.delete(`/categories/${id}`)
}

export const commentApi = {
  listByArticle: (articleId) => api.get(`/comments/article/${articleId}`),
  create: (data) => api.post('/comments', data),
  delete: (id) => api.delete(`/comments/${id}`)
}

export const userApi = {
  getProfile: () => api.get('/user/profile'),
  updateProfile: (data) => api.put('/user/profile', data),
  getPublicProfile: () => api.get('/user/public'),
  getByUsername: (username) => api.get(`/user/username/${username}`)
}

export const uploadApi = {
  upload: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return api.post('/upload', formData)
  }
}

export const followApi = {
  getStats: (userId) => api.get(`/follow/stats/${userId}`),
  checkFollowing: (followingId) => api.get(`/follow/check/${followingId}`),
  follow: (followingId) => api.post(`/follow/${followingId}`),
  unfollow: (followingId) => api.delete(`/follow/${followingId}`)
}

export const articleLikeApi = {
  countLikes: (articleId) => api.get(`/article-likes/count/${articleId}`),
  checkLiked: (articleId) => api.get(`/article-likes/check/${articleId}`),
  like: (articleId) => api.post(`/article-likes/${articleId}`),
  unlike: (articleId) => api.delete(`/article-likes/${articleId}`),
  getLikedArticles: () => api.get('/article-likes/user')
}

export const articleFavoriteApi = {
  countFavorites: (articleId) => api.get(`/article-favorites/count/${articleId}`),
  checkFavorited: (articleId) => api.get(`/article-favorites/check/${articleId}`),
  favorite: (articleId) => api.post(`/article-favorites/${articleId}`),
  unfavorite: (articleId) => api.delete(`/article-favorites/${articleId}`),
  getFavoritedArticles: () => api.get('/article-favorites/user')
}

export const commentLikeApi = {
  countLikes: (commentId) => api.get(`/comment-likes/count/${commentId}`),
  checkLiked: (commentId) => api.get(`/comment-likes/check/${commentId}`),
  like: (commentId) => api.post(`/comment-likes/${commentId}`),
  unlike: (commentId) => api.delete(`/comment-likes/${commentId}`)
}

export const contactApi = {
  create: (data) => api.post('/contact', data),
  list: () => api.get('/contact'),
  getById: (id) => api.get(`/contact/${id}`),
  reply: (id, data) => api.put(`/contact/${id}/reply`, data),
  delete: (id) => api.delete(`/contact/${id}`)
}

export default api