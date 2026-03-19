package ex05;

/**
 * Консольна команда для скасування операції.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class UndoCommand implements Command {
    
    private DataManager dataManager;
    
    public UndoCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    public char getKey() {
        return '8';
    }
    
    @Override
    public String toString() {
        return "undo";
    }
    
    @Override
    public void execute() {
        System.out.println("\n↩ СКАСУВАННЯ ОПЕРАЦІЇ");
        dataManager.undo();
    }
}