package it.unina.spme.testing.bank;

public class ItalianAntiMoneyLaunderingPolicy implements AntiMoneyLaunderingPolicy {
    @Override
    public boolean isLegalDeposit(double toDeposit) {
        // beware! A very complex implementation!
        return toDeposit <= 50000;
    }
}
