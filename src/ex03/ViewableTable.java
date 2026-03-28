package ex03;

/**
 * Фабрика для створення об'єктів {@link ViewTable} (ConcreteCreator).
 * Реалізує шаблон проектування Factory Method.
 * Розширює {@link ViewableResult} і додає перевантажені фабричні методи
 * для створення таблиць з різними параметрами.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ViewableTable extends ViewableResult {
    
    /**
     * Перевизначений фабричний метод.
     * Створює об'єкт {@link ViewTable} з шириною за замовчуванням.
     */
    @Override
    public View getView() {
        return new ViewTable();
    }
    
    /**
     * Перевантажений фабричний метод.
     * Створює об'єкт {@link ViewTable} з вказаною шириною.
     */
    public View getView(int width) {
        return new ViewTable(width);
    }
    
    /**
     * Перевантажений фабричний метод.
     * Створює об'єкт {@link ViewTable} з вказаною шириною та кількістю елементів.
     */
    public View getView(int width, int n) {
        return new ViewTable(width, n);
    }
}