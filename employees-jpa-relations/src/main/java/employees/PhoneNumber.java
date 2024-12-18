package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "phone_numbers")
@ToString
public class PhoneNumber {

    @Id
    @GeneratedValue(generator = "phone_number_generator")
    @SequenceGenerator(name = "phone_number_generator", sequenceName = "seq_phone_number")
    private Long id;

    private String type;

    private String number;

    @ManyToOne
    private Employee employee;

    public PhoneNumber(String type, String number) {
        this.type = type;
        this.number = number;
    }
}
