package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;
    
    @Before
    public void setUp() throws Exception {
        DKK = new Currency("DKK", 0.20);
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        Nordea = new Bank("Nordea", SEK);
        DanskeBank = new Bank("DanskeBank", DKK);
        SweBank.openAccount("Ulrika");
        SweBank.openAccount("Bob");
        Nordea.openAccount("Bob");
        DanskeBank.openAccount("Gertrud");
    }

    @Test
    public void testGetName() {
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(SEK, SweBank.getCurrency());
        assertEquals(SEK, Nordea.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
    }

    @Test
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        SweBank.openAccount("Alice");
        assertEquals(0, SweBank.getBalance("Alice").intValue());
        try {
            SweBank.openAccount("Alice");
            fail("Should throw AccountExistsException");
        } catch (AccountExistsException e) {
            // Expected
        }
    }

    @Test
    public void testDeposit() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(10000, SEK));
        assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
        try {
            SweBank.deposit("Unknown", new Money(10000, SEK));
            fail("Should throw AccountDoesNotExistException");
        } catch (AccountDoesNotExistException e) {
            // Expected
        }
    }

    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(10000, SEK));
        SweBank.withdraw("Ulrika", new Money(5000, SEK));
        assertEquals(5000, SweBank.getBalance("Ulrika").intValue());
        try {
            SweBank.withdraw("Unknown", new Money(5000, SEK));
            fail("Should throw AccountDoesNotExistException");
        } catch (AccountDoesNotExistException e) {
            // Expected
        }
    }
    
    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        assertEquals(0, SweBank.getBalance("Ulrika").intValue());
        try {
            SweBank.getBalance("Unknown");
            fail("Should throw AccountDoesNotExistException");
        } catch (AccountDoesNotExistException e) {
            // Expected
        }
    }
    
    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(10000, SEK));
        SweBank.transfer("Ulrika", Nordea, "Bob", new Money(5000, SEK));
        assertEquals(5000, SweBank.getBalance("Ulrika").intValue());
        assertEquals(5000, Nordea.getBalance("Bob").intValue());

        SweBank.deposit("Bob", new Money(10000, SEK));
        SweBank.transfer("Bob", "Ulrika", new Money(3000, SEK));
        assertEquals(7000, SweBank.getBalance("Bob").intValue());
        assertEquals(8000, SweBank.getBalance("Ulrika").intValue());
    }
    
    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(10000, SEK));
        SweBank.addTimedPayment("Ulrika", "pay1", 2, 0, new Money(1000, SEK), Nordea, "Bob");
        SweBank.tick();
        assertEquals(9000, SweBank.getBalance("Ulrika").intValue());
        assertEquals(1000, Nordea.getBalance("Bob").intValue());
        SweBank.removeTimedPayment("Ulrika", "pay1");
    }
}