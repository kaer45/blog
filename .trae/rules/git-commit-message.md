---
alwaysApply: true
scene: git_message
---

在此处编写规则，自定义 AI 生成提交信息的风格。
# Git Commit Message 提交规范文档
## git-commit-message.md
## 一、概述
每次提交代码必须规范填写 Commit Message，禁止随意填写无意义内容。
统一提交日志规范可解决团队提交信息杂乱、代码追溯困难、Code Review 效率低、版本日志难以自动生成等问题，便于后期维护、问题追踪、版本迭代发布。

## 二、规范制定目的
1. 统一团队 Git 提交信息标准，规范 Code Review 阅读体验；
2. 标准化分支、Tag、Issue 关联规则，打通项目管理流程；
3. 根据提交类型快速过滤日志，精准定位功能、bug、文档、工程改动；
4. 配合自动化工具自动生成版本更新日志 CHANGELOG；
5. 清晰记录每一次变更意图，降低长期项目维护成本。

## 三、完整提交格式
整体分为三部分，**空行分隔**：Header（标题）、Body（正文）、Footer（页脚）
```
<type>(<scope>): <subject>

<Body详细描述>

<Footer备注/关联工单/破坏性变更>
```
### 格式说明
1. Header：必填，控制整体描述，50字符以内；
2. Body：选填，复杂需求/重构/性能优化必须填写，每行不超过72字符；
3. Footer：选填，用于标记破坏性变更、关联关闭需求/缺陷工单。

## 四、Header 头部详细拆解
### 1. type 变更类型（必填，固定枚举）
| 分类 | 类型 | 说明 | 使用场景 |
| ---- | ---- | ---- | -------- |
| 核心变更 | feat | 新增功能 | 新增页面、接口、组件、业务能力 |
| 核心变更 | fix | 缺陷修复 | 修复页面报错、逻辑异常、数据错误、线上bug |
| 重构优化 | refactor | 代码重构 | 无新功能、无bug修复，仅调整代码结构、抽离公共逻辑 |
| 重构优化 | perf | 性能优化 | 页面渲染、接口请求、加载速度、内存优化 |
| 文档类 | docs | 文档修改 | README、接口文档、注释、规范文档、mock说明 |
| 格式类 | style | 代码格式调整 | 空格、缩进、换行、分号、CSS样式，不改动业务逻辑 |
| 测试类 | test | 测试相关 | 新增单元测试、修改测试用例、E2E测试 |
| 工程构建 | build | 构建配置改动 | Vite/Webpack配置、新增依赖、打包脚本、环境变量 |
| 工程构建 | ci | 持续集成配置 | GitLab CI、GitHub Actions、自动化部署流水线 |
| 杂项维护 | chore | 辅助工具/清理 | 修改.gitignore、更新mock数据、清理无用文件、版本微调 |
| 特殊 | revert | 代码回滚 | 撤销历史提交，页脚需标注回滚commit id |
| 初始化 | init | 项目初始化 | 项目首次创建、基础框架搭建 |

### 2. scope 作用域（选填，建议填写）
标识本次修改影响的模块/目录，写在括号内；多模块统一修改可用 `*`。
#### 前端Vue项目常用scope取值
`global`、`api`、`router`、`store`、`components`、`views`、`mock`、`utils`、`vite`、`article`、`category`、`login`

示例：
`fix(mock): 修正评论articleId关联错误`
`feat(article): 新增文章分页列表`

### 3. subject 简短描述（必填）
1. 中文项目统一使用中文描述，动词开头；
2. 简洁说明改动内容，不超过50字符；
3. 结尾不加句号、感叹号；
4. 禁止模糊描述：`改了一点`、`修复bug`、`调整页面`。

错误示例：
`fix: 修复页面bug`
`feat: 改文章列表`

规范示例：
`fix(category): 修复分类统计articleCount数值错误`
`docs: 新增git提交规范文档git-commit-message.md`

## 五、Body 正文规范（复杂提交必填）
用于详细补充变更背景、实现逻辑、风险点，小幅度修改可省略。
书写要点：
1. 说明**为什么修改**，而非只描述改了什么；
2. 逐条列出改动点，使用 `-` 列表；
3. 说明解决方案、潜在副作用、注意事项。

正文需要包含三点信息：
1. 变更必要性：修复bug/新增需求/性能提升；
2. 具体实现方式；
3. 是否存在风险、兼容问题。

示例：
```
refactor(composables): 抽离文章分页通用逻辑
- 将views中重复的分页查询代码抽离为useArticleList
- 统一分页参数处理格式，减少重复代码
- 原有页面无需修改，直接导入hooks使用，无兼容风险
```

## 六、Footer 页脚规范（两种场景）
### 1. 破坏性变更 BREAKING CHANGE
接口字段、参数、返回结构发生不兼容改动，必须标注，升级版本时重点关注。
```
BREAKING CHANGE: 文章接口content字段改为markdownContent，所有文章页面需同步适配
```

### 2. 关联/关闭工单（Jira/禅道/GitLab Issue）
- `Closes #123`：提交后自动关闭编号123需求单
- `Fixes #456`：修复编号456缺陷
```
Closes #89
```

### 3. revert 回滚专用页脚
```
revert: feat(article): 新增文章编辑页面

This reverts commit 3f62d98a7e21f456c89d1234567890abcdef1234.
```

## 七、完整标准示例
### 示例1：简单新增功能（无正文、无页脚）
```
feat(category): 新增分类下拉选择组件
```

### 示例2：修复mock数据bug
```
fix(mock): 修正comments评论关联文章id错误
```

### 示例3：重构代码（带正文）
```
refactor(composables): 抽离表格分页通用逻辑

- 将文章分页查询逻辑抽离为useArticleList
- 删除views内重复请求代码
- 统一分页参数处理格式
```

### 示例4：更新项目文档
```
docs: 新增git提交规范文档git-commit-message.md
```

### 示例5：Vite工程配置修改
```
build(vite): 配置路径别名@指向src目录
```

### 示例6：破坏性接口变更（带Footer）
```
feat(api): 调整文章列表接口返回结构

- 拆分category分类信息为独立对象返回
- 统一分页外层包装 {code, data, msg}

BREAKING CHANGE: 原有list、total层级变更，所有文章页面需同步适配
Closes #89
```

### 示例7：回滚提交
```
revert: feat(category): 新增分类统计图表

This reverts commit 8a32bc7d56ef9012ab34cd56ef7890ab1234567.
```

## 八、禁止的不规范提交
1. 无类型纯文字：`更新代码`、`改页面`、`修复问题`；
2. 一次提交混合多种变更（同时新增功能+修复bug+重构，建议拆分多次commit）；
3. 标题末尾带句号、特殊符号；
4. 描述模糊笼统，无法判断修改范围；
5. 中英文随意混杂，格式错乱。

## 九、配套工具落地方案
### 1. VSCode 插件辅助
安装插件 `git-commit-plugin`，快捷键唤起提交模板，可视化选择type、scope生成规范信息。

### 2. commitizen 交互式提交
交互式命令行生成标准commit，无需手动记忆格式
```bash
# 全局安装
npm install -g commitizen
# 项目内初始化
commitizen init cz-conventional-changelog --save --save-exact
# 提交代码
git cz
```

### 3. husky + validate-commit-msg 强制校验
约束团队所有人提交必须符合规范，格式错误直接阻断提交
```bash
# 安装依赖
npm install validate-commit-msg husky -D
```
package.json 配置：
```json
"husky": {
  "hooks": {
    "commit-msg": "validate-commit-msg"
  }
}
```
根目录新建 `.vcmrc` 自定义校验规则：
```json
{
  "types": ["feat", "fix", "docs", "style", "refactor", "perf", "test", "build", "ci", "chore", "revert", "init"],
  "scope": {
    "required": false,
    "allowed": ["*"],
    "validate": false,
    "multiple": false
  },
  "warnOnFail": false,
  "maxSubjectLength": 100,
  "subjectPattern": ".+",
  "subjectPatternErrorMsg": "提交描述不能为空，请按照规范填写",
  "autoFix": false
}
```

### 4. conventional-changelog 自动生成更新日志
基于规范commit自动产出 CHANGELOG.md 版本更新文档
```bash
# 全局安装
npm install -g conventional-changelog-cli
# 生成更新日志文件
conventional-changelog -p angular -i CHANGELOG.md -s -r 0
```

## 十、使用须知
1. 小改动（一行代码、文案修改）可只写Header；
2. 新增功能、重构、接口改动、性能优化必须补充Body说明；
3. 涉及接口、字段、参数不兼容修改，务必添加BREAKING CHANGE；
4. 所有bug、需求尽量关联对应Issue编号，方便追溯；
5. 禁止大量无关代码合并到同一个commit，保持单次提交单一职责。