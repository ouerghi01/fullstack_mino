# ================================
# Stage 1 — Build Angular App
# ================================
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install --legacy-peer-deps --loglevel=verbose

COPY . .

ENV NODE_OPTIONS="--max-old-space-size=4096"
RUN npm install -g @angular/cli --legacy-peer-deps --loglevel=verbose
RUN npm run build -- --configuration production


# ================================
# Stage 2 — Serve with Nginx
# ================================
FROM nginx:stable-alpine

# Create non-root user
RUN addgroup -g 1001 appgroup && \
    adduser -u 1001 -G appgroup -D appuser

# Prepare Nginx directories
RUN mkdir -p /tmp/nginx /usr/share/nginx/html /var/cache/nginx && \
    chown -R appuser:appgroup /tmp/nginx /usr/share/nginx/html /var/cache/nginx && \
    sed -i '/pid\s/c\pid /tmp/nginx.pid;' /etc/nginx/nginx.conf && \
    sed -i '/^user/d' /etc/nginx/nginx.conf

# ✅ Copy the correct build output
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Copy your Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

USER appuser

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
