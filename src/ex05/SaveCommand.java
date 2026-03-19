package ex05;

/**
 * Консольна команда для збереження даних.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class SaveCommand implements Command {
    
    private DataManager dataManager;
    
    public SaveCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    public char getKey() {
        return '5';
    }
    
    @Override
    public String toString() {
        return "save";
    }
    
    @Override
    public void execute() {
        System.out.println("\n💾 ЗБЕРЕЖЕННЯ ДАНИХ");
        dataManager.save();
    }
}