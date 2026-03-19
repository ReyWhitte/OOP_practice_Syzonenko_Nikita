package ex04;

import ex03.View;
import ex03.ViewTable;

/**
 * Головний клас додатку (шаблон Singleton).
 * <p>
 * Забезпечує єдиний екземпляр додатку та ініціалізує меню з командами.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class Application {
    
    /** Єдиний екземпляр класу (Singleton) */
    private static Application instance = new Application();
    
    /** Об'єкт для відображення даних */
    private View view;
    
    /** Головне меню програми */
    private Menu menu;
    
    /**
     * Приватний конструктор (шаблон Singleton).
     * Ініціалізує view та меню з командами.
     */
    private Application() {
        // Ініціалізація view (використовуємо ViewTable із завдання №4)
        view = new ViewTable(70);
        
        // Створення меню та додавання команд
        menu = new Menu();
        menu.add(new ViewConsoleCommand(view));
        menu.add(new GenerateConsoleCommand(view));
        menu.add(new AddConsoleCommand(view));
        menu.add(new ChangeConsoleCommand(view));
        menu.add(new SaveConsoleCommand(view));
        menu.add(new RestoreConsoleCommand(view));
        
        // Додаємо тестові дані (КОМЕНТУЄМО, щоб не виконувалося зразу)
        // view.viewInit();  // <-- ВИДАЛЕНО
    }
    
    /**
     * Повертає єдиний екземпляр додатку.
     * 
     * @return екземпляр Application
     */
    public static Application getInstance() {
        return instance;
    }
    
    /**
     * Повертає об'єкт відображення.
     * 
     * @return поточний view
     */
    public View getView() {
        return view;
    }
    
    /**
     * Повертає меню програми.
     * 
     * @return об'єкт Menu
     */
    public Menu getMenu() {
        return menu;
    }
    
    /**
     * Демонструє роботу макрокоманди.
     */
    public void demonstrateMacro() {
        System.out.println("\n📌 ДЕМОНСТРАЦІЯ МАКРОКОМАНДИ");
        
        // Створюємо макрокоманду
        MacroCommand macro = new MacroCommand();
        macro.add(new GenerateConsoleCommand(view));
        macro.add(new AddConsoleCommand(view));
        macro.add(new ViewConsoleCommand(view));
        
        // Виконуємо макрокоманду через меню
        menu.executeMacro(macro);
    }
    
    /**
     * Запускає головний цикл програми.
     */
    public void run() {
        System.out.println("🚀 ЗАВДАННЯ №5");
        System.out.println("   Шаблони Singleton та Command\n");
        
        // ВИДАЛЯЄМО демонстрацію макрокоманди при старті
        // demonstrateMacro();  // <-- ВИДАЛЕНО
        
        // ВІДРАЗУ запускаємо меню
        menu.execute();
    }
}