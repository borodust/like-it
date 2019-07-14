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

## Architecture

#### Overview
Service cluster composed of two subclusters:
* Scalable worker cluster of `borodust/like-it` images
* Scalable redis cluster as a database backend

Worker cluster can be deployed via docker swarm/stack/services
which provide load-balanced service out of the box. 
Each worker node can utilies multiple cores allowing it to scale vertically too.
No automation provided for setting up a Redis cluster as of yet.

With proper setup of Redis Cluster this solution provides vertically and horizontally scalable,
performant service with availability only bounded by Redis capabilities 
(which are not perfect for HA systems, but very reasonable).

`Like it!` can be run with plain-old single non-clustered Redis instance too.

#### Highlights
* [`ApiController`](src/main/java/org/borodust/likeit/controller/ApiController.java) processes REST API requests
* [`DefaultLikeService`](src/main/java/org/borodust/likeit/service/impl/DefaultLikeService.java) handles like service logic
* [`LikeRepository`](src/main/java/org/borodust/likeit/data/LikeRepository.java) provides persistence
