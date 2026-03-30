package ex04;

import ex03.PhoneEntry;
import ex03.View;
import ex03.ViewResult;

/**
 * Консольна команда для зміни елементів колекції.
 * <p>
 * Розширює {@link ChangeItemCommand} та реалізує {@link ConsoleCommand}.
 * Застосовує зміну до всіх елементів колекції.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ChangeConsoleCommand extends ChangeItemCommand implements ConsoleCommand {
    
    /** Посилання на об'єкт відображення */
    private View view;
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт для відображення даних
     */
    public ChangeConsoleCommand(View view) {
        this.view = view;
    }
    
    /**
     * Повертає об'єкт відображення.
     * 
     * @return поточний view
     */
    public View getView() {
        return view;
    }
    
    /**
     * Встановлює об'єкт відображення.
     * 
     * @param view новий об'єкт
     * @return встановлений об'єкт
     */
    public View setView(View view) {
        return this.view = view;
    }
    
    /**
     * Повертає гарячу клавішу команди.
     * 
     * @return символ 'c'
     */
    @Override
    public char getKey() {
        return 'c';
    }
    
    /**
     * Повертає рядкове представлення команди.
     * 
     * @return рядок з описом
     */
    @Override
    public String toString() {
        return "c|hange (масштабування)";
    }
    
    /**
     * Виконує команду - застосовує масштабування до всіх елементів колекції.
     */
    @Override
    public void execute() {
        if (!(view instanceof ViewResult)) {
            System.out.println("Помилка: view не є ViewResult");
            return;
        }
        
        double scale = Math.random() * 2.0; // випадковий коефіцієнт
        setScale(scale);
        
        System.out.println("Масштабування елементів з коефіцієнтом: " + scale);
        
        for (PhoneEntry entry : ((ViewResult) view).getItems()) {
            setItem(entry);
            super.execute(); // викликаємо execute з ChangeItemCommand
        }
        
        view.viewShow();
    }
    
    /**
     * Скасовує останнє виконання команди.
     */
    @Override
    public void undo() {
        if (!(view instanceof ViewResult)) return;
        
        System.out.println("Скасування масштабування...");
        
        for (PhoneEntry entry : ((ViewResult) view).getItems()) {
            setItem(entry);
            super.undo(); // викликаємо undo з ChangeItemCommand
        }
        
        view.viewShow();
    }
}