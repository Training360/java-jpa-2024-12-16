package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class EmployeeApp {

    public static void main(String[] args) {

        long id;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        try (

                EntityManager em = factory.createEntityManager()) {

            em.getTransaction().begin();

            Employee employee = new Employee("John Doe", List.of("J", "John", "Johnny"),
                    List.of(new Vacation(LocalDate.now(), 5),
                            new Vacation(LocalDate.of(2024, 1, 1), 10)));
            em.persist(employee);
            id = employee.getId();
            em.getTransaction().commit();

        }

        Employee employee;
        try (EntityManager em = factory.createEntityManager()) {
            employee = em.find(Employee.class, id);
        }

        System.out.println(employee.getName());
        System.out.println(employee.getNickNames());
        employee.getNickNames().stream().forEach(System.out::println);

        employee.getVacations().stream().forEach(System.out::println);
    }
}
