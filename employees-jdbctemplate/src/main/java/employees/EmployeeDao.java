package employees;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public EmployeeDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("select id, emp_name from employees",
                this::mapRow
                );
    }

    public Employee save(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("""
                insert into employees(id, emp_name) values (nextval('seq_employees'), ?)
             """, new String[]{"id"});
            ps.setString(1, employee.name());
            return ps;
        }, keyHolder);
        return new Employee(keyHolder.getKey().longValue(), employee.name());
    }

    public Optional<Employee> findById(long id) {
        List<Employee> employees = jdbcTemplate.query("""
                select id, emp_name from employees where id = ?
                """, this::mapRow, id);
        if (employees.isEmpty()) {
            return Optional.empty();
        }
        else if (employees.size() == 1) {
            return Optional.of(employees.getFirst());
        }
        else {
            throw new IllegalStateException("Found more than one employee with id: " + id);
        }
    }

    public Employee update(Employee employee) {
        jdbcTemplate.update("update employees set emp_name = ? where id = ?", employee.name(), employee.id());
        return employee;
    }

    public void deleteById(long id) {
        jdbcTemplate.update("delete from employees where id = ?", id);
    }

    private Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employee(rs.getLong("id"), rs.getString("emp_name"));
    }

//    {
//        PlatformTransactionManager platformTransactionManager = new JdbcTransactionManager(dataSource);
//        TransactionTemplate tx = new TransactionTemplate(platformTransactionManager);
//        tx.executeWithoutResult(ts -> {
//            //
//            //
//        });
//    }
}
