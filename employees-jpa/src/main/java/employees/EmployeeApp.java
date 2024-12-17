package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmployeeApp {

    public static void main(String[] args) {
        try (
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
                EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            Employee employee = new Employee("John Doe EclipseLink");
            em.persist(employee);
            em.getTransaction().commit();
        }
    }
}
