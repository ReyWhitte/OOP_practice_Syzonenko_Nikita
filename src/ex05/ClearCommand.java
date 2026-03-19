package ex05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Консольна команда для очищення всіх даних.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ClearCommand implements Command {
    
    private DataManager dataManager;
    private BufferedReader reader;
    
    public ClearCommand(DataManager dataManager) {
        this.dataManager = dataManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public char getKey() {
        return '0';
    }
    
    @Override
    public String toString() {
        return "clear all";
    }
    
    @Override
    public void execute() {
        System.out.println("\n⚠️ ОЧИЩЕННЯ ВСІХ ДАНИХ");
        System.out.print("Ви впевнені? (y/n): ");
        
        try {
            String answer = reader.readLine();
            if (answer != null && answer.trim().equalsIgnoreCase("y")) {
                dataManager.clearAll();
            } else {
                System.out.println("ℹ Операцію скасовано");
            }
        } catch (IOException e) {
            System.err.println("❌ Помилка введення: " + e.getMessage());
        }
    }
}