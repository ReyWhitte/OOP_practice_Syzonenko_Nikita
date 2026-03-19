package ex05;

import java.util.concurrent.TimeUnit;
import ex03.PhoneEntry;
import ex03.ViewResult;

/**
 * Команда для пошуку мінімального та максимального значення.
 * <p>
 * Виконується в окремому потоці (шаблон Worker Thread).
 * Демонструє паралельну обробку даних.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class MinMaxCommand implements Command {
    
    /** Результат - індекс мінімального значення */
    private int resultMin = -1;
    
    /** Результат - індекс максимального значення */
    private int resultMax = -1;
    
    /** Прогрес виконання (0-100) */
    private int progress = 0;
    
    /** Об'єкт для доступу до колекції */
    private ViewResult viewResult;
    
    /**
     * Конструктор з параметром.
     * 
     * @param viewResult об'єкт для доступу до колекції
     */
    public MinMaxCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }
    
    /**
     * Повертає результат - індекс мінімуму.
     * 
     * @return індекс мінімального значення
     */
    public int getResultMin() {
        return resultMin;
    }
    
    /**
     * Повертає результат - індекс максимуму.
     * 
     * @return індекс максимального значення
     */
    public int getResultMax() {
        return resultMax;
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
     * Виконує пошук мінімального та максимального значення.
     * Імітує тривалу обробку за допомогою затримок.
     */
    @Override
    public void execute() {
        progress = 0;
        System.out.println("🔍 Пошук МІНІМУМУ та МАКСИМУМУ...");
        
        int size = viewResult.getItems().size();
        int idx = 0;
        
        for (PhoneEntry entry : viewResult.getItems()) {
            int length = entry.getCleanNumber().length();
            
            // Пошук мінімуму
            if (resultMin == -1 || length < viewResult.getItems().get(resultMin).getCleanNumber().length()) {
                resultMin = idx;
            }
            
            // Пошук максимуму
            if (resultMax == -1 || length > viewResult.getItems().get(resultMax).getCleanNumber().length()) {
                resultMax = idx;
            }
            
            // Оновлюємо прогрес
            idx++;
            progress = idx * 100 / size;
            if (idx % (size / 4) == 0) {
                System.out.println("   MinMax: " + progress + "%");
            }
            
            // Імітуємо тривалу обробку
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                System.err.println("❌ Перервано: " + e);
            }
        }
        
        System.out.println("✅ Мінімум: " + viewResult.getItems().get(resultMin).getFormattedNumber());
        System.out.println("✅ Максимум: " + viewResult.getItems().get(resultMax).getFormattedNumber());
        progress = 100;
    }
}