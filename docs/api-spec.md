# API 规范

## 健康检查

- GET `/api/health`

响应示例：

```json
{
  "status": "ok",
  "service": "ai-native-cms"
}
```

## AI 编辑助手

- POST `/api/ai/editor/summary`
  - 请求：`{ "title": "...", "content": "..." }`
  - 响应：`{ "text": "..." }`
