package ex04;

import java.io.IOException;
import ex03.View;

/**
 * Консольна команда для збереження даних у файл.
 * <p>
 * Реалізує {@link ConsoleCommand} для серіалізації колекції.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class SaveConsoleCommand implements ConsoleCommand {
    
    /** Посилання на об'єкт відображення */
    private View view;
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт для відображення даних
     */
    public SaveConsoleCommand(View view) {
        this.view = view;
    }
    
    /**
     * Повертає гарячу клавішу команди.
     * 
     * @return символ 's'
     */
    @Override
    public char getKey() {
        return 's';
    }
    
    /**
     * Повертає рядкове представлення команди.
     * 
     * @return рядок з описом
     */
    @Override
    public String toString() {
        return "s|ave (збереження)";
    }
    
    /**
     * Виконує команду - зберігає дані у файл.
     */
    @Override
    public void execute() {
        System.out.println("Збереження даних...");
        try {
            view.viewSave();
        } catch (IOException e) {
            System.err.println("Помилка серіалізації: " + e);
        }
        view.viewShow();
    }
}