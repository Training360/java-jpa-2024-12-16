package employees;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeApp {

    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/employees");
        dataSource.setUser("employees");
        dataSource.setPassword("employees");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                insert into employees(id, emp_name) values (nextval('seq_employees'), ?)
                """)
            ) {
            statement.setString(1, "John Doe");
            // auto-commit
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println("Can not insert");
            e.printStackTrace();
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                select id, emp_name from employees where emp_name like ?
                """);

        ) {
            statement.setString(1, "John%");
            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("emp_name");
                    System.out.println("Employee with id: %d, name: %s".formatted(id, name));
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Can not insert");
            e.printStackTrace();
        }

    }
}
