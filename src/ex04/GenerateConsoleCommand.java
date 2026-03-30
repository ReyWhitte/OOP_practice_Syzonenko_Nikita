package ex04;

import ex03.View;


/**
 * Консольна команда для генерації тестових даних.
 * <p>
 * Реалізує {@link ConsoleCommand} для створення випадкових номерів.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class GenerateConsoleCommand implements ConsoleCommand {
    
    /** Посилання на об'єкт відображення */
    private View view;
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт для відображення даних
     */
    public GenerateConsoleCommand(View view) {
        this.view = view;
    }
    
    /**
     * Повертає гарячу клавішу команди.
     * 
     * @return символ 'g'
     */
    @Override
    public char getKey() {
        return 'g';
    }
    
    /**
     * Повертає рядкове представлення команди.
     * 
     * @return рядок з описом
     */
    @Override
    public String toString() {
        return "g|enerate (генерація)";
    }
    
    /**
     * Виконує команду - генерує тестові дані.
     */
    @Override
    public void execute() {
        System.out.println("Генерація тестових даних...");
        view.viewInit();
        view.viewShow();
    }
}