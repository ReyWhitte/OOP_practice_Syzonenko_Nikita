package ex03;

import org.junit.Test;

import ex03.ViewTable;

import org.junit.Before;
import static org.junit.Assert.*;

public class MainTest {
    
    private ViewTable table;
    
    @Before
    public void setUp() {
        table = new ViewTable();
        table.getItems().clear();
    }
    
    @Test
    public void testConstructors() {
        ViewTable t1 = new ViewTable();
        assertEquals(ViewTable.DEFAULT_WIDTH, t1.getWidth());
        
        ViewTable t2 = new ViewTable(40);
        assertEquals(40, t2.getWidth());
        
        ViewTable t3 = new ViewTable(50, 5);
        assertEquals(50, t3.getWidth());
        assertEquals(5, t3.getItems().size());
    }
    
    @Test
    public void testInitOverloading() {
        table.init(35);
        assertEquals(35, table.getWidth());
    }
    
    @Test
    public void testAddEntry() {
        table.addEntry("+380671234567");
        assertEquals(1, table.getItems().size());
    }
    
    @Test
    public void testViewShow() {
        table.addEntry("+380671234567");
        table.viewShow(); // перевіряємо, що не кидає винятків
    }
    
    @Test
    public void testViewShowOverloading() {
        table.addEntry("+380671234567");
        table.setWidth(30);
        int oldWidth = table.getWidth();
        table.viewShow(50);
        assertEquals(oldWidth, table.getWidth());
    }
}