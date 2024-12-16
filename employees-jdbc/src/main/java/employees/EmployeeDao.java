package employees;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @SneakyThrows
    public Optional<Employee> findById(long id) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement("""
                   select id, emp_name from employees where id = ? 
           """);
        ) {
            ps.setLong(1, id);
            return loadEmployee(id, ps);
        }
    }

    private Optional<Employee> loadEmployee(long id, PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String name = rs.getString(2);
                return Optional.of(new Employee(id, name));
            }
            else {
                return Optional.empty();
            }
        }
    }

    @SneakyThrows
    public Employee update(Employee employee) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement("update employees set emp_name = ? where id = ?")
                ) {
            ps.setString(1, employee.name());
            ps.setLong(2, employee.id());
            ps.executeUpdate();
            return employee;
        }
    }

    @SneakyThrows
    public void deleteById(long id) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement("delete from employees where id = ?")
                ) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

}
