package ex02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Головний клас програми.
 * Використовує шаблон Factory Method для створення об'єкта відображення.
 * 
 * @author Сизоненко Нікіта
 * @version 2.0
 */
public class Main {
    
    /** Об'єкт для відображення даних (використовує інтерфейс View) */
    private View view;
    
    /**
     * Конструктор з параметром.
     * @param view об'єкт, що реалізує інтерфейс View
     */
    public Main(View view) {
        this.view = view;
    }
    
    /**
     * Відображає меню програми та обробляє команди.
     */
    protected void menu() {				//змінення значення кнопок
        String s = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        do {
            System.out.println("\n========== МЕНЮ ==========");
            System.out.println("'0' - вихід");
            System.out.println("'1' - переглянути колекцію");
            System.out.println("'2' - згенерувати тестові дані");
            System.out.println("'3' - додати новий номер");
            System.out.println("'4' - видалити номер за індексом");
            System.out.println("'5' - зберегти у файл");
            System.out.println("'6' - відновити з файлу");
            System.out.println("'7' - довідка по операторах");
            System.out.print("Введіть команду: ");
            
            try {
                s = in.readLine();
            } catch (IOException e) {
                System.out.println("Помилка введення: " + e);
                System.exit(0);
            }
            
            if (s == null || s.isEmpty()) continue;
            
            switch (s.charAt(0)) {
                case '0':
                    System.out.println("Програма завершує роботу.");
                    break;
                    
                case '1':
                    System.out.println("Перегляд колекції:");
                    view.viewShow();
                    break;
                    
                case '2':
                    System.out.println("Генерація тестових даних:");
                    view.viewInit();
                    view.viewShow();
                    break;
                    
                case '3':
                    System.out.println("Додавання нового номера:");
                    if (view instanceof ViewResult) {
                        ((ViewResult) view).interactiveAdd();
                        view.viewShow();
                    }
                    break;
                    
                case '4':
                    System.out.println("Видалення номера:");
                    System.out.print("Введіть індекс для видалення: ");
                    try {
                        int index = Integer.parseInt(in.readLine()) - 1;
                        if (view instanceof ViewResult) {
                            ((ViewResult) view).removeEntry(index);
                            view.viewShow();
                        }
                    } catch (NumberFormatException | IOException e) {
                        System.out.println("✗ Невірний формат числа!");
                    }
                    break;
                    
                case '5':
                    System.out.println("Збереження даних:");
                    try {
                        view.viewSave();
                    } catch (IOException e) {
                        System.out.println("✗ Помилка серіалізації: " + e);
                    }
                    break;
                    
                case '6':
                    System.out.println("Відновлення даних:");
                    try {
                        view.viewRestore();
                        view.viewShow();
                    } catch (Exception e) {
                        System.out.println("✗ Помилка десеріалізації: " + e);
                    }
                    break;
                    
                case '7':
                    showHelp();
                    break;
                    
                default:
                    System.out.println("✗ Невідома команда!");
            }
        } while (s.charAt(0) != '0');
    }
    
    /**
     * Показує довідку по операторах.
     */
    private void showHelp() {
        System.out.println("\n===== КОДИ МОБІЛЬНИХ ОПЕРАТОРІВ =====");
        System.out.printf("%-15s %s\n", "Київстар", "67, 68, 96, 97, 98");
        System.out.printf("%-15s %s\n", "Vodafone", "50, 66, 95, 99");
        System.out.printf("%-15s %s\n", "lifecell", "63, 73, 93");
        System.out.printf("%-15s %s\n", "3Mob", "91");
        System.out.printf("%-15s %s\n", "Інтертелеком", "89, 94");
        System.out.printf("%-15s %s\n", "Peoplenet", "92");
        System.out.println("\nФормат введення: +380671234567, 0671234567, (067) 123-45-67");
    }
    
    /**
     * Головний метод програми.
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        System.out.println("======= Завдання №3 =======");
        System.out.println("Визначення мобільного оператора (Factory Method)");
        
        // Використання Factory Method для створення об'єкта
        Viewable creator = new ViewableResult();
        View view = creator.getView();
        
        Main main = new Main(view);
        main.menu();
    }
}