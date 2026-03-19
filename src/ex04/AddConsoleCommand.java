package ex04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ex03.View;
import ex03.ViewResult;

/**
 * Консольна команда для додавання власних даних користувачем.
 * <p>
 * Реалізує {@link ConsoleCommand} для інтерактивного введення
 * номерів телефонів та додавання їх до колекції.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class AddConsoleCommand implements ConsoleCommand {
    
    /** Посилання на об'єкт відображення */
    private View view;
    
    /** Для читання введення користувача */
    private BufferedReader in;
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт для відображення даних
     */
    public AddConsoleCommand(View view) {
        this.view = view;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }
    
    /**
     * Повертає гарячу клавішу команди.
     * 
     * @return символ 'a'
     */
    @Override
    public char getKey() {
        return 'a';
    }
    
    /**
     * Повертає рядкове представлення команди.
     * 
     * @return рядок з описом
     */
    @Override
    public String toString() {
        return "a|dd (додавання)";
    }
    
    /**
     * Виконує команду - дозволяє користувачеві ввести власний номер.
     */
    @Override
    public void execute() {
        if (!(view instanceof ViewResult)) {
            System.out.println("❌ Помилка: view не підтримує додавання даних");
            return;
        }
        
        ViewResult viewResult = (ViewResult) view;
        
        System.out.println("\n📱 ДОДАВАННЯ ВЛАСНОГО НОМЕРА");
        System.out.println("----------------------------------------");
        
        try {
            System.out.print("Введіть номер телефону (наприклад, +380671234567): ");
            String phoneNumber = in.readLine();
            
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                System.out.println("❌ Номер не може бути порожнім!");
                return;
            }
            
            // Додаємо номер через існуючий метод
            viewResult.addEntry(phoneNumber.trim());
            
            System.out.println("✅ Номер успішно додано!");
            System.out.println("----------------------------------------");
            
            // Показуємо оновлену колекцію
            view.viewShow();
            
        } catch (IOException e) {
            System.err.println("❌ Помилка введення: " + e.getMessage());
        }
    }
    
    /**
     * Скасовує останнє додавання (якщо підтримується).
     */
    @Override
    public void undo() {
        if (!(view instanceof ViewResult)) return;
        
        ViewResult viewResult = (ViewResult) view;
        int size = viewResult.getItems().size();
        
        if (size > 0) {
            System.out.println("↩ Скасування останнього додавання...");
            viewResult.removeEntry(size - 1);
            System.out.println("✅ Останній запис видалено");
            view.viewShow();
        } else {
            System.out.println("ℹ Немає записів для видалення");
        }
    }
}