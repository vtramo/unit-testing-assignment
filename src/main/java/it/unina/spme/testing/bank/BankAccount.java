package it.unina.spme.testing.bank;

public class BankAccount {
    private String holder;
    private double amount;
    private BankTransactionLedger transactionLedger = new BankTransactionLedger();
    private AntiMoneyLaunderingPolicy policy = new ItalianAntiMoneyLaunderingPolicy();


    public BankAccount(String holder, double amount) {
        this.holder = holder;
        this.amount = amount;
    }

    public void withdraw(double toWithdraw) throws IllegalArgumentException {
        if(amount > this.amount){
            throw new IllegalArgumentException("Insufficient funds!");
        }
        if(toWithdraw<=0){
            throw new IllegalArgumentException("Cannot withdraw non positive amount!");
        }
        this.amount -= toWithdraw;
        transactionLedger.write(this, "withdraw", toWithdraw);
    }

    public void deposit(double toDeposit){
        if(toDeposit <= 5) {
            throw new IllegalArgumentException("Cannot deposit less than 5 €!");
        }
        if(!policy.isLegalDeposit(toDeposit)){
            throw new IllegalArgumentException("Illegal amount (see Anti-Money Laundering policies!)");
        }
        this.amount += toDeposit;
        transactionLedger.write(this, "deposit", toDeposit);
    }

    public String getHolder(){
        return this.holder;
    }

    public double getAmount(){
        return this.amount;
    }
}
