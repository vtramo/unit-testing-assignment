package it.unina.spme.testing.bank;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.AdditionalMatchers.lt;
import static org.mockito.Mockito.*;

@DisplayName("A BankAccount")
public class BankAccountTest {

    BankAccount bankAccount;
    BankTransactionLedger mockedBankTransactionLedger;
    AntiMoneyLaunderingPolicy mockedAntiMoneyLaunderingPolicy;

    @BeforeEach
    void createMocks() {
        mockedBankTransactionLedger = mock(BankTransactionLedger.class);
        mockedAntiMoneyLaunderingPolicy = mock(AntiMoneyLaunderingPolicy.class);
    }

    @Nested
    @TestInstance(PER_CLASS)
    @DisplayName("when instantiated with no money")
    class whenItContainsNoMoney {

        @BeforeEach
        void createBankAccountWithNoMoney() {
            bankAccount = new BankAccount(
              "Vincenzo", 0D,
              mockedBankTransactionLedger,
              mockedAntiMoneyLaunderingPolicy
            );
        }

        @Test
        @DisplayName("should initializes the balance to zero correctly")
        void shouldHaveNoMoney() {
            final double amount = bankAccount.getAmount();

            assertThat(amount, is(equalTo(0D)));
            verifyNoInteractions(mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        }

        @ParameterizedTest
        @DisplayName("when withdrawing a positive amount it should throw an exception")
        @MethodSource("providePositiveDoubles")
        void whenWithdrawingPositiveAmountShouldThrowException(Double positiveAmount) {
            assertThrows(IllegalArgumentException.class,
              () -> bankAccount.withdraw(positiveAmount),
              "Insufficient funds!"
            );
            assertThat(bankAccount.getAmount(), is(equalTo(0D)));
            verifyNoInteractions(mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        }

        @ParameterizedTest
        @DisplayName("when withdrawing a negative amount it should throw an exception")
        @MethodSource("provideNegativeDoubles")
        void whenWithdrawingNegativeAmountShouldThrowException(Double negativeAmount) {
            assertThrows(IllegalArgumentException.class,
              () -> bankAccount.withdraw(negativeAmount),
              "Cannot withdraw non positive amount!"
            );
            assertThat(bankAccount.getAmount(), is(equalTo(0D)));
            verifyNoInteractions(mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        }

        @Test
        @DisplayName("when withdrawing an amount equal to zero it should throw an exception")
        void whenWithdrawingZeroShouldThrowException() {
            assertThrows(IllegalArgumentException.class,
              () -> bankAccount.withdraw(0D),
              "Cannot withdraw non positive amount!"
            );
            assertThat(bankAccount.getAmount(), is(equalTo(0D)));
            verifyNoInteractions(mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        }

        @Test
        @DisplayName("when depositing exactly 5€ it should throw an exception")
        void whenDepositingExactlyFiveEuroShouldThrowException() {
            assertThrows(IllegalArgumentException.class,
              () -> bankAccount.deposit(5D),
              "Cannot deposit less than 5 €!"
            );
            assertThat(bankAccount.getAmount(), is(equalTo(0D)));
            verifyNoInteractions(mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        }

        @ParameterizedTest
        @DisplayName("when depositing a negative amount it should throw an exception")
        @MethodSource("provideNegativeDoubles")
        void whenDepositingNegativeAmountShouldThrowException(Double negativeAmount) {
            assertThrows(IllegalArgumentException.class,
              () -> bankAccount.deposit(negativeAmount),
              "Cannot deposit less than 5 €!"
            );
            assertThat(bankAccount.getAmount(), is(equalTo(0D)));
            verifyNoInteractions(mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        }

        @ParameterizedTest
        @DisplayName("when depositing a positive amount less than 50.000€ and greater than 5€ it should be deposit it correctly")
        @ValueSource(doubles = {5.1D, 5_000D, 10_000D, 15_000D, 20_000D, 25_000D, 30_000D, 40_000D, 49_999D})
        void whenDepositingALegalAmountShouldDepositCorrectly(Double legalDeposit) {
            when(mockedAntiMoneyLaunderingPolicy.isLegalDeposit(lt(50_000D))).thenReturn(true);

            final double amountBeforeDeposit = bankAccount.getAmount();
            final double amountAfterDeposit = bankAccount.deposit(legalDeposit);

            assertThat(amountAfterDeposit, allOf(
                is(equalTo(amountBeforeDeposit + legalDeposit)),
                is(equalTo(bankAccount.getAmount())))
            );

            InOrder inOrder = inOrder(mockedAntiMoneyLaunderingPolicy, mockedBankTransactionLedger);
            inOrder.verify(mockedAntiMoneyLaunderingPolicy).isLegalDeposit(legalDeposit);
            inOrder.verify(mockedBankTransactionLedger).write(bankAccount, "deposit", legalDeposit);
            inOrder.verifyNoMoreInteractions();
        }

        Stream<Double> providePositiveDoubles() {
            return Stream.iterate(1000_0D, n -> n * 50_000D)
              .limit(10);
        }

        Stream<Double> provideNegativeDoubles() {
            return Stream.iterate(-10_000D, n -> n * 50_000D)
              .limit(10);
        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    @DisplayName("when instantiated with a lot of money")
    class whenItContainsALotOfMoney {

        @BeforeEach
        void createBankAccountWithNoMoney() {
            bankAccount = new BankAccount(
              "Vincenzo", 100_000_000D,
              mockedBankTransactionLedger,
              mockedAntiMoneyLaunderingPolicy
            );
        }

        @Test
        @DisplayName("should initializes the balance correctly")
        void shouldHaveNoMoney() {
            final double amount = bankAccount.getAmount();

            assertThat(amount, is(equalTo(1_000_000D)));
            verifyNoInteractions(mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        }

        @ParameterizedTest
        @DisplayName("when withdrawing a legal amount it should be withdrawn correctly")
        @MethodSource("provideLegalWithdrawalAmounts")
        void whenWithdrawingALegalAmountShouldWithdrawnCorrectly(Double legalAmount) {
            final double amountBeforeWithdraw = bankAccount.getAmount();
            final double amountAfterWithdraw = bankAccount.withdraw(legalAmount);

            assertThat(amountAfterWithdraw, allOf(
              is(equalTo(amountBeforeWithdraw - legalAmount)),
              is(equalTo(bankAccount.getAmount()))
            ));

            verifyNoInteractions(mockedAntiMoneyLaunderingPolicy);
            verify(mockedBankTransactionLedger).write(bankAccount, "withdraw", legalAmount);
            verifyNoMoreInteractions(mockedBankTransactionLedger);
        }

        Stream<Double> provideLegalWithdrawalAmounts() {
            return Stream.iterate(1D, n -> n * 5D)
              .limit(12);
        }
    }

    @Test
    public void shouldDepositPositiveAmount() {
        BankAccount b = new BankAccount("Luigi", 1_000D);
        b.deposit(1_000D);
        assertEquals(2_000D, b.getAmount(), 0.001D);
    }

    @Test
    public void shouldNotExceedAntiMoneyLaunderingQuotas() {
        AntiMoneyLaunderingPolicy mockedAntiMoneyLaunderingPolicy = mock(AntiMoneyLaunderingPolicy.class);
        when(mockedAntiMoneyLaunderingPolicy.isLegalDeposit(lt(50_000D))).thenReturn(true);

        BankAccount b = new BankAccount("Luigi", 1_000D, mockedBankTransactionLedger, mockedAntiMoneyLaunderingPolicy);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> b.deposit(100_000D));
        assertEquals("Illegal amount (see Anti-Money Laundering policies!)", e.getMessage());
    }

    @Test
    public void shouldNotAllowDepositsSmallerThanFiveEuros() {
        BankAccount b = new BankAccount("Luigi", 1000D);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> b.deposit(5D));
        assertEquals("Cannot deposit less than 5 €!", e.getMessage());
    }
}