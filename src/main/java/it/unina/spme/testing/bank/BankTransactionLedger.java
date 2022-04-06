package it.unina.spme.testing.bank;

public class BankTransactionLedger {
    public void write() {
        return;
    }

    public void write(BankAccount bankAccount, String operation, double amount) {
        System.out.println(bankAccount.getHolder() + " " + operation + " " + amount);
    }
}
