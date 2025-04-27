package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
    Currency SEK;
    Bank SweBank;
    Account testAccount;
    
    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        SweBank.openAccount("Alice");
        testAccount = new Account("Hans", SEK);
        testAccount.deposit(new Money(10000000, SEK));
        SweBank.deposit("Alice", new Money(1000000, SEK));
    }
    
    @Test
    public void testAddRemoveTimedPayment() {
        testAccount.addTimedPayment("pay1", 2, 1, new Money(1000, SEK), SweBank, "Alice");
        assertTrue(testAccount.timedPaymentExists("pay1"));
        testAccount.removeTimedPayment("pay1");
        assertFalse(testAccount.timedPaymentExists("pay1"));
    }
    
    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        testAccount.addTimedPayment("pay1", 2, 0, new Money(1000, SEK), SweBank, "Alice");
        testAccount.tick();
        assertEquals(9999000, testAccount.getBalance().getAmount().intValue()); // 10000000 - 1000
        assertEquals(1001000, SweBank.getBalance("Alice").intValue()); // 1000000 + 1000
    }

    @Test
    public void testAddWithdraw() {
        testAccount.deposit(new Money(5000, SEK));
        assertEquals(10005000, testAccount.getBalance().getAmount().intValue());
        testAccount.withdraw(new Money(2000, SEK));
        assertEquals(10003000, testAccount.getBalance().getAmount().intValue());
    }
    
    @Test
    public void testGetBalance() {
        assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
    }
}