package app.pagamento.shared.document.domain;

public sealed interface Document permits Cpf, Cnpj {

	String value();

	String formatted();

	static Document of(String raw) {
		String n = CheckDigits.normalize(raw, "Document");
		return switch (n.length()) {
			case 11 -> new Cpf(n);
			case 14 -> new Cnpj(n);
			default -> throw new InvalidDocumentException(
					"Document must have 11 (CPF) or 14 (CNPJ) characters");
		};
	}
}
