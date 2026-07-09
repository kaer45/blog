const e=[{id:1,name:"前端开发",description:"Vue、React、JavaScript等前端技术分享",articleCount:12,icon:"🎨"},{id:2,name:"后端开发",description:"Spring Boot、Node.js等后端技术",articleCount:8,icon:"⚙️"},{id:3,name:"数据库",description:"MySQL、Redis、MongoDB等数据库技术",articleCount:5,icon:"📊"},{id:4,name:"工具推荐",description:"开发工具、效率工具推荐",articleCount:6,icon:"🛠️"},{id:5,name:"生活感悟",description:"生活随笔、读书心得",articleCount:10,icon:"📝"},{id:6,name:"学习笔记",description:"技术学习笔记整理",articleCount:15,icon:"📚"}],t=["Vue3","React","TypeScript","Spring Boot","MySQL","Redis","Docker","Git","VS Code","Node.js","前端","后端","算法","面试"],n=[{id:1,title:"Vue3 组合式API入门指南",summary:"本文详细介绍了Vue3组合式API的基本用法，包括setup、ref、reactive、computed等核心概念。",content:`# Vue3 组合式API入门指南

## 什么是组合式API

组合式API（Composition API）是Vue3引入的一种新的API风格，它允许我们使用函数来组织组件逻辑，而不是选项对象。

## 核心概念

### setup

\`setup\`是组合式API的入口函数，在组件创建之前执行。

\`\`\`javascript
export default {
  setup() {
    // 在这里编写组合式API代码
  }
}
\`\`\`

### ref

\`ref\`用于创建响应式的基本类型数据。

\`\`\`javascript
import { ref } from 'vue'

const count = ref(0)

console.log(count.value) // 0
count.value++
console.log(count.value) // 1
\`\`\`

### reactive

\`\`\`javascript
import { reactive } from 'vue'

const state = reactive({
  count: 0,
  name: 'Vue3'
})

state.count++ // 不需要.value
\`\`\`

## 总结

组合式API提供了更灵活的代码组织方式，特别适合复杂组件的逻辑复用。`,authorId:1,authorName:"博客作者",categoryId:1,viewCount:1234,likeCount:89,commentCount:23,isPublished:!0,createdAt:"2024-01-15T10:30:00",updatedAt:"2024-01-15T10:30:00",publishedAt:"2024-01-15T10:30:00"},{id:2,title:"Spring Boot 3.0 新特性详解",summary:"Spring Boot 3.0带来了许多新特性，本文将详细介绍这些变化和升级注意事项。",content:`# Spring Boot 3.0 新特性详解

## 最低Java版本要求

Spring Boot 3.0要求Java 17或更高版本。

## 主要新特性

### 虚拟线程支持

Spring Boot 3.0支持Java 21的虚拟线程。

\`\`\`java
@Configuration
public class VirtualThreadConfig {
    @Bean
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
\`\`\`

### GraalVM原生镜像支持

改进了对GraalVM原生镜像的支持，启动速度更快。

### 移除的特性

- 移除了对Java 8和11的支持
- 移除了一些废弃的API

## 升级建议

建议逐步升级，先升级到Spring Boot 2.7，解决所有废弃警告，再升级到3.0。`,authorId:1,authorName:"博客作者",categoryId:2,viewCount:856,likeCount:67,commentCount:15,isPublished:!0,createdAt:"2024-01-14T14:20:00",updatedAt:"2024-01-14T14:20:00",publishedAt:"2024-01-14T14:20:00"},{id:3,title:"MySQL索引优化实战",summary:"深入理解MySQL索引原理，掌握索引优化技巧，提升数据库查询性能。",content:`# MySQL索引优化实战

## 索引类型

### B-Tree索引

最常见的索引类型，适用于范围查询。

### Hash索引

适用于等值查询，不支持范围查询。

## 索引优化技巧

### 1. 选择合适的索引列

\`\`\`sql
-- 好的索引
CREATE INDEX idx_user_name ON users(name);

-- 不好的索引（低选择性）
CREATE INDEX idx_user_gender ON users(gender);
\`\`\`

### 2. 复合索引顺序

遵循最左前缀原则。

\`\`\`sql
CREATE INDEX idx_user_name_age ON users(name, age);
-- 可以使用索引的查询
WHERE name = '张三'
WHERE name = '张三' AND age = 25
-- 不能使用索引的查询
WHERE age = 25
\`\`\`

## 总结

合理使用索引可以显著提升查询性能，但也要注意索引维护的成本。`,authorId:1,authorName:"博客作者",categoryId:3,viewCount:2156,likeCount:134,commentCount:34,isPublished:!0,createdAt:"2024-01-13T09:15:00",updatedAt:"2024-01-13T09:15:00",publishedAt:"2024-01-13T09:15:00"},{id:4,title:"2024年最值得学习的前端技术",summary:"展望2024年前端技术趋势，推荐最值得学习的技术栈。",content:`# 2024年最值得学习的前端技术

## 趋势分析

### 1. Vue3 + TypeScript

Vue3的组合式API越来越成熟，TypeScript已经成为前端开发的标配。

### 2. React Server Components

React 18引入的服务端组件，改变了前端开发模式。

### 3. WebAssembly

WebAssembly在前端的应用越来越广泛，特别是在性能敏感的场景。

## 学习路径

\`\`\`
基础: HTML/CSS/JavaScript
框架: Vue3或React
语言: TypeScript
构建: Vite或Webpack
状态管理: Pinia或Zustand
\`\`\`

## 总结

保持学习，跟上技术发展的步伐！`,authorId:1,authorName:"博客作者",categoryId:1,viewCount:3421,likeCount:256,commentCount:67,isPublished:!0,createdAt:"2024-01-12T16:45:00",updatedAt:"2024-01-12T16:45:00",publishedAt:"2024-01-12T16:45:00"},{id:5,title:"阅读《代码整洁之道》有感",summary:"分享阅读《代码整洁之道》的心得体会，谈谈对代码质量的理解。",content:`# 阅读《代码整洁之道》有感

## 核心观点

### 代码质量

"代码质量与其整洁度成正比。干净的代码，既在质量上较为可靠，也为后期维护、升级奠定了良好基础。"

### 命名规范

- 使用描述性的名称
- 名称应该能表达它的用途
- 避免使用缩写

### 函数设计

- 函数应该短小
- 函数应该只做一件事
- 函数参数越少越好

## 个人感悟

这本书让我重新审视了自己的代码习惯，开始注重代码的可读性和可维护性。

## 推荐

强烈推荐给所有程序员阅读！`,authorId:1,authorName:"博客作者",categoryId:5,viewCount:678,likeCount:45,commentCount:12,isPublished:!0,createdAt:"2024-01-11T11:30:00",updatedAt:"2024-01-11T11:30:00",publishedAt:"2024-01-11T11:30:00"},{id:6,title:"Docker入门到实践",summary:"从零开始学习Docker容器技术，掌握容器化部署的基本技能。",content:`# Docker入门到实践

## 什么是Docker

Docker是一个开源的容器化平台，可以将应用程序及其依赖打包成一个容器。

## 核心概念

### 镜像（Image）

只读的模板，包含运行应用所需的所有文件。

### 容器（Container）

镜像的运行实例。

### Dockerfile

定义如何构建镜像的文件。

## 基本命令

\`\`\`bash
# 拉取镜像
docker pull nginx

# 运行容器
docker run -d -p 80:80 nginx

# 查看运行中的容器
docker ps

# 查看所有容器
docker ps -a

# 停止容器
docker stop <container_id>

# 删除容器
docker rm <container_id>
\`\`\`

## Dockerfile示例

\`\`\`dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
\`\`\`

## 总结

Docker大大简化了应用的部署和运维。`,authorId:1,authorName:"博客作者",categoryId:4,viewCount:1890,likeCount:178,commentCount:45,isPublished:!0,createdAt:"2024-01-10T15:00:00",updatedAt:"2024-01-10T15:00:00",publishedAt:"2024-01-10T15:00:00"},{id:7,title:"Git工作流最佳实践",summary:"介绍常用的Git工作流，帮助团队更好地协作开发。",content:`# Git工作流最佳实践

## Git Flow

经典的Git工作流，适合大型团队。

### 分支类型

- main: 主分支
- develop: 开发分支
- feature/*: 功能分支
- release/*: 发布分支
- hotfix/*: 修复分支

## Trunk Based Development

简单直接的工作流，适合小团队或敏捷开发。

### 特点

- 所有人在同一个分支上开发
- 使用短生命周期的特性分支
- 频繁提交和合并

## 提交规范

使用Conventional Commits规范：

\`\`\`
feat: 新增功能
fix: 修复bug
docs: 更新文档
style: 代码格式调整
refactor: 代码重构
test: 添加测试
chore: 构建或工具更新
\`\`\`

## 总结

选择适合团队规模和项目特点的工作流。`,authorId:1,authorName:"博客作者",categoryId:4,viewCount:1567,likeCount:123,commentCount:32,isPublished:!0,createdAt:"2024-01-09T13:20:00",updatedAt:"2024-01-09T13:20:00",publishedAt:"2024-01-09T13:20:00"},{id:8,title:"Redis缓存策略详解",summary:"深入理解Redis缓存策略，包括缓存穿透、缓存击穿、缓存雪崩等问题的解决方案。",content:`# Redis缓存策略详解

## 缓存基本流程

\`\`\`
请求 -> 缓存 -> 命中 -> 返回
请求 -> 缓存 -> 未命中 -> 数据库 -> 更新缓存 -> 返回
\`\`\`

## 缓存问题

### 缓存穿透

查询不存在的数据，导致请求直接打到数据库。

**解决方案:**
- 缓存空值
- 使用布隆过滤器

### 缓存击穿

热点key过期，大量请求同时打到数据库。

**解决方案:**
- 设置热点key永不过期
- 使用互斥锁

### 缓存雪崩

大量key同时过期，导致数据库压力骤增。

**解决方案:**
- 设置随机过期时间
- 使用多级缓存

## 缓存一致性

### 方案一：先更新数据库，再删除缓存

### 方案二：先删除缓存，再更新数据库

## 总结

合理的缓存策略可以显著提升系统性能。`,authorId:1,authorName:"博客作者",categoryId:3,viewCount:2345,likeCount:189,commentCount:56,isPublished:!0,createdAt:"2024-01-08T10:45:00",updatedAt:"2024-01-08T10:45:00",publishedAt:"2024-01-08T10:45:00"}],a=n.slice(0,5),o=[{id:1,articleId:1,authorId:2,authorName:"读者A",parentId:null,content:"非常详细的教程，感谢分享！",createdAt:"2024-01-15T11:00:00",updatedAt:"2024-01-15T11:00:00"},{id:2,articleId:1,authorId:3,authorName:"读者B",parentId:null,content:"组合式API确实比选项式API更灵活",createdAt:"2024-01-15T12:30:00",updatedAt:"2024-01-15T12:30:00"},{id:3,articleId:1,authorId:4,authorName:"读者C",parentId:1,content:"请问reactive和ref有什么区别？",createdAt:"2024-01-15T14:00:00",updatedAt:"2024-01-15T14:00:00"},{id:4,articleId:2,authorId:5,authorName:"读者D",parentId:null,content:"升级到3.0需要注意什么？",createdAt:"2024-01-14T15:00:00",updatedAt:"2024-01-14T15:00:00"},{id:5,articleId:3,authorId:6,authorName:"读者E",parentId:null,content:"索引优化真的很重要，之前踩过坑",createdAt:"2024-01-13T10:00:00",updatedAt:"2024-01-13T10:00:00"}];export{n as a,o as b,e as c,a as l,t};
