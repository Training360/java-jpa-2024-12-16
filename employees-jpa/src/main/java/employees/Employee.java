package employees;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(generator = "employee_sequence_generator")
    @SequenceGenerator(name = "employee_sequence_generator", sequenceName = "seq_employees")
    private Long id;

    private String name;

    public Employee(String name) {
        this.name = name;
    }
}
