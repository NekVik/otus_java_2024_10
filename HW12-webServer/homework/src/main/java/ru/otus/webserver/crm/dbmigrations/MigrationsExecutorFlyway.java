package ru.otus.webserver.crm.dbmigrations;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S1066")
public class MigrationsExecutorFlyway {
    private static final Logger logger = LoggerFactory.getLogger(MigrationsExecutorFlyway.class);

    private final Flyway flyway;

    public MigrationsExecutorFlyway(String dbUrl, String dbUserName, String dbPassword) {
        flyway = Flyway.configure()
                .dataSource(dbUrl, dbUserName, dbPassword)
                .cleanDisabled(false)
                .locations("classpath:/db/migration")
                .load();
    }

    public void executeMigrations(boolean clean) {
        logger.info("db migration started...");

        if (clean) {
            if (flyway.info().current() != null) {
                flyway.clean();
                logger.info("db cleaned.");
            }
        }

        flyway.migrate();

        logger.info("db migration finished.");
    }
}
