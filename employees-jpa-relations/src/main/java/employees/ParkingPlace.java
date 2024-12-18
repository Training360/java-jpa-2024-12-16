package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "parking_places")
public class ParkingPlace {

    @Id
    @GeneratedValue(generator = "parking_place_generator")
    @SequenceGenerator(name = "parking_place_generator", sequenceName = "seq_parking_place")
    private Long id;

    @Column(unique = true, nullable = false)
    private int position;

    public ParkingPlace(int position) {
        this.position = position;
    }
}
