# AI Native CMS

AI 原生内容管理系统（CMS）工程骨架。

## 目标

- 后端：Spring Boot + Java
- 前端：Vue 3 + TypeScript + Vite
- AI 能力：后端抽象 `AiProvider`，支持 OpenAI 兼容 API
- 内容类型：文章、页面、产品、媒体、模板
- 权限体系：JWT 登录 + 角色/权限设计

## 目录结构

- `backend/`：Spring Boot 后端服务
- `frontend/`：Vue 3 管理界面与前台展示
- `docs/`：架构、API、部署文档

## 开始

1. 进入后端：`cd backend`
2. 构建并运行：`mvn spring-boot:run`
3. 进入前端：`cd frontend`
4. 安装依赖：`npm install`
5. 启动开发：`npm run dev`
