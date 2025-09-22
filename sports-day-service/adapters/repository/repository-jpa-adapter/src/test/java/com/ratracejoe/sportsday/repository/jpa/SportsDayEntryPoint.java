package com.ratracejoe.sportsday.repository.jpa;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class SportsDayEntryPoint {
  private static final Logger LOGGER = LoggerFactory.getLogger(SportsDayEntryPoint.class);

  public static void createDatabase(Connection connection) throws SQLException {
    try {
      Database database =
          DatabaseFactory.getInstance()
              .findCorrectDatabaseImplementation(new JdbcConnection(connection));
      Liquibase liquibase =
          new Liquibase(
              "changelog/db.changelog-master.yaml",
              new DirectoryResourceAccessor(new File("../../../../sports-day-db/liquibase/")),
              database);

      liquibase.update(new Contexts(), new LabelExpression());

    } catch (Exception e) {
      LOGGER.error(e, () -> "Failed to run Liquibase migration");
      throw new SQLException(e);
    }
  }
}
