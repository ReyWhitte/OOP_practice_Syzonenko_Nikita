package ex05;

import java.util.concurrent.TimeUnit;
import ex03.PhoneEntry;
import ex03.ViewResult;

/**
 * Команда для обчислення середньої довжини номера в колекції.
 * <p>
 * Виконується в окремому потоці (шаблон Worker Thread).
 * Демонструє паралельну обробку даних.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class AvgCommand implements Command {
    
    /** Результат - середня довжина номера */
    private double result = 0.0;
    
    /** Прогрес виконання (0-100) */
    private int progress = 0;
    
    /** Об'єкт для доступу до колекції */
    private ViewResult viewResult;
    
    /**
     * Конструктор з параметром.
     * 
     * @param viewResult об'єкт для доступу до колекції
     */
    public AvgCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }
    
    /**
     * Повертає результат виконання.
     * 
     * @return середня довжина номера
     */
    public double getResult() {
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
     * Обчислює середню довжину номера в колекції.
     * Імітує тривалу обробку за допомогою затримок.
     */
    @Override
    public void execute() {
        progress = 0;
        System.out.println("🔍 Обчислення СЕРЕДНЬОГО...");
        
        int size = viewResult.getItems().size();
        int totalLength = 0;
        int idx = 1;
        
        for (PhoneEntry entry : viewResult.getItems()) {
            totalLength += entry.getCleanNumber().length();
            
            // Оновлюємо прогрес
            progress = idx * 100 / size;
            if (idx % (size / 3) == 0) {
                System.out.println("   Середнє: " + progress + "%");
            }
            idx++;
            
            // Імітуємо тривалу обробку
            try {
                TimeUnit.MILLISECONDS.sleep(150);
            } catch (InterruptedException e) {
                System.err.println("❌ Перервано: " + e);
            }
        }
        
        result = (double) totalLength / size;
        System.out.printf("✅ Середня довжина номера: %.2f\n", result);
        progress = 100;
    }
}