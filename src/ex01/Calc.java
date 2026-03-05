package ex01;

import java.io.*;

/**
 * Клас для визначення мобільного оператора.
 */
public class Calc {
    
    private static final String FNAME = "Item2d.bin";
    private Item2d result;
    
    public Calc() {
        result = new Item2d();
    }
    
    public Item2d getResult() {
        return result;
    }
    
    /**
     * Очищає номер від нецифрових символів
     * Повертає: 
     * - для 380671234567 -> 0671234567
     * - для 0671234567 -> 0671234567
     */
    private String cleanNumber(String phoneNumber) {
        if (phoneNumber == null) return "";
        
        // Видаляємо всі нецифрові символи
        String digits = phoneNumber.replaceAll("[^0-9]", "");
        
        // Конвертуємо міжнародний формат у національний
        if (digits.startsWith("380") && digits.length() == 12) {
            return "0" + digits.substring(3); // 380671234567 -> 0671234567
        }
        
        // Якщо це вже національний формат (10 цифр, починається з 0)
        if (digits.length() == 10 && digits.startsWith("0")) {
            return digits;
        }
        
        // Якщо 10 цифр без 0 на початку, додаємо 0
        if (digits.length() == 10 && !digits.startsWith("0")) {
            return "0" + digits;
        }
        
        return digits;
    }
    
    /**
     * Витягує код оператора з очищеного номера
     * Для 0671234567 повертає "67"
     */
    private String extractCode(String cleanNumber) {
        if (cleanNumber == null || cleanNumber.length() < 3) return "";
        if (cleanNumber.startsWith("0") && cleanNumber.length() >= 3) {
            return cleanNumber.substring(1, 3); // "067" -> "67"
        }
        return "";
    }
    
    /**
     * Форматує номер для відображення
     * 0671234567 -> +380 (67) 123-45-67
     */
    private String formatNumber(String cleanNumber) {
        if (cleanNumber == null || cleanNumber.length() != 10 || !cleanNumber.startsWith("0")) {
            return cleanNumber;
        }
        
        String code = cleanNumber.substring(1, 3); // 67
        String part1 = cleanNumber.substring(3, 6); // 123
        String part2 = cleanNumber.substring(6, 8); // 45
        String part3 = cleanNumber.substring(8, 10); // 67
        
        return String.format("+380 (%s) %s-%s-%s", code, part1, part2, part3);
    }
    
    /**
     * Перевіряє, чи валідний номер
     */
    private boolean isValid(String cleanNumber) {
        return cleanNumber != null && 
               cleanNumber.length() == 10 && 
               cleanNumber.startsWith("0");
    }
    
    /**
     * Основний метод аналізу номера
     */
    public String init(String phoneNumber) {
        result.setPhoneNumber(phoneNumber);
        
        String clean = cleanNumber(phoneNumber);
        result.setCleanNumber(clean);
        
        boolean valid = isValid(clean);
        result.setValid(valid);
        
        if (valid) {
            String code = extractCode(clean);
            result.setOperatorCode(code);
            
            MobileOperator operator = MobileOperator.detectByCode(code);
            result.setOperatorName(operator.getName());
            
            String formatted = formatNumber(clean);
            result.setFormattedNumber(formatted);
        } else {
            result.setOperatorCode("");
            result.setOperatorName(MobileOperator.UNKNOWN.getName());
            result.setFormattedNumber(phoneNumber);
        }
        
        return result.getOperatorName();
    }
    
    public void show() {
        System.out.println(result);
    }
    
    public void save() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(
            new FileOutputStream(FNAME));
        os.writeObject(result);
        os.close();
    }
    
    public void restore() throws Exception {
        ObjectInputStream is = new ObjectInputStream(
            new FileInputStream(FNAME));
        result = (Item2d) is.readObject();
        is.close();
    }
}