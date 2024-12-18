package employees;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToOne //(cascade = CascadeType.ALL)
    private ParkingPlace parkingPlace;

    @ToString.Exclude
    @OneToMany(mappedBy = "employee", orphanRemoval = true, cascade = CascadeType.ALL)
//    @OrderBy("type")
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    // owner side
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "employees_projects",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "projects_id"))
//    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Project> projects = new ArrayList<>();

    @Embedded
    private Address address;

    @Embedded
    private Validation validation;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, ParkingPlace parkingPlace) {
        this.name = name;
        this.parkingPlace = parkingPlace;
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<>();
        }
        phoneNumbers.add(phoneNumber);
        phoneNumber.setEmployee(this);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getId() != null && Objects.equals(getId(), employee.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
