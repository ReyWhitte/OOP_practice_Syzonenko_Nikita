package ex05;

import ex03.View;

/**
 * Консольна команда для перегляду даних.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ViewCommand implements Command {
    
    private View view;
    
    public ViewCommand(View view) {
        this.view = view;
    }
    
    public char getKey() {
        return '1';
    }
    
    @Override
    public String toString() {
        return "view";
    }
    
    @Override
    public void execute() {
        System.out.println("\n📋 ПЕРЕГЛЯД КОЛЕКЦІЇ:");
        view.viewShow();
    }
}