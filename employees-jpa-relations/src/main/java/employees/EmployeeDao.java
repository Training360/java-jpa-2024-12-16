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

    public List<Employee> findAllWithParkingPlaces() {
        try (EntityManager em = factory.createEntityManager()) {
            return em.createNamedQuery("Employee.findAllWithParkingPlaces").getResultList();
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

    public ParkingPlace findParkingPlaceByPosition(int position) {
        try (EntityManager em = factory.createEntityManager()) {
            return em.createQuery("select p from ParkingPlace p where p.position = :position", ParkingPlace.class)
                    .setParameter("position", position)
                    .getSingleResult();
        }
    }

    public void delete(Employee employee) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(employee);
            em.getTransaction().commit();
        }
    }

    public void save(ParkingPlace parkingPlace) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(parkingPlace);
            em.getTransaction().commit();
        }
    }
}
