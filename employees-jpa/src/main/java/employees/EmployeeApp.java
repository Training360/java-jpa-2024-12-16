package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class EmployeeApp {

    public static void main(String[] args) {
        long id;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        try (

                EntityManager em = factory.createEntityManager()) {


            System.out.println(em.find(Employee.class, 1L));

            em.getTransaction().begin();
            Employee employee = new Employee("John Doe EclipseLink");
            em.persist(employee);
            em.getTransaction().commit();

            List<Employee> employees =
                    em.createQuery("select e from Employee e", Employee.class).getResultList();
            System.out.println(employees);

            em.createQuery("select e from Employee e", Employee.class)
                    .getResultStream().forEach(System.out::println);

            id = employee.getId();

            System.out.println("1:" + em.contains(employee));

            em.clear();

            System.out.println("2:" + em.contains(employee));



        }

        try (EntityManager em = factory.createEntityManager()) {
            System.out.println("Find employee by id");
            em.getTransaction().begin();
            Employee loaded = em.find(Employee.class, id);
            System.out.println("3:" + em.contains(loaded));
            loaded.setName("John Doe XXX");

            em.getTransaction().commit();
            System.out.println("Loaded employee: " + loaded);

        }

        System.out.println("Delete employee");
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
//            Employee employeeToDelete = em.find(Employee.class, id);
            Employee employeeToDelete = em.getReference(Employee.class, id);
            System.out.println("Call remove method");
            em.remove(employeeToDelete);
            em.getTransaction().commit();
        }
    }
}
