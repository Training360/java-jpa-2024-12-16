package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "employees")

@NamedQuery(name = "Employee.findAll", query = "select e from Employee e")
public class Employee {

    @Id
    @GeneratedValue(generator = "employee_sequence_generator")
    @SequenceGenerator(name = "employee_sequence_generator", sequenceName = "seq_employees")
    private Long id;

    @Column(name = "emp_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status")
    private EmployeeStatus employeeStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, EmployeeStatus employeeStatus) {
        this.name = name;
        this.employeeStatus = employeeStatus;
    }

    @PrePersist
    public void initCreatedAt() {
        createdAt = LocalDateTime.now();
    }
}
