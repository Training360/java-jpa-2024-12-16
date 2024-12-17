package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeDao {

    private final EntityManagerFactory factory;

    public Employee save(Employee employee) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        }
    }

    public Employee findById(int id) {
        try (EntityManager em = factory.createEntityManager()) {
            return em.find(Employee.class, id);
        }
    }

    public List<Employee> findAll() {
        try (EntityManager em = factory.createEntityManager()) {
//            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
            return em.createNamedQuery("Employee.findAll").getResultList();
        }
    }

    public Employee update(Employee employee) {
//        try (EntityManager em = factory.createEntityManager()) {
//            em.getTransaction().begin();
//            Employee managed = em.find(Employee.class, employee.getId());
//            managed.setName(employee.getName());
//            em.getTransaction().commit();
//            return managed;
//        }
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(employee);
            em.getTransaction().commit();
            return employee;
        }
    }

    public void delete(Employee employee) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(employee);
            em.getTransaction().commit();
        }
    }
}
