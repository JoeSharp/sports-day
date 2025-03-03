FROM nginx:latest
COPY dist /usr/share/nginx/html
COPY default.template.conf /etc/nginx/templates/default.conf.template
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
  CMD curl -f http://localhost/ || exit 1	    
