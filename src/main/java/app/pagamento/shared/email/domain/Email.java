package app.pagamento.shared.email.domain;

import java.util.Locale;
import java.util.regex.Pattern;

public record Email(String value) {

	private static final int MAX_LENGTH = 254;
	private static final int MAX_LABEL_LENGTH = 63;
	private static final Pattern LOCAL_PART = Pattern.compile("[a-z0-9.!#$%&'*+/=?^_`{|}~-]+");
	private static final Pattern LABEL = Pattern.compile("[a-z0-9-]+");

	public Email {
		if (value == null || value.isBlank()) {
			throw new InvalidEmailException("Email is required");
		}
		value = value.trim().toLowerCase(Locale.ROOT);
		if (value.length() > MAX_LENGTH || !isValid(value)) {
			throw new InvalidEmailException("Invalid email: " + value);
		}
	}

	public static Email of(String raw) {
		return new Email(raw);
	}

	public String domain() {
		return value.substring(value.indexOf('@') + 1);
	}

	@Override
	public String toString() {
		return value;
	}

	private static boolean isValid(String email) {
		int at = email.indexOf('@');
		if (at <= 0 || at != email.lastIndexOf('@')) {
			return false;
		}
		String localPart = email.substring(0, at);
		String domain = email.substring(at + 1);
		return LOCAL_PART.matcher(localPart).matches() && isValidDomain(domain);
	}

	private static boolean isValidDomain(String domain) {
		String[] labels = domain.split("\\.", -1);
		if (labels.length < 2) {
			return false;
		}
		for (String label : labels) {
			if (!isValidLabel(label)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isValidLabel(String label) {
		return !label.isEmpty()
				&& label.length() <= MAX_LABEL_LENGTH
				&& label.charAt(0) != '-'
				&& label.charAt(label.length() - 1) != '-'
				&& LABEL.matcher(label).matches();
	}
}
