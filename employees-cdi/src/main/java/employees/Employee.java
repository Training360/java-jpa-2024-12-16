package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "employee")
@NoArgsConstructor
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Employee_SEQ")
    @SequenceGenerator(name = "Employee_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public Employee(String name) {
        this.name = name;
    }
}