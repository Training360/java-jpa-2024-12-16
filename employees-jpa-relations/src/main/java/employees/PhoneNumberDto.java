package employees;

import java.io.Serializable;

/**
 * DTO for {@link PhoneNumber}
 */
public record PhoneNumberDto(String type) implements Serializable {
}