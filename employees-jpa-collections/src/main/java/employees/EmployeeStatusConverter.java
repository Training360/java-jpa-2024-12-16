package employees;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class EmployeeStatusConverter implements AttributeConverter<EmployeeStatus, String> {

    @Override
    public String convertToDatabaseColumn(EmployeeStatus attribute) {
        return attribute.toString().substring(0, 1);
    }

    @Override
    public EmployeeStatus convertToEntityAttribute(String dbData) {
        return Arrays.stream(EmployeeStatus.values()).filter(s -> s.name().startsWith(dbData))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid Employee Status: " + dbData));
    }
}
