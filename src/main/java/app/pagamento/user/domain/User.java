package app.pagamento.user.domain;

import java.util.UUID;

import app.pagamento.shared.document.domain.Cnpj;
import app.pagamento.shared.document.domain.Document;
import app.pagamento.shared.email.domain.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "full_name", nullable = false, length = 500)
	private String fullName;

	@Column(name = "document", nullable = false, unique = true, length = 14)
	private Document document;

	@Column(name = "email", nullable = false, unique = true, length = 254)
	private Email email;

	public boolean isCompany() {
		return document instanceof Cnpj;
	}
}
