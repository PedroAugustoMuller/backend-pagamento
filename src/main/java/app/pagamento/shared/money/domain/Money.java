package app.pagamento.shared.money.domain;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount) implements Comparable<Money> {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        Objects.requireNonNull(amount, "amount is required");
        if (amount.signum() < 0) {
            throw new InvalidMoneyException("Amount cannot be negative: " + amount);
        }
        try {
            amount = amount.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException _) {
            throw new InvalidMoneyException("Amount cannot have more than 2 decimal places: " + amount);
        }
    }

    public static Money of(String value) { return new Money(new BigDecimal(value)); }

    public Money plus(Money other)  { return new Money(amount.add(other.amount)); }
    public Money minus(Money other) { return new Money(amount.subtract(other.amount)); }

    public boolean isZero() { return amount.signum() == 0; }
    public boolean isLessThan(Money other) { return compareTo(other) < 0; }

    @Override
    public int compareTo(Money other) { return amount.compareTo(other.amount); }

    @Override
    public String toString() { return "R$ " + amount; }
}
