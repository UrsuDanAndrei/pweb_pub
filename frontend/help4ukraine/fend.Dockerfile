# Stage 1

FROM node:latest AS build
#RUN npm -v
#RUN npm install -g npm@8.10.0
#RUN npm -v
WORKDIR /app
COPY . .
#RUN npm install --force
#RUN npm run build --prod


# Stage 2

FROM nginx:1.17.1-alpine
#COPY --from=build /app/dist/help4ukraine/ /usr/share/nginx/html
EXPOSE 4200
EXPOSE 80
EXPOSE 8080
