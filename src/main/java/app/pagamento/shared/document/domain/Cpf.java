package app.pagamento.shared.document.domain;

import java.util.regex.Pattern;

public record Cpf(String value) implements Document {

	private static final Pattern FORMAT = Pattern.compile("\\d{11}");
	private static final int[] FIRST_DIGIT_WEIGHTS = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] SECOND_DIGIT_WEIGHTS = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	public Cpf {
		value = CheckDigits.normalize(value, "CPF");
		if (!isValid(value)) {
			throw new InvalidDocumentException("Invalid CPF: " + value);
		}
	}

	public static Cpf of(String raw) {
		return new Cpf(raw);
	}

	@Override
	public String formatted() {
		return "%s.%s.%s-%s".formatted(
				value.substring(0, 3), value.substring(3, 6),
				value.substring(6, 9), value.substring(9));
	}

	@Override
	public String toString() {
		return formatted();
	}

	private static boolean isValid(String n) {
		return FORMAT.matcher(n).matches()
				&& !CheckDigits.allSameCharacter(n)
				&& CheckDigits.calculate(n, FIRST_DIGIT_WEIGHTS) == CheckDigits.digitAt(n, 9)
				&& CheckDigits.calculate(n, SECOND_DIGIT_WEIGHTS) == CheckDigits.digitAt(n, 10);
	}
}
