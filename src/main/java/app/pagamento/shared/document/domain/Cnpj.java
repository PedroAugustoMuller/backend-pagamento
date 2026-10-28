package app.pagamento.shared.document.domain;

import java.util.regex.Pattern;

public record Cnpj(String value) implements Document {

	private static final Pattern FORMAT = Pattern.compile("[0-9A-Z]{12}\\d{2}");
	private static final int[] FIRST_DIGIT_WEIGHTS = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] SECOND_DIGIT_WEIGHTS = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	public Cnpj {
		value = CheckDigits.normalize(value, "CNPJ");
		if (!isValid(value)) {
			throw new InvalidDocumentException("Invalid CNPJ: " + value);
		}
	}

	public static Cnpj of(String raw) {
		return new Cnpj(raw);
	}

	@Override
	public String formatted() {
		return "%s.%s.%s/%s-%s".formatted(
				value.substring(0, 2), value.substring(2, 5), value.substring(5, 8),
				value.substring(8, 12), value.substring(12));
	}

	@Override
	public String toString() {
		return formatted();
	}

	private static boolean isValid(String n) {
		return FORMAT.matcher(n).matches()
				&& !CheckDigits.allSameCharacter(n)
				&& CheckDigits.calculate(n, FIRST_DIGIT_WEIGHTS) == CheckDigits.digitAt(n, 12)
				&& CheckDigits.calculate(n, SECOND_DIGIT_WEIGHTS) == CheckDigits.digitAt(n, 13);
	}
}