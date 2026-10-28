package app.pagamento.shared.password.domain;

public interface PasswordHasher {
	PasswordHash hash(Password password);
	boolean matches(Password password, PasswordHash hash);
}
