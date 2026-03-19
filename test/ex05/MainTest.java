package ex05;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import ex03.ViewResult;
import ex03.ViewTable;

/**
 * Тестування класів завдання №6.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class MainTest {
    
    private static final int N = 100;
    private static ViewResult view;
    private static MaxCommand max1;
    private static MaxCommand max2;
    private static AvgCommand avg1;
    private static AvgCommand avg2;
    private static MinMaxCommand min1;
    private static MinMaxCommand min2;
    private CommandQueue queue;
    
    /**
     * Виконується один раз перед усіма тестами.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        // Створюємо view з N елементами
        view = new ViewTable(70, N);
        
        // Важливо: viewInit() може генерувати тільки 5 елементів!
        // Тому ми маємо заповнити колекцію вручну
        view.getItems().clear();
        
        // Додаємо N тестових елементів
        for (int i = 0; i < N; i++) {
            String phoneNumber = String.format("+38067%07d", i);
            ((ViewTable) view).addEntry(phoneNumber);
        }
        
        // Перевіряємо, що дійсно є N елементів
        assertEquals("Має бути " + N + " елементів", N, view.getItems().size());
        
        max1 = new MaxCommand(view);
        max2 = new MaxCommand(view);
        avg1 = new AvgCommand(view);
        avg2 = new AvgCommand(view);
        min1 = new MinMaxCommand(view);
        min2 = new MinMaxCommand(view);
    }
    
    /**
     * Виконується один раз після всіх тестів.
     */
    @AfterClass
    public static void tearDownAfterClass() {
        assertNotNull("max1 не повинен бути null", max1);
        assertNotNull("max2 не повинен бути null", max2);
        assertNotNull("avg1 не повинен бути null", avg1);
        assertNotNull("avg2 не повинен бути null", avg2);
        assertNotNull("min1 не повинен бути null", min1);
        assertNotNull("min2 не повинен бути null", min2);
    }
    
    /**
     * Виконується перед кожним тестом.
     */
    @Before
    public void setUp() {
        queue = new CommandQueue();
    }
    
    /**
     * Тест перевірки кількості елементів.
     */
    @Test
    public void testSize() {
        assertEquals("Кількість елементів має бути " + N, N, view.getItems().size());
    }
    
    /**
     * Тест перевірки MaxCommand.
     */
    @Test
    public void testMax() {
        max1.execute();
        assertTrue("Результат max має бути >= 0", max1.getResult() >= 0);
        assertTrue("Результат max має бути < " + N, max1.getResult() < N);
        System.out.println("Max result: " + max1.getResult());
    }
    
    /**
     * Тест перевірки AvgCommand.
     */
    @Test
    public void testAvg() {
        avg1.execute();
        assertTrue("Середнє має бути > 0", avg1.getResult() > 0);
        System.out.println("Avg result: " + avg1.getResult());
    }
    
    /**
     * Тест перевірки MinMaxCommand.
     */
    @Test
    public void testMinMax() {
        min1.execute();
        assertTrue("Мінімум має бути >= 0", min1.getResultMin() >= 0);
        assertTrue("Мінімум має бути < " + N, min1.getResultMin() < N);
        assertTrue("Максимум має бути >= 0", min1.getResultMax() >= 0);
        assertTrue("Максимум має бути < " + N, min1.getResultMax() < N);
        System.out.println("Min result: " + min1.getResultMin() + ", Max result: " + min1.getResultMax());
    }
}