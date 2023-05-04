package it.unina.spme.testing.bank;

public class BankAccount {
    private final String holder;
    private double amount;

    private BankTransactionLedger transactionLedger = new BankTransactionLedgerImpl();
    private AntiMoneyLaunderingPolicy policy = new ItalianAntiMoneyLaunderingPolicy();

    public BankAccount(String holder, double amount) {
        this.holder = holder;
        this.amount = amount;
    }

    public BankAccount(
        String holder,
        double amount,
        BankTransactionLedger transactionLedger,
        AntiMoneyLaunderingPolicy policy
    ) {
        this(holder, amount);
        this.transactionLedger = transactionLedger;
        this.policy = policy;
    }

    public double withdraw(double toWithdraw) throws IllegalArgumentException {
        if (toWithdraw > this.amount) {
            throw new IllegalArgumentException("Insufficient funds!");
        }
        if (toWithdraw <= 0) {
            throw new IllegalArgumentException("Cannot withdraw non positive amount!");
        }
        this.amount -= toWithdraw;
        transactionLedger.write(this, "withdraw", toWithdraw);
        return amount;
    }

    public double deposit(double toDeposit) {
        if (toDeposit <= 5) {
            throw new IllegalArgumentException("Cannot deposit less than 5 €!");
        }
        if (!policy.isLegalDeposit(toDeposit)) {
            throw new IllegalArgumentException("Illegal amount (see Anti-Money Laundering policies!)");
        }
        this.amount += toDeposit;
        transactionLedger.write(this, "deposit", toDeposit);
        return amount;
    }

    public String getHolder(){
        return this.holder;
    }

    public double getAmount(){
        return this.amount;
    }
}