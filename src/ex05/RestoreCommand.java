package ex05;

/**
 * Консольна команда для відновлення даних.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class RestoreCommand implements Command {
    
    private DataManager dataManager;
    
    public RestoreCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    public char getKey() {
        return '6'; // l - load
    }
    
    @Override
    public String toString() {
        return "load";
    }
    
    @Override
    public void execute() {
        System.out.println("\n📂 ВІДНОВЛЕННЯ ДАНИХ");
        dataManager.restore();
    }
}