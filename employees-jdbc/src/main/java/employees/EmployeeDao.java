package employees;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeDao {

    private final DataSource dataSource;

    @SneakyThrows
    public Employee save(Employee employee) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement("""
                   insert into employees(id, emp_name) values (nextval('seq_employees'), ?)
                   """, new String[]{"id"})
                ) {
            ps.setString(1, employee.name());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                return new Employee(id, employee.name());
            } else {
                throw new IllegalStateException("Could not find generated key");
            }
        }
    }

    @SneakyThrows
    public List<Employee> findAll() {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement("""
                   select id, emp_name from employees 
           """);
                ResultSet rs = ps.executeQuery()
        ) {
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                employees.add(new Employee(id, name));
            }
            return employees;
        }
    }


}
