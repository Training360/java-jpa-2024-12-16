package employees;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmployeeProjectApp {

    public static void main(String[] args) {
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu")) {
            EmployeeDao employeeDao = new EmployeeDao(factory);

            Project project = new Project("Big Java Project");
            employeeDao.save(project);

            Employee employee = new Employee("John Doe");
            employeeDao.save(employee);

            employeeDao.addProjectToEmployee(project.getId(), employee.getId());

            Employee loaded = employeeDao.findByIdWithProjects(employee.getId());
            System.out.println(loaded.getProjects());
            for (Project p: loaded.getProjects()) {
                System.out.println(p.getName());
            }

            System.out.println("Remove project from employee");
            employeeDao.removeProjectFromEmployee(project.getId(), employee.getId());
        }
    }
}
