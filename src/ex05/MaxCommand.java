package ex05;

import java.util.concurrent.TimeUnit;
import ex03.PhoneEntry;
import ex03.ViewResult;

/**
 * Команда для пошуку максимального значення в колекції.
 * <p>
 * Виконується в окремому потоці (шаблон Worker Thread).
 * Демонструє паралельну обробку даних.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class MaxCommand implements Command {
    
    /** Результат - індекс елемента з максимальним значенням */
    private int result = -1;
    
    /** Прогрес виконання (0-100) */
    private int progress = 0;
    
    /** Об'єкт для доступу до колекції */
    private ViewResult viewResult;
    
    /**
     * Конструктор з параметром.
     * 
     * @param viewResult об'єкт для доступу до колекції
     */
    public MaxCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }
    
    /**
     * Повертає результат виконання.
     * 
     * @return індекс елемента з максимальним значенням
     */
    public int getResult() {
        return result;
    }
    
    /**
     * Перевіряє, чи виконується команда.
     * 
     * @return true, якщо виконання ще триває
     */
    public boolean running() {
        return progress < 100;
    }
    
    /**
     * Виконує пошук максимального значення.
     * Імітує тривалу обробку за допомогою затримок.
     */
    @Override
    public void execute() {
        progress = 0;
        System.out.println("🔍 Пошук МАКСИМУМУ...");
        
        int size = viewResult.getItems().size();
        result = 0;
        
        for (int idx = 1; idx < size; idx++) {
            // Порівнюємо за довжиною номера (для демонстрації)
            String current = viewResult.getItems().get(result).getCleanNumber();
            String next = viewResult.getItems().get(idx).getCleanNumber();
            
            if (current.length() < next.length()) {
                result = idx;
            }
            
            // Оновлюємо прогрес
            progress = idx * 100 / size;
            if (idx % (size / 3) == 0) {
                System.out.println("   Максимум: " + progress + "%");
            }
            
            // Імітуємо тривалу обробку
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("❌ Перервано: " + e);
            }
        }
        
        PhoneEntry entry = viewResult.getItems().get(result);
        System.out.println("✅ Максимум знайдено: " + entry.getFormattedNumber());
        progress = 100;
    }
}