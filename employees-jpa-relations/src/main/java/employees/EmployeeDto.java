package employees;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Employee}
 */
public record EmployeeDto(String name, List<PhoneNumberDto> phoneNumbers) implements Serializable {
}