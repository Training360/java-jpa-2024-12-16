package employees;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.util.List;
import java.util.stream.IntStream;

public class EmployeeDaoApp {

    @SneakyThrows
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/employees");
        dataSource.setUser("employees");
        dataSource.setPassword("employees");

        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    new JdbcConnection(connection)
            );
            Liquibase liquibase = new Liquibase("/db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update(new Contexts());
//            liquibase.validate();
        }

        EmployeeDao dao = new EmployeeDao(dataSource);
        Employee created = dao.save(new Employee(null, "John Doe"));
        long id = created.id();
        System.out.println(created);

        dao.findById(id).ifPresent(
                employee -> System.out.println("Employee: " + employee)
        );

        dao.update(new Employee(id, "Jack Doe"));

        dao.findById(id).ifPresent(
                employee -> System.out.println("Employee: " + employee)
        );

        dao.deleteById(id);

        List<Employee> employees = dao.findAll();
        System.out.println(employees);

        List<Employee> employeesToCreate = IntStream.range(0, 5)
                .mapToObj(i -> new Employee(null, "John Doe" + i))
                .toList();
        dao.saveAll(employeesToCreate);
    }
}
