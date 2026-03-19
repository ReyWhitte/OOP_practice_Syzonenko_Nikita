package ex03;

import java.io.IOException;

/**
 * Інтерфейс Product (шаблон Factory Method).
 * Оголошує методи для відображення об'єктів та роботи з даними.
 * 
 * @author Сизоненко Нікіта
 * @version 2.0
 */
public interface View {
    
    /** Відображає заголовок */
    void viewHeader();
    
    /** Відображає основну частину */
    void viewBody();
    
    /** Відображає завершення */
    void viewFooter();
    
    /** Відображає об'єкт повністю */
    void viewShow();
    
    /** Виконує ініціалізацію даних */
    void viewInit();
    
    /** Зберігає дані для подальшого відновлення */
    void viewSave() throws IOException;
    
    /** Відновлює раніше збережені дані */
    void viewRestore() throws Exception;
}