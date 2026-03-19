package ex04;

import ex03.View;

/**
 * Консольна команда для відновлення даних з файлу.
 * <p>
 * Реалізує {@link ConsoleCommand} для десеріалізації колекції.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class RestoreConsoleCommand implements ConsoleCommand {
    
    /** Посилання на об'єкт відображення */
    private View view;
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт для відображення даних
     */
    public RestoreConsoleCommand(View view) {
        this.view = view;
    }
    
    /**
     * Повертає гарячу клавішу команди.
     * 
     * @return символ 'r'
     */
    @Override
    public char getKey() {
        return 'r';
    }
    
    /**
     * Повертає рядкове представлення команди.
     * 
     * @return рядок з описом
     */
    @Override
    public String toString() {
        return "r|estore (відновлення)";
    }
    
    /**
     * Виконує команду - відновлює дані з файлу.
     */
    @Override
    public void execute() {
        System.out.println("Відновлення даних...");
        try {
            view.viewRestore();
        } catch (Exception e) {
            System.err.println("Помилка десеріалізації: " + e);
        }
        view.viewShow();
    }
}