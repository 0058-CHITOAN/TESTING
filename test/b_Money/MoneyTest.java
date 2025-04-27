package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
    Currency SEK, DKK, EUR;
    Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
    
    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
        SEK100 = new Money(10000, SEK);
        EUR10 = new Money(1000, EUR);
        SEK200 = new Money(20000, SEK);
        EUR20 = new Money(2000, EUR);
        SEK0 = new Money(0, SEK);
        EUR0 = new Money(0, EUR);
        SEKn100 = new Money(-10000, SEK);
    }

    @Test
    public void testGetAmount() {
        assertEquals(10000, SEK100.getAmount().intValue());
        assertEquals(1000, EUR10.getAmount().intValue());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR, EUR10.getCurrency());
    }

    @Test
    public void testToString() {
        assertEquals("100.0 SEK", SEK100.toString());
        assertEquals("10.0 EUR", EUR10.toString());
    }

    @Test
    public void testGlobalValue() {
        // 10000 SEK với rate 0.15 -> 1500
        assertEquals(1500, SEK100.universalValue().intValue());
        // 1000 EUR với rate 1.5 -> 1500
        assertEquals(1500, EUR10.universalValue().intValue());
    }

    @Test
    public void testEqualsMoney() {
        // SEK100 (10000 SEK) và EUR10 (1000 EUR) có universal value bằng nhau (1500)
        assertTrue(SEK100.equals(EUR10));
        assertFalse(SEK100.equals(SEK200));
    }

    @Test
    public void testAdd() {
        Money result = SEK100.add(EUR10);
        // EUR10 (1000 EUR, rate 1.5) sang SEK (rate 0.15) -> 1000 * 1.5 / 0.15 = 10000
        // 10000 + 10000 = 20000 SEK
        assertEquals(20000, result.getAmount().intValue());
        assertEquals(SEK, result.getCurrency());
    }

    @Test
    public void testSub() {
        Money result = SEK200.sub(EUR10);
        // EUR10 (1000 EUR, rate 1.5) sang SEK (rate 0.15) -> 1000 * 1.5 / 0.15 = 10000
        // 20000 - 10000 = 10000 SEK
        assertEquals(10000, result.getAmount().intValue());
        assertEquals(SEK, result.getCurrency());
    }

    @Test
    public void testIsZero() {
        assertTrue(SEK0.isZero());
        assertTrue(EUR0.isZero());
        assertFalse(SEK100.isZero());
    }

    @Test
    public void testNegate() {
        Money result = SEK100.negate();
        assertEquals(-10000, result.getAmount().intValue());
        assertEquals(SEK, result.getCurrency());
    }

    @Test
    public void testCompareTo() {
        assertTrue(SEK100.compareTo(EUR10) == 0); // Giá trị universal bằng nhau
        assertTrue(SEK100.compareTo(SEK200) < 0); // SEK100 < SEK200
        assertTrue(SEK200.compareTo(SEK100) > 0); // SEK200 > SEK100
    }
}