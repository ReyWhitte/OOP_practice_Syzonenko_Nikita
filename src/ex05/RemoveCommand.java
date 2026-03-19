package ex05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Консольна команда для видалення даних.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class RemoveCommand implements Command {
    
    private DataManager dataManager;
    private BufferedReader reader;
    
    public RemoveCommand(DataManager dataManager) {
        this.dataManager = dataManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public char getKey() {
        return '4';
    }
    
    @Override
    public String toString() {
        return "remove";
    }
    
    @Override
    public void execute() {
        System.out.println("\n🗑️ ВИДАЛЕННЯ НОМЕРА");
        System.out.print("Введіть індекс для видалення: ");
        
        try {
            String input = reader.readLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println("❌ Індекс не може бути порожнім!");
                return;
            }
            
            int index = Integer.parseInt(input.trim());
            dataManager.remove(index);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Невірний формат числа!");
        } catch (IOException e) {
            System.err.println("❌ Помилка введення: " + e.getMessage());
        }
    }
}