package ex04;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import ex03.PhoneEntry;
import ex03.ViewTable;

/**
 * Тестування класів Завдання №5.
 * <p>
 * Перевіряє роботу команд та шаблонів Singleton та Command.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 4.0
 */
public class MainTest {
    
    private ViewTable view;
    private ChangeItemCommand changeCommand;
    
    @Before
    public void setUp() {
        view = new ViewTable();
        view.getItems().clear();
        changeCommand = new ChangeItemCommand();
    }
    
    /**
     * Тест перевірки Singleton.
     */
    @Test
    public void testSingleton() {
        Application app1 = Application.getInstance();
        Application app2 = Application.getInstance();
        
        // Повинні бути одним і тим же об'єктом
        assertSame(app1, app2);
    }
    
    /**
     * Тест перевірки ChangeItemCommand.
     */
    @Test
    public void testChangeItemCommand() {
        PhoneEntry entry = new PhoneEntry("+380671234567", "0671234567", "67", "Київстар");
        changeCommand.setItem(entry);
        changeCommand.setScale(2.5);
        
        String originalNumber = entry.getOriginalNumber();
        changeCommand.execute();
        
        // Номер має змінитися
        assertNotEquals(originalNumber, entry.getOriginalNumber());
    }
    
    /**
     * Тест перевірки undo для ChangeItemCommand.
     */
    @Test
    public void testChangeItemUndo() {
        PhoneEntry entry = new PhoneEntry("+380671234567", "0671234567", "67", "Київстар");
        changeCommand.setItem(entry);
        changeCommand.setScale(2.5);
        
        String originalNumber = entry.getOriginalNumber();
        changeCommand.execute();
        changeCommand.undo();
        
        // Після undo номер має відновитися
        assertEquals(originalNumber, entry.getOriginalNumber());
    }
    
    /**
     * Тест перевірки GenerateConsoleCommand.
     */
    @Test
    public void testGenerateCommand() {
        GenerateConsoleCommand cmd = new GenerateConsoleCommand(view);
        cmd.execute();
        
        assertTrue(view.getItems().size() > 0);
    }
    
    /**
     * Тест перевірки AddConsoleCommand.
     */
    @Test
    public void testAddCommand() {
        // Не можемо протестувати інтерактивне введення в автоматичному тесті,
        // тому просто перевіряємо, що команда створюється і має правильний ключ
        AddConsoleCommand cmd = new AddConsoleCommand(view);
        assertEquals('a', cmd.getKey());
        assertNotNull(cmd.toString());
    }
    
    /**
     * Тест перевірки гарячих клавіш команд.
     */
    @Test
    public void testCommandKeys() {
        assertEquals('v', new ViewConsoleCommand(view).getKey());
        assertEquals('g', new GenerateConsoleCommand(view).getKey());
        assertEquals('a', new AddConsoleCommand(view).getKey());      // ← НОВИЙ ТЕСТ
        assertEquals('c', new ChangeConsoleCommand(view).getKey());
        assertEquals('s', new SaveConsoleCommand(view).getKey());
        assertEquals('r', new RestoreConsoleCommand(view).getKey());
    }
    
    /**
     * Тест перевірки макрокоманди.
     */
    @Test
    public void testMacroCommand() {
        MacroCommand macro = new MacroCommand();
        assertEquals(0, macro.size());
        
        macro.add(new GenerateConsoleCommand(view));
        macro.add(new AddConsoleCommand(view));    // ← ДОДАНО В ТЕСТ
        macro.add(new ViewConsoleCommand(view));
        assertEquals(3, macro.size());
        
        macro.execute();
        macro.undo();
        
        // Просто перевіряємо, що не було винятків
        assertTrue(true);
    }
    
    /**
     * Тест перевірки Menu.
     */
    @Test
    public void testMenu() {
        Menu menu = new Menu();
        menu.add(new ViewConsoleCommand(view));
        menu.add(new GenerateConsoleCommand(view));
        menu.add(new AddConsoleCommand(view));     // ← ДОДАНО В ТЕСТ
        
        // Перевіряємо, що toString не порожній
        assertNotNull(menu.toString());
    }
}