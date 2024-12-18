package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "employees")
@ToString
@NamedQuery(name = "Employee.findAll", query = "select e from Employee e")
@NamedQuery(name = "Employee.findAllWithParkingPlaces", query = "select distinct e from Employee e left join fetch e.parkingPlace order by e.name")
public class Employee {

    @Id
    @GeneratedValue(generator = "employee_sequence_generator")
    @SequenceGenerator(name = "employee_sequence_generator", sequenceName = "seq_employees")
    private Long id;

    @Column(name = "emp_name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ParkingPlace parkingPlace;

    public Employee(String name, ParkingPlace parkingPlace) {
        this.name = name;
        this.parkingPlace = parkingPlace;
    }
}
