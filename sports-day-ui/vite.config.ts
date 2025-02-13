import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

const PROXY_DEBUG = (proxy, _options) => {
  proxy.on('error', (err, _req, _res) => {
    console.log('proxy error', err);
  });
  proxy.on('proxyReq', (proxyReq, req, _res) => {
    console.log('Sending Request to the Target:', req.method, req.url);
  });
  proxy.on('proxyRes', (proxyRes, req, _res) => {
    console.log('Received Response from the Target:', proxyRes.statusCode, req.url);
  });
};


// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  preview: {
    port: 9080,
    strictPort: true,
  },
  server: {
    proxy: {
      '/auth/': {
        target: "http://localhost:8085",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/auth/, ''),
        configure: PROXY_DEBUG      
      },
      '/api/': {
        target: "http://localhost:8080",
        rewrite: (path) => path.replace(/^\/api/, ''),
        configure: PROXY_DEBUG      
      },
    }
  }
})
