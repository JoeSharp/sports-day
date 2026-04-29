FROM liquibase:alpine

WORKDIR /liquibase

RUN lpm add postgresql --global

COPY ./liquibase/changelog /liquibase/changelog
COPY ./liquibase/data /liquibase/data
COPY ./liquibase.properties /liquibase/liquibase.properties

ENTRYPOINT ["liquibase", "update"]


