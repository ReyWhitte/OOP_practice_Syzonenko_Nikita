package ex01;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.IOException;

public class MainTest {
    
    private Calc calc;
    
    @Before
    public void setUp() {
        calc = new Calc();
    }
    
    @Test
    public void testKyivstarDetection() {
        assertEquals("Київстар", calc.init("+380671234567"));
        assertEquals("0671234567", calc.getResult().getCleanNumber());
        assertEquals("67", calc.getResult().getOperatorCode());
        assertTrue(calc.getResult().isValid());
        
        assertEquals("Київстар", calc.init("0671234567"));
        assertEquals("0671234567", calc.getResult().getCleanNumber());
        
        assertEquals("Київстар", calc.init("380681234567"));
        assertEquals("0681234567", calc.getResult().getCleanNumber());
        
        assertEquals("Київстар", calc.init("0961234567"));
        assertEquals("0961234567", calc.getResult().getCleanNumber());
        assertEquals("96", calc.getResult().getOperatorCode());
    }
    
    @Test
    public void testVodafoneDetection() {
        assertEquals("Vodafone", calc.init("+380501234567"));
        assertEquals("0501234567", calc.getResult().getCleanNumber());
        assertEquals("50", calc.getResult().getOperatorCode());
        
        assertEquals("Vodafone", calc.init("0661234567"));
        assertEquals("66", calc.getResult().getOperatorCode());
    }
    
    @Test
    public void testLifecellDetection() {
        assertEquals("lifecell", calc.init("+380631234567"));
        assertEquals("0631234567", calc.getResult().getCleanNumber());
        assertEquals("63", calc.getResult().getOperatorCode());
        
        assertEquals("lifecell", calc.init("0731234567"));
        assertEquals("73", calc.getResult().getOperatorCode());
    }
    
    @Test
    public void testOtherOperators() {
        assertEquals("3Mob", calc.init("0911234567"));
        assertEquals("91", calc.getResult().getOperatorCode());
        
        assertEquals("Інтертелеком", calc.init("0891234567"));
        assertEquals("89", calc.getResult().getOperatorCode());
        
        assertEquals("Peoplenet", calc.init("0921234567"));
        assertEquals("92", calc.getResult().getOperatorCode());
    }
    
    @Test
    public void testDifferentFormats() {
        calc.init("+380671234567");
        assertEquals("0671234567", calc.getResult().getCleanNumber());
        assertEquals("+380 (67) 123-45-67", calc.getResult().getFormattedNumber());
        
        calc.init("0671234567");
        assertEquals("0671234567", calc.getResult().getCleanNumber());
        
        calc.init("380671234567");
        assertEquals("0671234567", calc.getResult().getCleanNumber());
        
        calc.init("(067) 123-45-67");
        assertEquals("0671234567", calc.getResult().getCleanNumber());
    }
    
    @Test
    public void testInvalidNumbers() {
        calc.init("123");
        assertFalse(calc.getResult().isValid());
        assertEquals(MobileOperator.UNKNOWN.getName(), calc.getResult().getOperatorName());
        
        calc.init("123456789012345");
        assertFalse(calc.getResult().isValid());
        
        calc.init("abc123def456");
        assertFalse(calc.getResult().isValid());
        
        calc.init("");
        assertFalse(calc.getResult().isValid());
        
        calc.init(null);
        assertFalse(calc.getResult().isValid());
    }
    
    @Test
    public void testUnknownOperator() {
        calc.init("+380551234567"); // 55 - неіснуючий код
        assertEquals(MobileOperator.UNKNOWN.getName(), calc.getResult().getOperatorName());
        assertEquals("0551234567", calc.getResult().getCleanNumber());
        assertTrue(calc.getResult().isValid());
    }
    
    @Test
    public void testRestore() throws Exception {
        calc.init("+380671234567");
        Item2d original = cloneItem2d(calc.getResult());
        
        calc.save();
        
        calc.init("0000000000"); // змінюємо
        
        calc.restore();
        Item2d restored = calc.getResult();
        
        assertEquals(original.getCleanNumber(), restored.getCleanNumber());
        assertEquals(original.getOperatorCode(), restored.getOperatorCode());
        assertEquals(original.getOperatorName(), restored.getOperatorName());
        assertEquals(original.getFormattedNumber(), restored.getFormattedNumber());
        assertEquals(original.isValid(), restored.isValid());
        
        // transient поле не збереглося
        assertNull(restored.getPhoneNumber());
    }
    
    private Item2d cloneItem2d(Item2d original) {
        Item2d clone = new Item2d();
        clone.setPhoneNumber(original.getPhoneNumber());
        clone.setCleanNumber(original.getCleanNumber());
        clone.setOperatorCode(original.getOperatorCode());
        clone.setOperatorName(original.getOperatorName());
        clone.setFormattedNumber(original.getFormattedNumber());
        clone.setValid(original.isValid());
        return clone;
    }
}