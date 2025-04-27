package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
    Currency SEK, DKK, EUR;
    
    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
    }

    @Test
    public void testGetName() {
        assertEquals("SEK", SEK.getName());
        assertEquals("DKK", DKK.getName());
        assertEquals("EUR", EUR.getName());
    }
    
    @Test
    public void testGetRate() {
        assertEquals(0.15, SEK.getRate(), 0.0001);
        assertEquals(0.20, DKK.getRate(), 0.0001);
        assertEquals(1.5, EUR.getRate(), 0.0001);
    }
    
    @Test
    public void testSetRate() {
        SEK.setRate(0.25);
        assertEquals(0.25, SEK.getRate(), 0.0001);
    }
    
    @Test
    public void testGlobalValue() {
        // 10000 SEK với rate 0.15 -> 10000 * 0.15 = 1500
        assertEquals(1500, SEK.universalValue(10000).intValue());
        // 10000 DKK với rate 0.20 -> 10000 * 0.20 = 2000
        assertEquals(2000, DKK.universalValue(10000).intValue());
    }
    
    @Test
    public void testValueInThisCurrency() {
        // 10000 DKK (rate 0.20) sang SEK (rate 0.15)
        // Universal value: 10000 * 0.20 = 2000
        // Trong SEK: 2000 / 0.15 = 13333.33
        assertEquals(13333, SEK.valueInThisCurrency(10000, DKK).intValue());
    }
}