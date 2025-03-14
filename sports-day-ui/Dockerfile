# Stage 1: Build
FROM node:20 AS builder
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Runtime
FROM nginx:latest
COPY --from=builder /app/dist /usr/share/nginx/html
COPY default.template.conf /etc/nginx/templates/default.conf.template
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
  CMD curl -f http://localhost/ || exit 1	    
