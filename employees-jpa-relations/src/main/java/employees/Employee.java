package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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

    @ElementCollection// TILOS: (fetch = FetchType.EAGER)
    private List<String> nickNames;

    @ElementCollection
    private List<Vacation> vacations;

    public Employee(String name, List<String> nickNames) {
        this.name = name;
        this.nickNames = nickNames;
    }

    public Employee(String name, List<String> nickNames, List<Vacation> vacations) {
        this.name = name;
        this.nickNames = nickNames;
        this.vacations = vacations;
    }
}
