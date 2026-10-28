package app.pagamento.shared.email.infrastructure;

import app.pagamento.shared.email.domain.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {
	@Override
	public String convertToDatabaseColumn(Email email) {
		return email == null ? null : email.value();
	}

	@Override
	public Email convertToEntityAttribute(String value) {
		return value == null ? null : Email.of(value);
	}
}
