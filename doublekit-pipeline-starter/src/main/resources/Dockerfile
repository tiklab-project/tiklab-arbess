FROM jdk
FROM mysql:8.0
COPY . /app
WORKDIR /app
EXPOSE 8080
CMD cd bin;sed -e 's/^M//g' startup.sh > startup.sh ;sh startup.sh