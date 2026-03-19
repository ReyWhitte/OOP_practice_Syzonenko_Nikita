package ex04;

/**
 * Головний клас програми.
 * <p>
 * Містить точку входу та демонструє роботу шаблонів Singleton та Command.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 5.0
 */
public class Main {
    
    /**
     * Головний метод програми.
     * 
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        // Отримуємо єдиний екземпляр додатку (Singleton)
        Application app = Application.getInstance();
        
        // Запускаємо додаток
        app.run();
    }
}