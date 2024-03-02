import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    server: {
        proxy: {
            // 获取路径中包含 /api 的请求
            '/api': {
                target: "http://localhost:8080", // 后端服务地址
                changeOrigin: true, // 修改源，修改后请求路径变为 http://localhost:8080/api/
                rewrite: (path) => path.replace(/^\/api/, "") // 将 /api 替换为空字符串，请求路径变为 http://localhost:8080/
            }
        }
    }
})
