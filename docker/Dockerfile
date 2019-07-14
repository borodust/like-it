FROM openjdk:12.0.1-oracle

RUN groupadd -r likeit && useradd --no-log-init -r -g likeit likeit
COPY --chown=likeit:likeit ["resources/*", "/like-it/"]
COPY --chown=likeit:likeit ["maven/*", "/like-it/lib/"]

RUN chmod +x /like-it/run.sh

USER likeit
CMD ["/like-it/run.sh"]
