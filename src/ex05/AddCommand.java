package ex05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Консольна команда для додавання даних.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class AddCommand implements Command {
    
    private DataManager dataManager;
    private BufferedReader reader;
    
    public AddCommand(DataManager dataManager) {
        this.dataManager = dataManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public char getKey() {
        return '3';
    }
    
    @Override
    public String toString() {
        return "add";
    }
    
    @Override
    public void execute() {
        System.out.println("\n📱 ДОДАВАННЯ НОМЕРА");
        System.out.print("Введіть номер телефону: ");
        
        try {
            String number = reader.readLine();
            if (number == null || number.trim().isEmpty()) {
                System.out.println("❌ Номер не може бути порожнім!");
                return;
            }
            dataManager.add(number.trim());
        } catch (IOException e) {
            System.err.println("❌ Помилка введення: " + e.getMessage());
        }
    }
}