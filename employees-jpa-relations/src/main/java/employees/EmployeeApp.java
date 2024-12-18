package employees;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class EmployeeApp {

    public static void main(String[] args) {
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu")) {
            EmployeeDao employeeDao = new EmployeeDao(factory);

            ParkingPlace parkingPlace = new ParkingPlace(100);
            employeeDao.save(parkingPlace);
            Employee employee = new Employee("John Doe", parkingPlace);
            employee.addPhoneNumber(new PhoneNumber("home", "1234"));
            employee.addPhoneNumber(new PhoneNumber("mobile", "5678"));
            employeeDao.save(employee);

            ParkingPlace parkingPlace2 = new ParkingPlace(200);
            employeeDao.save(parkingPlace2);
            employee.setParkingPlace(parkingPlace2);
            employeeDao.update(employee);

            System.out.println("Attach existing parkingplace to employee");
            ParkingPlace parkingPlace3 = employeeDao.findParkingPlaceByPosition(100);
            Employee employee2 = new Employee("Jane Doe", parkingPlace3);
            employeeDao.save(employee2);
            System.out.println("Attach existing parkingplace to employee end");

            //
            System.out.println("List employees");
            List<Employee> employees = employeeDao.findAllWithParkingPlaces();
            for (Employee emp : employees) {
//                System.out.println(emp.getName() + " " + emp.getParkingPlace());
                System.out.println(emp.getName());
            }

            System.out.println("Add phone number");
            employeeDao.addPhoneTo(employee2.getId(), new PhoneNumber("home", "5555"));
            System.out.println(employee2.getPhoneNumbers());

            System.out.println("Read all employees");
            employees = employeeDao.findAllWithPhoneNumbers();
            for (Employee e: employees) {
                for (PhoneNumber phoneNumber: e.getPhoneNumbers()) {
                    System.out.println(phoneNumber);
                }
            }

            System.out.println("DTO only");
            List<EmployeeDto> employeeDtos = employeeDao.findAllEmployeeDtosWithPhoneNumbers();
            System.out.println(employeeDtos);
        }
    }
}
