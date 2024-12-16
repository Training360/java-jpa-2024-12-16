package employees;

import org.postgresql.ds.PGSimpleDataSource;

import java.util.List;

public class EmployeeDaoApp {

    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/employees");
        dataSource.setUser("employees");
        dataSource.setPassword("employees");

        EmployeeDao dao = new EmployeeDao(dataSource);
        Employee created = dao.save(new Employee(null, "John Doe"));
        System.out.println(created);

        dao.findById(created.id()).ifPresent(
                employee -> System.out.println("Employee: " + employee)
        );

        List<Employee> employees = dao.findAll();
        System.out.println(employees);
    }
}
