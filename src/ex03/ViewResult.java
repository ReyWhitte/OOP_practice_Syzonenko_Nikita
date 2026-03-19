package ex03;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Клас ConcreteProduct (шаблон Factory Method).
 * Реалізує інтерфейс View для роботи з колекцією телефонних номерів.
 * 
 * @author Сизоненко Нікіта
 * @version 3.0
 */
public class ViewResult implements View {
    
    /** Ім'я файлу для серіалізації */
    private static final String FNAME = "phones.bin";
    
    /** Кількість елементів за замовчуванням */
    private static final int DEFAULT_NUM = 5;
    
    /** Колекція телефонних записів (замість одного Item2d) */
    private ArrayList<PhoneEntry> items = new ArrayList<>();
    
    /** Сканер для введення даних */
    private transient Scanner scanner = new Scanner(System.in);
    
    /**
     * Конструктор за замовчуванням.
     * Створює колекцію з DEFAULT_NUM елементів.
     */
    public ViewResult() {
        this(DEFAULT_NUM);
    }
    
    /**
     * Конструктор з параметром.
     * @param n кількість елементів у колекції
     */
    public ViewResult(int n) {
        for (int i = 0; i < n; i++) {
            items.add(new PhoneEntry());
        }
    }
    
    /**
     * Повертає колекцію елементів.
     * @return ArrayList з PhoneEntry
     */
    public ArrayList<PhoneEntry> getItems() {
        return items;
    }
    
    /**
     * Очищає номер від нецифрових символів.
     * @param phoneNumber вхідний номер
     * @return очищений номер (10 цифр)
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
     * Витягує код оператора з номера.
     * @param cleanNumber очищений номер
     * @return код оператора
     */
    private String extractCode(String cleanNumber) {
        if (cleanNumber == null || cleanNumber.length() < 3) return "";
        if (cleanNumber.startsWith("0") && cleanNumber.length() >= 3) {
            return cleanNumber.substring(1, 3);
        }
        return "";
    }
    
    /**
     * Визначає оператора для номера.
     * @param phoneNumber номер телефону
     * @return назва оператора
     */
    public String detectOperator(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return MobileOperator.UNKNOWN.getName();
        }
        
        String clean = cleanNumber(phoneNumber);
        String code = extractCode(clean);
        
        // Додайте логування для налагодження
        System.out.println("Debug: clean=" + clean + ", code=" + code);
        
        MobileOperator operator = MobileOperator.detectByCode(code);
        return operator.getName();
    }
    
    /**
     * Додає новий запис до колекції.
     * @param phoneNumber номер телефону
     */
    public void addEntry(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.println("✗ Номер не може бути порожнім");
            return;
        }
        
        String clean = cleanNumber(phoneNumber);
        String code = extractCode(clean);
        MobileOperator operator = MobileOperator.detectByCode(code);
        
        PhoneEntry entry = new PhoneEntry(
            phoneNumber,
            clean,
            code,
            operator.getName()
        );
        
        items.add(entry);
        System.out.println("✓ Запис додано: " + entry);
    }
    
    /**
     * Видаляє запис з колекції за індексом.
     * @param index індекс запису (0-based)
     */
    public void removeEntry(int index) {
        if (items.isEmpty()) {
            System.out.println("✗ Колекція порожня");
            return;
        }
        
        if (index >= 0 && index < items.size()) {
            PhoneEntry removed = items.remove(index);
            System.out.println("✓ Запис видалено: " + removed);
        } else {
            System.out.println("✗ Невірний індекс! Допустимі індекси: 0-" + (items.size()-1));
        }
    }
    
    /**
     * Ініціалізує колекцію тестовими даними.
     * @param count кількість тестових записів
     */
    public void initTestData(int count) {
        String[] testNumbers = {
            "+380671234567",
            "0501234567",
            "0931234567",
            "0911234567",
            "0891234567",
            "0991234567",
            "0731234567",
            "0971234567"
        };
        
        for (int i = 0; i < count && i < testNumbers.length; i++) {
            addEntry(testNumbers[i]);
        }
    }
    
    @Override
    public void viewHeader() {
        System.out.println("\n=====================================");
        System.out.println("   КОЛЕКЦІЯ ТЕЛЕФОННИХ НОМЕРІВ");
        System.out.println("=====================================");
    }
    
    @Override
    public void viewBody() {
        if (items.isEmpty()) {
            System.out.println("   Колекція порожня");
        } else {
            System.out.printf("%-4s | %-20s | %-10s | %-15s\n", 
                            "№", "Номер", "Код", "Оператор");
            System.out.println("-----+----------------------+------------+-----------------");
            
            for (int i = 0; i < items.size(); i++) {
                PhoneEntry entry = items.get(i);
                System.out.printf("%-4d | %-20s | %-10s | %-15s\n",
                                i + 1,
                                entry.getFormattedNumber(),
                                entry.getOperatorCode(),
                                entry.getOperatorName());
            }
        }
    }
    
    @Override
    public void viewFooter() {
        System.out.println("=====================================");
        System.out.println("   Загальна кількість: " + items.size());
        System.out.println("=====================================\n");
    }
    
    @Override
    public void viewShow() {
        viewHeader();
        viewBody();
        viewFooter();
    }
    
    @Override
    public void viewInit() {
        items.clear();
        initTestData(5);
        System.out.println("✓ Згенеровано тестові дані");
    }
    
    @Override
    public void viewSave() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(
            new FileOutputStream(FNAME));
        os.writeObject(items);
        os.flush();
        os.close();
        System.out.println("✓ Дані збережено у файл " + FNAME);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void viewRestore() throws Exception {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FNAME))) {
            Object obj = is.readObject();
            if (obj instanceof ArrayList) {
                items = (ArrayList<PhoneEntry>) obj;
            } else {
                throw new Exception("Невірний тип даних у файлі");
            }
        }
        System.out.println("✓ Дані відновлено з файлу " + FNAME);
    }
    
    /**
     * Інтерактивне додавання номера.
     */
    public void interactiveAdd() {
        System.out.print("Введіть номер телефону: ");
        String number = scanner.nextLine();
        addEntry(number);
    }
}