package ex05;

import ex03.PhoneEntry;
import ex03.ViewResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Клас для управління даними з підтримкою undo/redo.
 * <p>
 * Реалізує збереження, відновлення, додавання, видалення
 * та повернення операцій (undo/redo).
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class DataManager {
    
    /** Посилання на view для доступу до колекції */
    private ViewResult view;
    
    /** Історія виконаних операцій для undo */
    private Stack<Runnable> undoStack = new Stack<>();
    
    /** Історія скасованих операцій для redo */
    private Stack<Runnable> redoStack = new Stack<>();
    
    /** Ім'я файлу для збереження */
    private static final String FILE_NAME = "phones.dat";
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт для доступу до колекції
     */
    public DataManager(ViewResult view) {
        this.view = view;
    }
    
    /**
     * Додає новий запис.
     * 
     * @param phoneNumber номер телефону
     */
    public void add(String phoneNumber) {
        int position = view.getItems().size();
        
        // Створюємо запис
        String clean = cleanNumber(phoneNumber);
        String code = extractCode(clean);
        String operator = detectOperator(code);
        
        PhoneEntry entry = new PhoneEntry(phoneNumber, clean, code, operator);
        
        // Додаємо до колекції
        view.getItems().add(entry);
        
        // Зберігаємо операцію для undo
        undoStack.push(() -> {
            view.getItems().remove(position);
            System.out.println("↩ Додавання скасовано");
        });
        
        // Очищаємо redo stack
        redoStack.clear();
        
        System.out.println("✅ Запис додано: " + entry.getFormattedNumber());
    }
    
    /**
     * Видаляє запис за індексом.
     * 
     * @param index індекс запису (1-based)
     * @return true, якщо видалення успішне
     */
    public boolean remove(int index) {
        int idx = index - 1;
        
        if (idx < 0 || idx >= view.getItems().size()) {
            System.out.println("❌ Невірний індекс!");
            return false;
        }
        
        PhoneEntry removed = view.getItems().get(idx);
        PhoneEntry copy = new PhoneEntry(
            removed.getOriginalNumber(),
            removed.getCleanNumber(),
            removed.getOperatorCode(),
            removed.getOperatorName()
        );
        
        // Видаляємо з колекції
        view.getItems().remove(idx);
        
        // Зберігаємо операцію для undo
        undoStack.push(() -> {
            view.getItems().add(idx, copy);
            System.out.println("↩ Видалення скасовано");
        });
        
        // Очищаємо redo stack
        redoStack.clear();
        
        System.out.println("✅ Запис видалено: " + copy.getFormattedNumber());
        return true;
    }
    
    /**
     * Повертає останню скасовану операцію.
     */
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("ℹ Немає операцій для повторення");
            return;
        }
        
        Runnable action = redoStack.pop();
        action.run();
        System.out.println("✅ Операцію повторено");
    }
    
    /**
     * Скасовує останню операцію.
     */
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("ℹ Немає операцій для скасування");
            return;
        }
        
        Runnable action = undoStack.pop();
        
        // Зберігаємо для redo
        redoStack.push(action);
        
        action.run();
        System.out.println("↩ Операцію скасовано");
    }
    
    /**
     * Зберігає дані у файл.
     */
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(view.getItems()));
            System.out.println("✅ Дані збережено у файл: " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("❌ Помилка збереження: " + e.getMessage());
        }
    }
    
    /**
     * Відновлює дані з файлу.
     */
    @SuppressWarnings("unchecked")
    public void restore() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("ℹ Файл не знайдено: " + FILE_NAME);
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<PhoneEntry> loaded = (ArrayList<PhoneEntry>) ois.readObject();
            view.getItems().clear();
            view.getItems().addAll(loaded);
            System.out.println("✅ Дані відновлено з файлу: " + FILE_NAME);
            System.out.println("   Завантажено записів: " + loaded.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Помилка відновлення: " + e.getMessage());
        }
    }
    
    /**
     * Очищає всі дані.
     */
    public void clearAll() {
        if (view.getItems().isEmpty()) {
            System.out.println("ℹ Колекція вже порожня");
            return;
        }
        
        ArrayList<PhoneEntry> copy = new ArrayList<>(view.getItems());
        
        view.getItems().clear();
        
        // Зберігаємо для undo
        undoStack.push(() -> {
            view.getItems().addAll(copy);
            System.out.println("↩ Очищення скасовано");
        });
        
        redoStack.clear();
        
        System.out.println("✅ Всі дані видалено");
    }
    
    /**
     * Допоміжний метод для очищення номера.
     */
    private String cleanNumber(String phoneNumber) {
        if (phoneNumber == null) return "";
        String digits = phoneNumber.replaceAll("[^0-9]", "");
        
        if (digits.startsWith("380") && digits.length() == 12) {
            return "0" + digits.substring(3);
        }
        if (digits.length() == 10 && digits.startsWith("0")) {
            return digits;
        }
        if (digits.length() == 10 && !digits.startsWith("0")) {
            return "0" + digits;
        }
        return digits;
    }
    
    /**
     * Допоміжний метод для отримання коду оператора.
     */
    private String extractCode(String cleanNumber) {
        if (cleanNumber == null || cleanNumber.length() < 3) return "";
        if (cleanNumber.startsWith("0") && cleanNumber.length() >= 3) {
            return cleanNumber.substring(1, 3);
        }
        return "";
    }
    
    /**
     * Допоміжний метод для визначення оператора.
     */
    private String detectOperator(String code) {
        if (code == null || code.isEmpty()) return "Невідомий";
        
        String[][] operators = {
            {"67", "68", "96", "97", "98", "Київстар"},
            {"50", "66", "95", "99", "Vodafone"},
            {"63", "73", "93", "lifecell"},
            {"91", "3Mob"},
            {"89", "94", "Інтертелеком"},
            {"92", "Peoplenet"}
        };
        
        for (String[] op : operators) {
            for (int i = 0; i < op.length - 1; i++) {
                if (code.equals(op[i])) {
                    return op[op.length - 1];
                }
            }
        }
        
        return "Невідомий";
    }
}