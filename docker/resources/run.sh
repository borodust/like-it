#!/bin/bash

WORK_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

JAVA_ARGS="-Xmx256m \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

if [[ ! -z "$LIKEIT_WORKER_COUNT" ]]; then
JAVA_ARGS+=" -Dlike.worker.count=${LIKEIT_WORKER_COUNT}"
fi

if [[ ! -z "$LIKEIT_REDIS_HOST" ]]; then
JAVA_ARGS+=" -Dspring.redis.host=${LIKEIT_REDIS_HOST}"
fi

if [[ ! -z "$LIKEIT_REDIS_PORT" ]]; then
JAVA_ARGS+=" -Dspring.redis.port=${LIKEIT_REDIS_PORT}"
fi

if [[ ! -z "$LIKEIT_REDIS_PASSWORD" ]]; then
JAVA_ARGS+=" -Dspring.redis.password=${LIKEIT_REDIS_PASSWORD}"
fi

if [[ ! -z "$LIKEIT_REDIS_CLUSTER_NODES" ]]; then
JAVA_ARGS+=" -Dspring.redis.cluster.nodes=${LIKEIT_REDIS_CLUSTER_NODES}"
fi


java -jar ${JAVA_ARGS} ${WORK_DIR}/lib/like-it-@project.version@.jar