package app.pagamento.wallet.domain;

public enum WalletType {
    USER("User", true),
    MERCHANT("Merchant", false);

    private final String displayName;
    private final boolean canSendMoney;

    WalletType(String displayName, boolean canSendMoney) {
        this.displayName = displayName;
        this.canSendMoney = canSendMoney;
    }

    public String getDisplayName() { return displayName; }
    public boolean canSendMoney() { return canSendMoney; }
}
