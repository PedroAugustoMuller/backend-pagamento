package app.pagamento.shared.document.infrastructure;

import app.pagamento.shared.document.domain.Cnpj;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CnpjConverter implements AttributeConverter<Cnpj, String> {

	@Override
	public String convertToDatabaseColumn(Cnpj cnpj) {
		return cnpj == null ? null : cnpj.value();
	}

	@Override
	public Cnpj convertToEntityAttribute(String value) {
		return value == null ? null : new Cnpj(value);
	}
}
