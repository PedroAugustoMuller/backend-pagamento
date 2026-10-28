package app.pagamento.wallet.domain;

public class WalletNotAllowedException extends RuntimeException {
	WalletNotAllowedException(String message) {
		super(message);
	}
}
