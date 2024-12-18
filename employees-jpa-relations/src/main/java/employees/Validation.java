package employees;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Embeddable
@Getter @Setter
public class Validation {

    private LocalDate validFrom;

    private LocalDate validTo;

    public int getDuration() {
        return (int) ChronoUnit.DAYS.between(validFrom, validTo);
    }

}
