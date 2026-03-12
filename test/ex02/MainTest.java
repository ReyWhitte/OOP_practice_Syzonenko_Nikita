package ex02;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Тестування класів завдання №3.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class MainTest {
    
    private ViewResult view;
    
    @Before
    public void setUp() {
        view = new ViewResult();
        view.getItems().clear();  // Гарантовано очищаємо колекцію
    }
    
    /**
     * Тест ініціалізації тестовими даними.
     */
    @Test
    public void testInitTestData() {
        view.initTestData(3);
        assertEquals(3, view.getItems().size());
        
        assertEquals("Київстар", view.getItems().get(0).getOperatorName());
        assertEquals("Vodafone", view.getItems().get(1).getOperatorName());
        assertEquals("lifecell", view.getItems().get(2).getOperatorName());
    }
    
    /**
     * Тест видалення записів.
     */
    @Test
    public void testRemoveEntry() {
        view.addEntry("+380671234567");
        view.addEntry("0501234567");
        assertEquals(2, view.getItems().size());
        
        view.removeEntry(0);
        assertEquals(1, view.getItems().size());
        assertEquals("0501234567", view.getItems().get(0).getOriginalNumber());
        
        view.removeEntry(5); // невірний індекс
        assertEquals(1, view.getItems().size());
    }
    
    /**
     * Тест додавання записів до колекції.
     */
    @Test
    public void testAddEntry() {
        assertEquals(0, view.getItems().size());
        
        view.addEntry("+380671234567");
        assertEquals(1, view.getItems().size());
        
        PhoneEntry entry = view.getItems().get(0);
        assertEquals("+380671234567", entry.getOriginalNumber());
        assertEquals("0671234567", entry.getCleanNumber());
        assertEquals("67", entry.getOperatorCode());
        assertEquals("Київстар", entry.getOperatorName());
    }
    
    /**
     * Тест перевірки визначення оператора.
     */
    @Test
    public void testOperatorDetection() {
        assertEquals("Київстар", view.detectOperator("+380671234567"));
        assertEquals("Vodafone", view.detectOperator("0501234567"));
        assertEquals("lifecell", view.detectOperator("0931234567"));
        assertEquals("3Mob", view.detectOperator("0911234567"));
        assertEquals("Інтертелеком", view.detectOperator("0891234567"));
        assertEquals("Peoplenet", view.detectOperator("0921234567"));
        assertEquals("Невідомий оператор", view.detectOperator("0551234567"));
    }
    
    
    /**
     * Тест методу viewInit.
     */
    @Test
    public void testViewInit() {
        view.viewInit();
        assertTrue(view.getItems().size() > 0);
    }
    
    /**
     * Тест серіалізації.
     */
    @Test
    public void testSerialization() {
        view.addEntry("+380671234567");
        view.addEntry("0501234567");
        view.addEntry("0931234567");
        
        ArrayList<PhoneEntry> original = new ArrayList<>(view.getItems());
        
        try {
            view.viewSave();
        } catch (IOException e) {
            fail("Помилка збереження: " + e.getMessage());
        }
        
        // Створюємо новий об'єкт і завантажуємо дані
        ViewResult view2 = new ViewResult();
        try {
            view2.viewRestore();
        } catch (Exception e) {
            fail("Помилка відновлення: " + e.getMessage());
        }
        
        assertEquals(original.size(), view2.getItems().size());
        assertTrue(original.containsAll(view2.getItems()));
    }
    
    /**
     * Тест фабричного методу.
     */
    @Test
    public void testFactoryMethod() {
        Viewable creator = new ViewableResult();
        View view1 = creator.getView();
        assertNotNull(view1);
        assertTrue(view1 instanceof ViewResult);
        
        View view2 = ((ViewableResult) creator).getView(10);
        assertNotNull(view2);
        assertTrue(view2 instanceof ViewResult);
    }
    
    /**
     * Тест форматування номера.
     */
    @Test
    public void testFormatting() {
        PhoneEntry entry = new PhoneEntry("+380671234567", "0671234567", "67", "Київстар");
        assertEquals("+380 (67) 123-45-67", entry.getFormattedNumber());
        
        PhoneEntry entry2 = new PhoneEntry("0501234567", "0501234567", "50", "Vodafone");
        assertEquals("+380 (50) 123-45-67", entry2.getFormattedNumber());
    }
    
    /**
     * Тест методів equals та hashCode.
     */
    @Test
    public void testEquals() {
        PhoneEntry entry1 = new PhoneEntry("+380671234567", "0671234567", "67", "Київстар");
        PhoneEntry entry2 = new PhoneEntry("0671234567", "0671234567", "67", "Київстар");
        PhoneEntry entry3 = new PhoneEntry("0501234567", "0501234567", "50", "Vodafone");
        
        assertEquals(entry1, entry2);
        assertNotEquals(entry1, entry3);
        assertEquals(entry1.hashCode(), entry2.hashCode());
    }
}