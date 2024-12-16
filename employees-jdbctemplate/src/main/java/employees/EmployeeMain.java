package employees;

import org.postgresql.ds.PGSimpleDataSource;

import java.util.List;

public class EmployeeMain {

    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/employees");
        dataSource.setUser("employees");
        dataSource.setPassword("employees");

        EmployeeDao employeeDao = new EmployeeDao(dataSource);

        employeeDao.save(new Employee(null, "John Doe"));

        List<Employee> employees = employeeDao.findAll();
        System.out.println(employees);
    }
}
