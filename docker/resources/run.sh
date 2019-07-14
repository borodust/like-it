#!/bin/bash

WORK_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
LIKEIT_WORKER_COUNT=${LIKEIT_WORKER_COUNT:=1}

java -jar                                                               \
-Xmx256m                                                                \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005    \
-Dlike.worker.count=${LIKEIT_WORKER_COUNT}                              \
${WORK_DIR}/lib/like-it-@project.version@.jar