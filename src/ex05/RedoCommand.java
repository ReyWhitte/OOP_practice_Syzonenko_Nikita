package ex05;

/**
 * Консольна команда для повторення операції.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class RedoCommand implements Command {
    
    private DataManager dataManager;
    
    public RedoCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    public char getKey() {
        return '9'; // e - redo
    }
    
    
    @Override
    public String toString() {
        return "redo";
    }
    
    @Override
    public void execute() {
        System.out.println("\n🔁 ПОВТОРЕННЯ ОПЕРАЦІЇ");
        dataManager.redo();
    }
}