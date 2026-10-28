package app.pagamento.shared.password.infrastructure;

import app.pagamento.shared.password.domain.PasswordHash;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter (autoApply = true)
public class PasswordHashConverter implements AttributeConverter<PasswordHash, String> {

    @Override
    public String convertToDatabaseColumn(PasswordHash hash) {
        return hash == null ? null : hash.value();
    }

    @Override
    public PasswordHash convertToEntityAttribute(String value) {
        return value == null ? null : new PasswordHash(value);
    }
}
