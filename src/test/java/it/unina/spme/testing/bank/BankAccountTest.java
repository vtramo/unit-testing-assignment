package it.unina.spme.testing.bank;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.mockito.hamcrest.MockitoHamcrest.doubleThat;

public class BankAccountTest {

    @Test
    public void shouldDepositPositiveAmount(){
        BankAccount b = new BankAccount("Luigi", 1000.0);
        b.deposit(1000.0);
        assertEquals(2000.0, b.getAmount(), 0.001);
    }

    @Test
    public void shouldNotExceedAntiMoneyLaunderingQuotas(){
        BankAccount b = new BankAccount("Luigi", 1000.0);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            b.deposit(100000.0);
        });
        assertEquals("Illegal amount (see Anti-Money Laundering policies!)", e.getMessage());
    }

    @Test
    public void shouldNotAllowDepositsSmallerThanFiveEuros(){
        BankAccount b = new BankAccount("Luigi", 1000.0);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            b.deposit(5.0);
        });
        assertEquals("Cannot deposit less than 5 €!", e.getMessage());
    }

}
