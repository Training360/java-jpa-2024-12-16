package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class LazyEmployeeApp {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        try (EntityManager em = factory.createEntityManager()) {

            em.getTransaction().begin();
            for (int i = 1; i <= 10; i++) {
                Employee employee = new Employee("John Doe " + i, List.of("J", "John", "Johnny"),
                        List.of(new Vacation(LocalDate.now(), 5),
                                new Vacation(LocalDate.of(2024, 1, 1), 10)));
                em.persist(employee);
            }
            em.getTransaction().commit();
        }

        // N + 1 select problem
        try (EntityManager em = factory.createEntityManager()) {
            List<Employee> employees = em.createQuery("""
                            select distinct e from Employee e 
                                left join fetch e.nickNames
                                left join fetch e.vacations
                                 order by e.name
                            """, Employee.class)
                    .getResultList();

            for (Employee employee: employees) {
                System.out.println(employee.getName());
                for (String nick: employee.getNickNames()) {
                    System.out.println(nick);
                }
// itt szorzat jön vissza
                for (Vacation vacation: employee.getVacations()) {
                    System.out.println(vacation);
                }
            }

            List<EmployeeDto> data = em.createQuery("select new employees.EmployeeDto(e.name) from Employee e")
                    .getResultList();
            for (EmployeeDto row: data) {
                System.out.println(row.name());
            }

            // https://stackoverflow.com/questions/33893099/the-state-field-path-cannot-be-resolved-to-a-collection-type
//            List<EmployeeWithNickNamesDto> employeeWithNickNamesDtos = em.createQuery("select new employees.EmployeeWithNickNamesDto(e.name, e.nickNames) from Employee e")
//                    .getResultList();
//            for (EmployeeWithNickNamesDto row: employeeWithNickNamesDtos) {
//                System.out.println(row.name() + " " + row.nicks());
//            }
        }
    }
}
