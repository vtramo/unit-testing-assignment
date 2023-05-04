package it.unina.spme.testing.bank;

public interface BankTransactionLedger {
  void write();
  void write(BankAccount bankAccount, String operation, double amount);
}
