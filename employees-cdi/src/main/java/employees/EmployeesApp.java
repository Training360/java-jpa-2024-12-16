package employees;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.List;

public class EmployeesApp {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer
                .newInstance().addPackages(EmployeesApp.class).initialize()) {

            HelloService helloService = container.select(HelloService.class).get();
            System.out.println(helloService.sayHello());

            EmployeeService employeeService = container.select(EmployeeService.class).get();
            employeeService.save(new Employee("John Doe"));
            employeeService.save(new Employee("Jane Doe"));
            List<Employee> employees = employeeService.findAll();
            System.out.println(employees);

            employees = employeeService.findByNameLike("John%");
            System.out.println(employees);
        }
    }
}
