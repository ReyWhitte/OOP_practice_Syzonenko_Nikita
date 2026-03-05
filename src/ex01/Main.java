package ex01;

import java.io.*;
import java.util.Scanner;

/**
 * Головний клас програми для демонстрації визначення мобільного оператора
 * в діалоговому режимі з можливістю серіалізації/десеріалізації.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class Main {
    
    /** Об'єкт класу Calc для виконання обчислень */
    private Calc calc = new Calc();
    
    /** Сканер для введення даних з клавіатури */
    private Scanner scanner = new Scanner(System.in);
    
    /**
     * Відображає меню програми та обробляє команди користувача.
     */
    private void menu() {
        String command = null;
        
        do {
            System.out.println("\n===== МЕНЮ =====");
            System.out.println("'q' - вихід");
            System.out.println("'v' - переглянути поточні результати");
            System.out.println("'d' - визначити оператора за номером");
            System.out.println("'s' - зберегти результати у файл");
            System.out.println("'r' - відновити результати з файлу");
            System.out.println("'h' - показати довідку по кодам операторів");
            System.out.print("Введіть команду: ");
            
            command = scanner.nextLine().trim();
            
            switch (command) {
                case "q":
                    System.out.println("Програма завершує роботу.");
                    break;
                    
                case "v":
                    System.out.println("Поточні результати:");
                    calc.show();
                    break;
                    
                case "d":
                    detectOperator();
                    break;
                    
                case "s":
                    saveData();
                    break;
                    
                case "r":
                    restoreData();
                    break;
                    
                case "h":
                    showHelp();
                    break;
                    
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        } while (!command.equals("q"));
        
        scanner.close();
    }
    
    /**
     * Виконує визначення оператора за введеним номером телефону.
     */
    private void detectOperator() {
        System.out.print("Введіть номер телефону абонента (в будь-якому форматі): ");
        String phoneNumber = scanner.nextLine();
        
        String operator = calc.init(phoneNumber);
        Item2d result = calc.getResult();
        
        System.out.println("\n===== РЕЗУЛЬТАТИ АНАЛІЗУ =====");
        
        if (result.isValid()) {
            System.out.printf("Оригінальний номер: %s\n", result.getPhoneNumber());
            System.out.printf("Очищений номер: %s\n", result.getCleanNumber());
            System.out.printf("Код оператора: %s\n", result.getOperatorCode());
            System.out.printf("Мобільний оператор: %s\n", operator);
            System.out.printf("Форматований номер: %s\n", result.getFormattedNumber());
        } else {
            System.out.printf("Номер '%s' НЕ є валідним українським мобільним номером!\n", 
                            result.getPhoneNumber());
            System.out.println("\nВалідні формати номерів:");
            System.out.println("  +380671234567");
            System.out.println("  0671234567");
            System.out.println("  380671234567");
            System.out.println("  (067) 123-45-67");
        }
        
        System.out.println("\nДетальна інформація:");
        calc.show();
    }
    
    /**
     * Зберігає поточні результати у файл.
     */
    private void saveData() {
        try {
            calc.save();
            System.out.println("✓ Дані успішно збережено у файл 'Item2d.bin'");
            calc.show();
        } catch (IOException e) {
            System.out.println("✗ Помилка серіалізації: " + e.getMessage());
        }
    }
    
    /**
     * Відновлює результати з файлу.
     */
    private void restoreData() {
        try {
            System.out.println("Відновлення даних з файлу 'Item2d.bin'...");
            calc.restore();
            System.out.println("✓ Дані успішно відновлено!");
            
            // Демонстрація особливості transient полів
            Item2d result = calc.getResult();
            if (result.getPhoneNumber() == null) {
                System.out.println("ℹ transient поле phoneNumber = null (значення за замовчуванням)");
            }
            
            calc.show();
        } catch (Exception e) {
            System.out.println("✗ Помилка десеріалізації: " + e.getMessage());
            System.out.println("  Можливі причини: файл не існує або пошкоджений");
        }
    }
    
    /**
     * Показує довідку по кодам мобільних операторів.
     */
    private void showHelp() {
        System.out.println("\n===== КОДИ МОБІЛЬНИХ ОПЕРАТОРІВ УКРАЇНИ =====");
        System.out.println("┌───────────────┬─────────────────────────┐");
        System.out.printf("│ %-13s │ %-23s │\n", "Оператор", "Коди");
        System.out.println("├───────────────┼─────────────────────────┤");
        System.out.printf("│ %-13s │ %-23s │\n", "Київстар", "067, 068, 096, 097, 098");
        System.out.printf("│ %-13s │ %-23s │\n", "Vodafone", "050, 066, 095, 099");
        System.out.printf("│ %-13s │ %-23s │\n", "lifecell", "063, 073, 093");
        System.out.printf("│ %-13s │ %-23s │\n", "3Mob", "091");
        System.out.printf("│ %-13s │ %-23s │\n", "Інтертелеком", "089, 094");
        System.out.printf("│ %-13s │ %-23s │\n", "Peoplenet", "092");
        System.out.println("└───────────────┴─────────────────────────┘");
        
        System.out.println("\n📞 ПРИКЛАДИ ФОРМАТІВ НОМЕРІВ:");
        System.out.println("   ✓ +380671234567");
        System.out.println("   ✓ 0671234567");
        System.out.println("   ✓ 380671234567");
        System.out.println("   ✓ (067) 123-45-67");
        System.out.println("   ✓ 067 123 45 67");
        System.out.println();
        System.out.println("   ✗ 1234567 (закороткий)");
        System.out.println("   ✗ 123456789012345 (задовгий)");
    }
    
    /**
     * Головний метод програми.
     * 
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   ПРОГРАМА ВИЗНАЧЕННЯ МОБІЛЬНОГО       ║");
        System.out.println("║              ОПЕРАТОРА                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        main.menu();
    }
}