package employees;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmbeddableApp {

    public static void main(String[] args) {
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu")) {
            EmployeeDao employeeDao = new EmployeeDao(factory);
            Employee employee = new Employee("John Doe");
            employee.setAddress(new Address("Fő utca", "Budapest"));
            employeeDao.save(employee);
        }
        }
}
