package ex05;

import ex03.View;

/**
 * Консольна команда для генерації тестових даних.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class GenerateCommand implements Command {
    
    private View view;
    
    public GenerateCommand(View view) {
        this.view = view;
    }
    
    public char getKey() {
        return '2';
    }
    
    @Override
    public String toString() {
        return "generate";
    }
    
    @Override
    public void execute() {
        System.out.println("\n🔄 ГЕНЕРАЦІЯ ТЕСТОВИХ ДАНИХ");
        view.viewInit();
        System.out.println("✅ Тестові дані згенеровано");
        view.viewShow();
    }
}