package ex04;

import ex03.View;

/**
 * Консольна команда для перегляду колекції.
 * <p>
 * Реалізує {@link ConsoleCommand} для відображення даних.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ViewConsoleCommand implements ConsoleCommand {
    
    /** Посилання на об'єкт відображення */
    private View view;
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт для відображення даних
     */
    public ViewConsoleCommand(View view) {
        this.view = view;
    }
    
    /**
     * Повертає гарячу клавішу команди.
     * 
     * @return символ 'v'
     */
    @Override
    public char getKey() {
        return 'v';
    }
    
    /**
     * Повертає рядкове представлення команди.
     * 
     * @return рядок з описом
     */
    @Override
    public String toString() {
        return "v|iew (колекції)";
    }
    
    /**
     * Виконує команду - відображає колекцію.
     */
    @Override
    public void execute() {
        System.out.println("Перегляд колекції:");
        view.viewShow();
    }
}