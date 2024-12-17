package employees;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PhoneConverter implements AttributeConverter<Phone, String> {

    @Override
    public String convertToDatabaseColumn(Phone attribute) {
        return attribute.prefix() + "-" + attribute.postfix();
    }

    @Override
    public Phone convertToEntityAttribute(String dbData) {
        String[] parts = dbData.split("-");
        return new Phone(parts[0], parts[1]);
    }
}
