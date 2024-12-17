package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "employees")
@ToString
@NamedQuery(name = "Employee.findAll", query = "select e from Employee e")
public class Employee {

    @Id
    @GeneratedValue(generator = "employee_sequence_generator")
    @SequenceGenerator(name = "employee_sequence_generator", sequenceName = "seq_employees")
    private Long id;

    @Column(name = "emp_name")
    private String name;

//    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status")
    @Convert(converter = EmployeeStatusConverter.class)
    private EmployeeStatus employeeStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Convert(converter = PhoneConverter.class)
    private Phone phone;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, EmployeeStatus employeeStatus) {
        this.name = name;
        this.employeeStatus = employeeStatus;
    }

    public Employee(String name, EmployeeStatus employeeStatus, Phone phone) {
        this.name = name;
        this.employeeStatus = employeeStatus;
        this.phone = phone;
    }

    @PrePersist
    public void initCreatedAt() {
        createdAt = LocalDateTime.now();
    }
}
