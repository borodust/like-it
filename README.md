# Like It

Or not, but this is a service for liking things.

## Requirements
* Docker 18.06+
* docker-compose 3.7+
* Maven 3.6.1+

## Building
```
mvn clean package docker:build
```

## Running

#### Locally
To start service on port 8080 locally:
```bash
cd <project-root>/compose/local/ && docker-compose up -d && cd -
```

To check if it works (after a few seconds):
```bash
curl localhost:8080/get-likes?name=likeit
```