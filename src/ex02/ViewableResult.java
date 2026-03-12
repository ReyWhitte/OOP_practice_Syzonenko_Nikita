package ex02;

/**
 * Клас ConcreteCreator (шаблон Factory Method).
 * Реалізує фабричний метод для створення об'єктів ViewResult.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ViewableResult implements Viewable {
    
    /**
     * Створює новий об'єкт ViewResult.
     * @return об'єкт ViewResult
     */
    @Override
    public View getView() {
        return new ViewResult();
    }
    
    /**
     * Створює новий об'єкт ViewResult з вказаною кількістю елементів.
     * @param n кількість елементів
     * @return об'єкт ViewResult
     */
    public View getView(int n) {
        return new ViewResult(n);
    }
}