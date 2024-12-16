package employees;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeDaoTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.2-alpine");

    EmployeeDao dao;

    @BeforeAll
    static void startDb() {
        postgres.start();
    }

    @AfterAll
    static void stopDb() {
        postgres.stop();
    }

    @BeforeEach
    void createDao() throws SQLException, LiquibaseException {
        // Given
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        System.out.println(postgres.getJdbcUrl());
        dataSource.setURL(postgres.getJdbcUrl());
        dataSource.setUser(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());

        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    new JdbcConnection(connection)
            );
            Liquibase liquibase = new Liquibase("/db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update(new Contexts());
        }

        dao = new EmployeeDao(dataSource);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from employees")
        ) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void save() {
        // When
        Employee created = dao.save(new Employee(null, "John Doe"));

        // Then
        assertNotNull(created.id());
        assertEquals("John Doe", created.name());
    }

    @Test
    void findAll() {
        // Given
        dao.save(new Employee(null, "John Doe"));
        dao.save(new Employee(null, "Jane Doe"));

        // When
        List<Employee> employees = dao.findAll();

        // Then
        assertThat(employees)
                .extracting(Employee::name)
                .containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }


}
