import { defineConfig, HttpProxy, ProxyOptions } from 'vite'
import react from '@vitejs/plugin-react-swc'

const PROXY_DEBUG = (proxy: HttpProxy.Server, _options: ProxyOptions) => {
  proxy.on('error', (err, _req, _res) => {
    console.log('proxy error', err);
  });
  proxy.on('proxyReq', (_proxyReq, req, _res) => {
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
      '/api/': {
        target: `http://sports-day-service.${process.env.LOCAL_STACK_HOST}.nip.io:8080`,
        configure: PROXY_DEBUG
      },
    }
  }
})
