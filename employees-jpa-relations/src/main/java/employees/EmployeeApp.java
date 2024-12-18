package employees;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class EmployeeApp {

    public static void main(String[] args) {
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu")) {
            EmployeeDao employeeDao = new EmployeeDao(factory);

            ParkingPlace parkingPlace = new ParkingPlace(100);
//            employeeDao.save(parkingPlace);
            Employee employee = new Employee("John Doe", parkingPlace);
            employeeDao.save(employee);

            ParkingPlace parkingPlace2 = new ParkingPlace(200);
            employee.setParkingPlace(parkingPlace2);
            employeeDao.update(employee);

            //
            ParkingPlace parkingPlace3 = employeeDao.findParkingPlaceByPosition(100);
            Employee employee2 = new Employee("Jane Doe", parkingPlace3);
            employeeDao.update(employee2);

            //
            System.out.println("List employees");
            List<Employee> employees = employeeDao.findAllWithParkingPlaces();
            for (Employee emp : employees) {
//                System.out.println(emp.getName() + " " + emp.getParkingPlace());
                System.out.println(emp.getName());
            }
        }
    }
}
