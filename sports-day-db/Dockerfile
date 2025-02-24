FROM liquibase:latest

WORKDIR /liquibase

COPY ./liquibase/changelog /liquibase/changelog
COPY ./liquibase/data /liquibase/data
COPY ./liquibase.properties /liquibase/liquibase.properties

ENTRYPOINT ["liquibase", "update"]


