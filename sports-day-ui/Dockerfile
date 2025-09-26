# Stage 1: Build - Have to be SUPER specific with the build image, since things keep going pear shaped
FROM node@sha256:66ccf8d19d2efc6c770027baf508aa5ee9f164dcecd2f73d7fd4fe55c7688173 AS builder
WORKDIR /app
COPY package.json ./
RUN npm install
RUN npm rebuild esbuild
COPY . .
RUN npm run build

# Stage 2: Runtime
FROM nginx:1.27.4-alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY default.template.conf /etc/nginx/templates/default.conf.template
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
  CMD curl -f http://localhost/ || exit 1	    
