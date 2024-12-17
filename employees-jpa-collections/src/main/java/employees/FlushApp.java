package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class FlushApp {

    public static void main(String[] args) {
        try (
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Employee employee = new Employee("John Doe",
                    EmployeeStatus.ACCEPTED, new Phone("1", "2"));

            em.persist(employee);

//            em.flush();

            em.createQuery("update Employee set name = 'Doe'").executeUpdate();

//            em.createNativeQuery("update employees set emp_name = 'Doe'")
//                    .executeUpdate();

            em.detach(employee);

            System.out.println(em.contains(employee));

            Employee loaded = em.find(Employee.class, 1L);
            System.out.println(loaded);

            em.getTransaction().commit();
        }

    }
}
