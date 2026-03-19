package ex04;

import ex03.PhoneEntry;

/**
 * Команда для зміни елемента колекції (шаблон Command).
 * <p>
 * Масштабує значення (для демонстрації) - множить на коефіцієнт.
 * Підтримує операцію undo.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ChangeItemCommand implements Command {
    
    /** Елемент, над яким виконується команда */
    private PhoneEntry item;
    
    /** Коефіцієнт масштабування */
    private double scale;
    
    /** Попереднє значення для undo */
    private String previousNumber;
    
    /**
     * Конструктор за замовчуванням.
     */
    public ChangeItemCommand() {
        this.scale = 1.0;
    }
    
    /**
     * Конструктор з параметрами.
     * 
     * @param item елемент для обробки
     * @param scale коефіцієнт масштабування
     */
    public ChangeItemCommand(PhoneEntry item, double scale) {
        this.item = item;
        this.scale = scale;
    }
    
    /**
     * Встановлює елемент для обробки.
     * 
     * @param item новий елемент
     * @return встановлений елемент
     */
    public PhoneEntry setItem(PhoneEntry item) {
        return this.item = item;
    }
    
    /**
     * Повертає поточний елемент.
     * 
     * @return елемент
     */
    public PhoneEntry getItem() {
        return item;
    }
    
    /**
     * Встановлює коефіцієнт масштабування.
     * 
     * @param scale новий коефіцієнт
     * @return встановлений коефіцієнт
     */
    public double setScale(double scale) {
        return this.scale = scale;
    }
    
    /**
     * Повертає коефіцієнт масштабування.
     * 
     * @return коефіцієнт
     */
    public double getScale() {
        return scale;
    }
    
    /**
     * Виконує команду - масштабує номер телефону (демонстраційна функція).
     * <p>
     * Для демонстрації просто додає scale до номера (в реальності тут могла б бути
     * математична обробка даних).
     * </p>
     */
    @Override
    public void execute() {
        if (item == null) return;
        
        // Зберігаємо попереднє значення для undo
        previousNumber = item.getOriginalNumber();
        
        // Демонстраційна обробка - додаємо scale до номера
        String newNumber = item.getOriginalNumber() + " (scaled: " + scale + ")";
        
        // Створюємо новий запис (в реальному коді тут була б обробка)
        PhoneEntry newEntry = new PhoneEntry(
            newNumber,
            item.getCleanNumber(),
            item.getOperatorCode(),
            item.getOperatorName()
        );
        
        // Копіюємо поля (спрощено для демонстрації)
        item.setOriginalNumber(newNumber);
    }
    
    /**
     * Скасовує виконання команди.
     */
    @Override
    public void undo() {
        if (item != null && previousNumber != null) {
            item.setOriginalNumber(previousNumber);
        }
    }
}