package ex03;

/**
 * Інтерфейс Creator (шаблон Factory Method).
 * Оголошує метод, що "фабрикує" об'єкти View.
 * 
 * @author Сизоненко Нікіта
 * @version 2.0
 */
public interface Viewable {
    
    /**
     * Створює об'єкт, що реалізує інтерфейс View.
     * @return новий об'єкт View
     */
    View getView();
}