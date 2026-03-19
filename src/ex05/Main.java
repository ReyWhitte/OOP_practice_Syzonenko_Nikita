package ex05;

import ex03.View;
import ex03.ViewTable;
import ex03.ViewResult;

/**
 * Головний клас програми.
 * 
 * @author Сизоненко Нікіта
 * @version 6.0
 */
public class Main {
    
    private View view;
    private DataManager dataManager;
    private Menu menu;
    
    /**
     * Конструктор.
     */
    public Main() {
        view = new ViewTable(70);
        dataManager = new DataManager((ViewResult) view);
        menu = new Menu();
        
        // Додаємо всі команди
        menu.add(new ViewCommand(view));
        menu.add(new GenerateCommand(view));
        menu.add(new AddCommand(dataManager));
        menu.add(new RemoveCommand(dataManager));
        menu.add(new SaveCommand(dataManager));
        menu.add(new RestoreCommand(dataManager));
        menu.add(new ExecuteParallelCommand(view));
        menu.add(new UndoCommand(dataManager));
        menu.add(new RedoCommand(dataManager));
        menu.add(new ClearCommand(dataManager));
        
        // Генеруємо тестові дані при старті
        ((ViewResult) view).initTestData(5);
    }
    
    /**
     * Запускає програму.
     */
    public void run() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   Завдання №6       			     ║");
        System.out.println("║   Паралельне виконання             ║");
        System.out.println("║   Шаблон Worker Thread             ║");
        System.out.println("╚════════════════════════════════════╝");
        
        menu.run();
    }
    
    /**
     * Головний метод.
     */
    public static void main(String[] args) {
        new Main().run();
    }
}