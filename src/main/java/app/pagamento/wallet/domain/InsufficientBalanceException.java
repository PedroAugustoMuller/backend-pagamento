package app.pagamento.wallet.domain;

public class InsufficientBalanceException extends RuntimeException {
	InsufficientBalanceException(String message) {
		super(message);
	}
}
