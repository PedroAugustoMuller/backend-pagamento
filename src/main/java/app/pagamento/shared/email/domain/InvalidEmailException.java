package app.pagamento.shared.email.domain;

public class InvalidEmailException extends RuntimeException {
	InvalidEmailException(String message) {
		super(message);
	}
}
