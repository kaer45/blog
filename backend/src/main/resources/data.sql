INSERT INTO `user` (`username`, `password`, `email`, `avatar`, `nickname`, `bio`, `created_at`, `updated_at`) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 'admin@example.com', '', '博客作者', '热爱技术，分享生活', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
('reader_a', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 'reader_a@example.com', '', '读者A', '', '2024-01-10 10:00:00', '2024-01-10 10:00:00'),
('reader_b', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 'reader_b@example.com', '', '读者B', '', '2024-01-11 10:00:00', '2024-01-11 10:00:00'),
('reader_c', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 'reader_c@example.com', '', '读者C', '', '2024-01-12 10:00:00', '2024-01-12 10:00:00'),
('reader_d', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 'reader_d@example.com', '', '读者D', '', '2024-01-13 10:00:00', '2024-01-13 10:00:00'),
('reader_e', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 'reader_e@example.com', '', '读者E', '', '2024-01-14 10:00:00', '2024-01-14 10:00:00');

INSERT INTO `category` (`name`, `description`, `article_count`, `created_at`, `updated_at`) VALUES
('前端开发', 'Vue、React、JavaScript等前端技术分享', 12, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
('后端开发', 'Spring Boot、Node.js等后端技术', 8, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
('数据库', 'MySQL、Redis、MongoDB等数据库技术', 5, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
('工具推荐', '开发工具、效率工具推荐', 6, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
('生活感悟', '生活随笔、读书心得', 10, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
('学习笔记', '技术学习笔记整理', 15, '2024-01-01 10:00:00', '2024-01-01 10:00:00');

INSERT INTO `article` (`title`, `summary`, `content`, `author_id`, `category_id`, `view_count`, `like_count`, `comment_count`, `is_published`, `created_at`, `updated_at`, `published_at`) VALUES
('Vue3 组合式API入门指南', '本文详细介绍了Vue3组合式API的基本用法', '# Vue3 组合式API入门指南\n\n## 什么是组合式API\n\n组合式API是Vue3引入的一种新的API风格。\n\n### setup\n\nsetup是组合式API的入口函数。\n\n### ref\n\nref用于创建响应式的基本类型数据。\n\n### reactive\n\nreactive用于创建响应式对象。', 1, 1, 1234, 89, 23, 1, '2024-01-15 10:30:00', '2024-01-15 10:30:00', '2024-01-15 10:30:00'),
('Spring Boot 3.0 新特性详解', 'Spring Boot 3.0带来了许多新特性', '# Spring Boot 3.0 新特性详解\n\n## 最低Java版本要求\n\nSpring Boot 3.0要求Java 17或更高版本。\n\n### 虚拟线程支持\n\n支持Java 21的虚拟线程。\n\n### GraalVM原生镜像支持\n\n改进了对GraalVM原生镜像的支持。', 1, 2, 856, 67, 15, 1, '2024-01-14 14:20:00', '2024-01-14 14:20:00', '2024-01-14 14:20:00'),
('MySQL索引优化实战', '深入理解MySQL索引原理', '# MySQL索引优化实战\n\n## 索引类型\n\n### B-Tree索引\n\n最常见的索引类型，适用于范围查询。\n\n## 索引优化技巧\n\n### 1. 选择合适的索引列\n\n### 2. 复合索引顺序\n\n遵循最左前缀原则。', 1, 3, 2156, 134, 34, 1, '2024-01-13 09:15:00', '2024-01-13 09:15:00', '2024-01-13 09:15:00'),
('2024年最值得学习的前端技术', '展望2024年前端技术趋势', '# 2024年最值得学习的前端技术\n\n## 趋势分析\n\n### Vue3 + TypeScript\n\nVue3的组合式API越来越成熟。\n\n### React Server Components\n\nReact 18引入的服务端组件。\n\n### WebAssembly\n\n在前端的应用越来越广泛。', 1, 1, 3421, 256, 67, 1, '2024-01-12 16:45:00', '2024-01-12 16:45:00', '2024-01-12 16:45:00'),
('阅读《代码整洁之道》有感', '分享阅读心得体会', '# 阅读《代码整洁之道》有感\n\n## 核心观点\n\n代码质量与其整洁度成正比。\n\n### 命名规范\n\n使用描述性的名称。\n\n### 函数设计\n\n函数应该短小，只做一件事。', 1, 5, 678, 45, 12, 1, '2024-01-11 11:30:00', '2024-01-11 11:30:00', '2024-01-11 11:30:00'),
('Docker入门到实践', '从零开始学习Docker容器技术', '# Docker入门到实践\n\n## 什么是Docker\n\nDocker是一个开源的容器化平台。\n\n### 镜像\n\n只读的模板，包含运行应用所需的所有文件。\n\n### 容器\n\n镜像的运行实例。', 1, 4, 1890, 178, 45, 1, '2024-01-10 15:00:00', '2024-01-10 15:00:00', '2024-01-10 15:00:00'),
('Git工作流最佳实践', '介绍常用的Git工作流', '# Git工作流最佳实践\n\n## Git Flow\n\n经典的Git工作流，适合大型团队。\n\n## Trunk Based Development\n\n简单直接的工作流，适合小团队。', 1, 4, 1567, 123, 32, 1, '2024-01-09 13:20:00', '2024-01-09 13:20:00', '2024-01-09 13:20:00'),
('Redis缓存策略详解', '深入理解Redis缓存策略', '# Redis缓存策略详解\n\n## 缓存基本流程\n\n请求 -> 缓存 -> 命中 -> 返回\n\n## 缓存问题\n\n### 缓存穿透\n\n查询不存在的数据。\n\n### 缓存击穿\n\n热点key过期。\n\n### 缓存雪崩\n\n大量key同时过期。', 1, 3, 2345, 189, 56, 1, '2024-01-08 10:45:00', '2024-01-08 10:45:00', '2024-01-08 10:45:00');

INSERT INTO `comment` (`article_id`, `author_id`, `parent_id`, `content`, `created_at`, `updated_at`) VALUES
(1, 2, NULL, '非常详细的教程，感谢分享！', '2024-01-15 11:00:00', '2024-01-15 11:00:00'),
(1, 3, NULL, '组合式API确实比选项式API更灵活', '2024-01-15 12:30:00', '2024-01-15 12:30:00'),
(2, 5, NULL, '升级到3.0需要注意什么？', '2024-01-14 15:00:00', '2024-01-14 15:00:00'),
(3, 6, NULL, '索引优化真的很重要，之前踩过坑', '2024-01-13 10:00:00', '2024-01-13 10:00:00');