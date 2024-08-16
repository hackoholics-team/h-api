package app.hackoholics.api;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractContextInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  private static final String FLYWAY_TESTDATA_PATH = "classpath:/db/testdata";

  @Override
  public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
    var postgresContainer =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("dummy")
            .withUsername("dummy")
            .withPassword("dummy");

    postgresContainer.start();

    TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
        applicationContext,
        "server.port=" + this.getServerPort(),
        "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
        "spring.datasource.username=" + postgresContainer.getUsername(),
        "spring.datasource.password=" + postgresContainer.getPassword(),
        "firebase.access.key={}",
        "project.access.key={}",
        "google.project.id=dummy",
        "bucket.name=dummy",
        "google.api.key=dummy",
        "stripe.api.key=dummy",
        "sentry.dsn=https://public@sentry.example.com/1",
        "sentry.environment=prod",
        "spring.flyway.locations=classpath:/db/migration," + FLYWAY_TESTDATA_PATH);
  }

  public abstract int getServerPort();
}
