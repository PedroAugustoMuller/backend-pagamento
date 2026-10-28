package app.pagamento.shared.document.infrastructure;

import app.pagamento.shared.document.domain.Document;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter (autoApply = true)
public class DocumentConverter implements AttributeConverter<Document, String> {

    @Override
    public String convertToDatabaseColumn(Document doc) {
        return doc == null ? null : doc.value();
    }

    @Override
    public Document convertToEntityAttribute(String value) {
        return value == null ? null : Document.of(value);
    }
}
