package app.pagamento.shared.password.domain;

public class InvalidPasswordException extends RuntimeException {
	InvalidPasswordException(String message) {
		super(message);
	}
}
