package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Employee findById(long id) {
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

    public void addPhoneTo(Long id, PhoneNumber home) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
//            Employee employee = em.find(Employee.class, id);
            Employee employee = em.getReference(Employee.class, id); //
            System.out.println("Class: " + employee.getClass().getName());
//            employee.addPhoneNumber(home);
            home.setEmployee(employee);
            em.persist(home);
            em.getTransaction().commit();
        }
    }

    public List<Employee> findAllWithPhoneNumbers() {
        try (EntityManager em = factory.createEntityManager()) {
            return em.createQuery(
                    """
                        select distinct e from Employee e 
                            left join fetch e.phoneNumbers 
                            left join fetch e.parkingPlace
                            order by e.name
                        """, Employee.class).getResultList();
        }
    }

    public List<EmployeeDto> findAllEmployeeDtosWithPhoneNumbers() {
        try (EntityManager em = factory.createEntityManager()) {
            List<Object[]> items = em.createQuery("""
                select e.id, e.name, p.type from Employee e join e.phoneNumbers p
                """).getResultList();

            Map<Long, EmployeeDto> employees = new HashMap<>();
            for (Object[] item : items) {
                long id = (Long) item[0];
                EmployeeDto employee = employees.get(id);
                if (employee == null) {
                    String name = (String) item[1];
                    employee = new EmployeeDto(name, new ArrayList<>());
                    employees.put(id, employee);
                }
                String type = (String) item[2];
                employee.phoneNumbers().add(new PhoneNumberDto(type));
            }
            return new ArrayList<>(employees.values());
        }
    }

    public Project save(Project project) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
            return project;
        }
    }

    public void addProjectToEmployee(Long projectId, Long employeeId) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            Employee employees = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);
            employees.addProject(project);

            // Ha csak az inverse side-ot állítjuk, nincs insert
//            project.getEmployees().add(employees);

            em.getTransaction().commit();
        }
    }

    public void removeProjectFromEmployee(Long projectId, Long employeeId) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            Employee employees = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);
            employees.removeProject(project);
            em.getTransaction().commit();
        }
    }

    public Employee findByIdWithProjects(long id) {
        try (EntityManager em = factory.createEntityManager()) {
            return em.createQuery("""
                select e from Employee e left join fetch e.projects where e.id = :id
                """, Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }
}
