package app.pagamento.shared.document.domain;

import java.util.Locale;

final class CheckDigits {

	private CheckDigits() {
	}

	static String normalize(String raw, String label) {
		if (raw == null || raw.isBlank()) {
			throw new InvalidDocumentException(label + " is required");
		}
		return raw.replaceAll("[.\\-/\\s]", "").toUpperCase(Locale.ROOT);
	}

	static boolean allSameCharacter(String n) {
		return n.chars().distinct().count() == 1;
	}

	static int calculate(String n, int[] weights) {
		int sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += (n.charAt(i) - '0') * weights[i];
		}
		int r = sum % 11;
		return r < 2 ? 0 : 11 - r;
	}

	static int digitAt(String n, int index) {
		return n.charAt(index) - '0';
	}
}
