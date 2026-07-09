
export const categories = [
  { id: 1, name: '前端开发', description: 'Vue、React、JavaScript等前端技术分享', articleCount: 12, icon: '🎨' },
  { id: 2, name: '后端开发', description: 'Spring Boot、Node.js等后端技术', articleCount: 8, icon: '⚙️' },
  { id: 3, name: '数据库', description: 'MySQL、Redis、MongoDB等数据库技术', articleCount: 5, icon: '📊' },
  { id: 4, name: '工具推荐', description: '开发工具、效率工具推荐', articleCount: 6, icon: '🛠️' },
  { id: 5, name: '生活感悟', description: '生活随笔、读书心得', articleCount: 10, icon: '📝' },
  { id: 6, name: '学习笔记', description: '技术学习笔记整理', articleCount: 15, icon: '📚' }
]

export const tags = [
  'Vue3', 'React', 'TypeScript', 'Spring Boot', 'MySQL', 'Redis', 
  'Docker', 'Git', 'VS Code', 'Node.js', '前端', '后端', '算法', '面试'
]

export const articles = [
  {
    id: 1,
    title: 'Vue3 组合式API入门指南',
    summary: '本文详细介绍了Vue3组合式API的基本用法，包括setup、ref、reactive、computed等核心概念。',
    content: '# Vue3 组合式API入门指南\n\n## 什么是组合式API\n\n组合式API（Composition API）是Vue3引入的一种新的API风格，它允许我们使用函数来组织组件逻辑，而不是选项对象。\n\n## 核心概念\n\n### setup\n\n`setup`是组合式API的入口函数，在组件创建之前执行。\n\n```javascript\nexport default {\n  setup() {\n    // 在这里编写组合式API代码\n  }\n}\n```\n\n### ref\n\n`ref`用于创建响应式的基本类型数据。\n\n```javascript\nimport { ref } from \'vue\'\n\nconst count = ref(0)\n\nconsole.log(count.value) // 0\ncount.value++\nconsole.log(count.value) // 1\n```\n\n### reactive\n\n```javascript\nimport { reactive } from \'vue\'\n\nconst state = reactive({\n  count: 0,\n  name: \'Vue3\'\n})\n\nstate.count++ // 不需要.value\n```\n\n## 总结\n\n组合式API提供了更灵活的代码组织方式，特别适合复杂组件的逻辑复用。',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 1,
    viewCount: 1234,
    likeCount: 89,
    commentCount: 23,
    isPublished: true,
    createdAt: '2024-01-15T10:30:00',
    updatedAt: '2024-01-15T10:30:00',
    publishedAt: '2024-01-15T10:30:00'
  },
  {
    id: 2,
    title: 'Spring Boot 3.0 新特性详解',
    summary: 'Spring Boot 3.0带来了许多新特性，本文将详细介绍这些变化和升级注意事项。',
    content: '# Spring Boot 3.0 新特性详解\n\n## 最低Java版本要求\n\nSpring Boot 3.0要求Java 17或更高版本。\n\n## 主要新特性\n\n### 虚拟线程支持\n\nSpring Boot 3.0支持Java 21的虚拟线程。\n\n```java\n@Configuration\npublic class VirtualThreadConfig {\n    @Bean\n    public ExecutorService virtualThreadExecutor() {\n        return Executors.newVirtualThreadPerTaskExecutor();\n    }\n}\n```\n\n### GraalVM原生镜像支持\n\n改进了对GraalVM原生镜像的支持，启动速度更快。\n\n### 移除的特性\n\n- 移除了对Java 8和11的支持\n- 移除了一些废弃的API\n\n## 升级建议\n\n建议逐步升级，先升级到Spring Boot 2.7，解决所有废弃警告，再升级到3.0。',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 2,
    viewCount: 856,
    likeCount: 67,
    commentCount: 15,
    isPublished: true,
    createdAt: '2024-01-14T14:20:00',
    updatedAt: '2024-01-14T14:20:00',
    publishedAt: '2024-01-14T14:20:00'
  },
  {
    id: 3,
    title: 'MySQL索引优化实战',
    summary: '深入理解MySQL索引原理，掌握索引优化技巧，提升数据库查询性能。',
    content: '# MySQL索引优化实战\n\n## 索引类型\n\n### B-Tree索引\n\n最常见的索引类型，适用于范围查询。\n\n### Hash索引\n\n适用于等值查询，不支持范围查询。\n\n## 索引优化技巧\n\n### 1. 选择合适的索引列\n\n```sql\n-- 好的索引\nCREATE INDEX idx_user_name ON users(name);\n\n-- 不好的索引（低选择性）\nCREATE INDEX idx_user_gender ON users(gender);\n```\n\n### 2. 复合索引顺序\n\n遵循最左前缀原则。\n\n```sql\nCREATE INDEX idx_user_name_age ON users(name, age);\n-- 可以使用索引的查询\nWHERE name = \'张三\'\nWHERE name = \'张三\' AND age = 25\n-- 不能使用索引的查询\nWHERE age = 25\n```\n\n## 总结\n\n合理使用索引可以显著提升查询性能，但也要注意索引维护的成本。',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 3,
    viewCount: 2156,
    likeCount: 134,
    commentCount: 34,
    isPublished: true,
    createdAt: '2024-01-13T09:15:00',
    updatedAt: '2024-01-13T09:15:00',
    publishedAt: '2024-01-13T09:15:00'
  },
  {
    id: 4,
    title: '2024年最值得学习的前端技术',
    summary: '展望2024年前端技术趋势，推荐最值得学习的技术栈。',
    content: '# 2024年最值得学习的前端技术\n\n## 趋势分析\n\n### 1. Vue3 + TypeScript\n\nVue3的组合式API越来越成熟，TypeScript已经成为前端开发的标配。\n\n### 2. React Server Components\n\nReact 18引入的服务端组件，改变了前端开发模式。\n\n### 3. WebAssembly\n\nWebAssembly在前端的应用越来越广泛，特别是在性能敏感的场景。\n\n## 学习路径\n\n```\n基础: HTML/CSS/JavaScript\n框架: Vue3或React\n语言: TypeScript\n构建: Vite或Webpack\n状态管理: Pinia或Zustand\n```\n\n## 总结\n\n保持学习，跟上技术发展的步伐！',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 1,
    viewCount: 3421,
    likeCount: 256,
    commentCount: 67,
    isPublished: true,
    createdAt: '2024-01-12T16:45:00',
    updatedAt: '2024-01-12T16:45:00',
    publishedAt: '2024-01-12T16:45:00'
  },
  {
    id: 5,
    title: '阅读《代码整洁之道》有感',
    summary: '分享阅读《代码整洁之道》的心得体会，谈谈对代码质量的理解。',
    content: '# 阅读《代码整洁之道》有感\n\n## 核心观点\n\n### 代码质量\n\n"代码质量与其整洁度成正比。干净的代码，既在质量上较为可靠，也为后期维护、升级奠定了良好基础。"\n\n### 命名规范\n\n- 使用描述性的名称\n- 名称应该能表达它的用途\n- 避免使用缩写\n\n### 函数设计\n\n- 函数应该短小\n- 函数应该只做一件事\n- 函数参数越少越好\n\n## 个人感悟\n\n这本书让我重新审视了自己的代码习惯，开始注重代码的可读性和可维护性。\n\n## 推荐\n\n强烈推荐给所有程序员阅读！',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 5,
    viewCount: 678,
    likeCount: 45,
    commentCount: 12,
    isPublished: true,
    createdAt: '2024-01-11T11:30:00',
    updatedAt: '2024-01-11T11:30:00',
    publishedAt: '2024-01-11T11:30:00'
  },
  {
    id: 6,
    title: 'Docker入门到实践',
    summary: '从零开始学习Docker容器技术，掌握容器化部署的基本技能。',
    content: '# Docker入门到实践\n\n## 什么是Docker\n\nDocker是一个开源的容器化平台，可以将应用程序及其依赖打包成一个容器。\n\n## 核心概念\n\n### 镜像（Image）\n\n只读的模板，包含运行应用所需的所有文件。\n\n### 容器（Container）\n\n镜像的运行实例。\n\n### Dockerfile\n\n定义如何构建镜像的文件。\n\n## 基本命令\n\n```bash\n# 拉取镜像\ndocker pull nginx\n\n# 运行容器\ndocker run -d -p 80:80 nginx\n\n# 查看运行中的容器\ndocker ps\n\n# 查看所有容器\ndocker ps -a\n\n# 停止容器\ndocker stop <container_id>\n\n# 删除容器\ndocker rm <container_id>\n```\n\n## Dockerfile示例\n\n```dockerfile\nFROM node:18-alpine\nWORKDIR /app\nCOPY package*.json ./\nRUN npm install\nCOPY . .\nEXPOSE 3000\nCMD ["npm", "start"]\n```\n\n## 总结\n\nDocker大大简化了应用的部署和运维。',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 4,
    viewCount: 1890,
    likeCount: 178,
    commentCount: 45,
    isPublished: true,
    createdAt: '2024-01-10T15:00:00',
    updatedAt: '2024-01-10T15:00:00',
    publishedAt: '2024-01-10T15:00:00'
  },
  {
    id: 7,
    title: 'Git工作流最佳实践',
    summary: '介绍常用的Git工作流，帮助团队更好地协作开发。',
    content: '# Git工作流最佳实践\n\n## Git Flow\n\n经典的Git工作流，适合大型团队。\n\n### 分支类型\n\n- main: 主分支\n- develop: 开发分支\n- feature/*: 功能分支\n- release/*: 发布分支\n- hotfix/*: 修复分支\n\n## Trunk Based Development\n\n简单直接的工作流，适合小团队或敏捷开发。\n\n### 特点\n\n- 所有人在同一个分支上开发\n- 使用短生命周期的特性分支\n- 频繁提交和合并\n\n## 提交规范\n\n使用Conventional Commits规范：\n\n```\nfeat: 新增功能\nfix: 修复bug\ndocs: 更新文档\nstyle: 代码格式调整\nrefactor: 代码重构\ntest: 添加测试\nchore: 构建或工具更新\n```\n\n## 总结\n\n选择适合团队规模和项目特点的工作流。',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 4,
    viewCount: 1567,
    likeCount: 123,
    commentCount: 32,
    isPublished: true,
    createdAt: '2024-01-09T13:20:00',
    updatedAt: '2024-01-09T13:20:00',
    publishedAt: '2024-01-09T13:20:00'
  },
  {
    id: 8,
    title: 'Redis缓存策略详解',
    summary: '深入理解Redis缓存策略，包括缓存穿透、缓存击穿、缓存雪崩等问题的解决方案。',
    content: '# Redis缓存策略详解\n\n## 缓存基本流程\n\n```\n请求 -> 缓存 -> 命中 -> 返回\n请求 -> 缓存 -> 未命中 -> 数据库 -> 更新缓存 -> 返回\n```\n\n## 缓存问题\n\n### 缓存穿透\n\n查询不存在的数据，导致请求直接打到数据库。\n\n**解决方案:**\n- 缓存空值\n- 使用布隆过滤器\n\n### 缓存击穿\n\n热点key过期，大量请求同时打到数据库。\n\n**解决方案:**\n- 设置热点key永不过期\n- 使用互斥锁\n\n### 缓存雪崩\n\n大量key同时过期，导致数据库压力骤增。\n\n**解决方案:**\n- 设置随机过期时间\n- 使用多级缓存\n\n## 缓存一致性\n\n### 方案一：先更新数据库，再删除缓存\n\n### 方案二：先删除缓存，再更新数据库\n\n## 总结\n\n合理的缓存策略可以显著提升系统性能。',
    authorId: 1,
    authorName: '博客作者',
    categoryId: 3,
    viewCount: 2345,
    likeCount: 189,
    commentCount: 56,
    isPublished: true,
    createdAt: '2024-01-08T10:45:00',
    updatedAt: '2024-01-08T10:45:00',
    publishedAt: '2024-01-08T10:45:00'
  }
]

export const latestArticles = articles.slice(0, 5)

export const comments = [
  {
    id: 1,
    articleId: 1,
    authorId: 2,
    authorName: '读者A',
    parentId: null,
    content: '非常详细的教程，感谢分享！',
    createdAt: '2024-01-15T11:00:00',
    updatedAt: '2024-01-15T11:00:00'
  },
  {
    id: 2,
    articleId: 1,
    authorId: 3,
    authorName: '读者B',
    parentId: null,
    content: '组合式API确实比选项式API更灵活',
    createdAt: '2024-01-15T12:30:00',
    updatedAt: '2024-01-15T12:30:00'
  },
  {
    id: 3,
    articleId: 1,
    authorId: 4,
    authorName: '读者C',
    parentId: 1,
    content: '请问reactive和ref有什么区别？',
    createdAt: '2024-01-15T14:00:00',
    updatedAt: '2024-01-15T14:00:00'
  },
  {
    id: 4,
    articleId: 2,
    authorId: 5,
    authorName: '读者D',
    parentId: null,
    content: '升级到3.0需要注意什么？',
    createdAt: '2024-01-14T15:00:00',
    updatedAt: '2024-01-14T15:00:00'
  },
  {
    id: 5,
    articleId: 3,
    authorId: 6,
    authorName: '读者E',
    parentId: null,
    content: '索引优化真的很重要，之前踩过坑',
    createdAt: '2024-01-13T10:00:00',
    updatedAt: '2024-01-13T10:00:00'
  }
]
