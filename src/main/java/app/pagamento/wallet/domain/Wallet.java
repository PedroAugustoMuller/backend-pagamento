package app.pagamento.wallet.domain;

import java.util.UUID;

import app.pagamento.shared.document.domain.Cnpj;
import app.pagamento.shared.document.domain.Cpf;
import app.pagamento.shared.money.domain.Money;
import app.pagamento.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;

@Getter
@Entity
@Table(name = "wallets")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;

	@Enumerated(EnumType.STRING)
	@Column(name = "wallet_type", nullable = false, length = 20)
	private WalletType walletType;

	@Column(name = "balance", nullable = false, precision = 19, scale = 2)
	private Money balance = Money.ZERO;

	@Column(name = "cnpj", unique = true, length = 14)
	private Cnpj cnpj; // nulo para carteiras pessoais

	@Version
	private Long version;

	protected Wallet() {
	}

	private Wallet(User owner, WalletType walletType, Cnpj cnpj) {
		this.owner = owner;
		this.walletType = walletType;
		this.cnpj = cnpj;
	}

	public static Wallet personal(User owner) {
		if (!(owner.getDocument() instanceof Cpf)) {
			throw new WalletNotAllowedException("Only individuals can open a personal wallet");
		}
		return new Wallet(owner, WalletType.USER, null);
	}

	public static Wallet merchant(User owner, Cnpj cnpj) {
		if (cnpj == null) {
			throw new WalletNotAllowedException("A merchant wallet requires a CNPJ");
		}
		if (owner.getDocument() instanceof Cnpj companyCnpj && !companyCnpj.equals(cnpj)) {
			throw new WalletNotAllowedException("A company can only open wallets with its own CNPJ");
		}
		return new Wallet(owner, WalletType.MERCHANT, cnpj);
	}

	public boolean isMerchant() {
		return walletType == WalletType.MERCHANT;
	}

	public void debit(Money amount) {
		if (isMerchant()) {
			throw new WalletNotAllowedException("Merchant wallets cannot send money");
		}
		if (balance.isLessThan(amount)) {
			throw new InsufficientBalanceException("Insufficient balance");
		}
		balance = balance.minus(amount);
	}

	public void credit(Money amount) {
		balance = balance.plus(amount);
	}
}