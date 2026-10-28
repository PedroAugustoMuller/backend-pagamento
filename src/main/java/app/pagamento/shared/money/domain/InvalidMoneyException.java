package app.pagamento.shared.money.domain;

public class InvalidMoneyException extends RuntimeException {
	InvalidMoneyException(String message) {
		super(message);
	}
}
