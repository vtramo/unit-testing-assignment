package it.unina.spme.testing.bank;

public interface AntiMoneyLaunderingPolicy {
    boolean isLegalDeposit(double toDeposit);
}
