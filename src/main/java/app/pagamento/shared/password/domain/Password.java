package app.pagamento.shared.password.domain;

import java.nio.charset.StandardCharsets;

public record Password(String value) {
	private static final int MIN_LENGTH = 8;
	private static final int MAX_BYTES = 72;

	public Password {
		if (value == null || value.isBlank()) {
			throw new InvalidPasswordException("Password is required");
		}
		if (value.length() < MIN_LENGTH) {
			throw new InvalidPasswordException("Password must have at least " + MIN_LENGTH + " characters");
		}
		if (value.getBytes(StandardCharsets.UTF_8).length > MAX_BYTES) {
			throw new InvalidPasswordException("Password is too long");
		}
	}

	@Override
	public String toString() {
		return "********";
	}
}
