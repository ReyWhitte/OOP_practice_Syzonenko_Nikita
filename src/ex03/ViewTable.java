package ex03;

/**
 * Клас для табличного виведення результатів (ConcreteProduct).
 * Розширює {@link ViewResult}, додаючи можливість виведення даних
 * у вигляді форматованої таблиці. Демонструє:
 * перевизначення (overriding) методів суперкласу
 * перевантаження (overloading) методів
 * поліморфізм (динамічне зв'язування)
 
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 * @see ViewResult
 * @see View
 */
public class ViewTable extends ViewResult {
    
    /** Ширина таблиці за замовчуванням */
    public static final int DEFAULT_WIDTH = 60;
    
    /** Поточна ширина таблиці */
    private int width;
    
    /**
     * Конструктор за замовчуванням.
     * Встановлює ширину {@link #DEFAULT_WIDTH}.
     */
    public ViewTable() {
        width = DEFAULT_WIDTH;
    }
    
    /**
     * Конструктор з параметром ширини.
     * 
     * @param width ширина таблиці
     */
    public ViewTable(int width) {
        this.width = width;
    }
    
    /**
     * Конструктор з параметрами ширини та кількості елементів.
     * Викликає конструктор суперкласу {@link ViewResult#ViewResult(int)}.
     * 
     * @param width ширина таблиці
     * @param n кількість елементів колекції
     */
    public ViewTable(int width, int n) {
        super(n);
        this.width = width;
    }
    
    /**
     * Повертає поточну ширину таблиці.
     * 
     * @return ширина таблиці
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Встановлює нову ширину таблиці.
     * 
     * @param width нова ширина
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Виводить горизонтальну лінію таблиці.
     * Використовує поточну ширину {@link #width}.
     */
    private void outLine() {
        System.out.println("-".repeat(width));
    }
    
    /**
     * Виводить подвійну горизонтальну лінію таблиці.
     * Використовується для заголовка.
     */
    private void outDoubleLine() {
        System.out.println("=".repeat(width));
    }
    
    /**
     * Виводить заголовок таблиці.
     * Форматує назви колонок відповідно до ширини.
     */
    private void outHeader() {
        int colWidth = (width - 5) / 2;
        System.out.printf("| %-" + colWidth + "s | %-" + colWidth + "s |%n", 
                         "НОМЕР ТЕЛЕФОНУ", "ОПЕРАТОР");
        outLine();
    }
    
    /**
     * Виводить тіло таблиці.
     * Для кожного елемента колекції виводить номер та оператора.
     * Якщо текст занадто довгий, обрізає з додаванням "...".
     */
    private void outBody() {
        if (getItems().isEmpty()) {
            System.out.printf("| %-" + (width - 4) + "s |%n", "Колекція порожня");
            return;
        }
        
        int colWidth = (width - 5) / 2;
        String format = "| %-" + colWidth + "s | %-" + colWidth + "s |%n";
        
        for (PhoneEntry entry : getItems()) {
            String number = entry.getFormattedNumber();
            String operator = entry.getOperatorName();
            
            if (number.length() > colWidth) {
                number = number.substring(0, colWidth - 3) + "...";
            }
            if (operator.length() > colWidth) {
                operator = operator.substring(0, colWidth - 3) + "...";
            }
            
            System.out.printf(format, number, operator);
        }
    }
    
    /**
     * Виводить завершальну лінію таблиці та підсумок.
     */
    private void outFooter() {
        outLine();
        System.out.println("Загальна кількість: " + getItems().size());
    }
    
    // ==================== ПЕРЕВАНТАЖЕННЯ (OVERLOADING) ====================
    
    /**
     * Перевантажений метод ініціалізації.
     * Встановлює ширину таблиці та викликає {@link #viewInit()}.
     * 
     * @param width нова ширина таблиці
     */
    public void init(int width) {
        this.width = width;
        viewInit();
        System.out.println("Таблиця ініціалізована з шириною " + width);
    }
    
    /**
     * Перевантажений метод ініціалізації.
     * Встановлює ширину таблиці та імітує ініціалізацію з кроком.
     * 
     * @param width нова ширина таблиці
     * @param stepX крок ініціалізації (для демонстрації перевантаження)
     */
    public void init(int width, double stepX) {
        this.width = width;
        System.out.println("Таблиця ініціалізована з шириною " + width + " та кроком " + stepX);
    }
    
    /**
     * Перевантажений метод виведення таблиці.
     * Тимчасово змінює ширину для виведення, потім відновлює попередню.
     * 
     * @param tempWidth тимчасова ширина таблиці
     */
    public void viewShow(int tempWidth) {
        int oldWidth = width;
        width = tempWidth;
        viewShow();
        width = oldWidth;
        System.out.println("Виведено з тимчасовою шириною " + tempWidth);
    }
    
    // ==================== ПЕРЕВИЗНАЧЕННЯ (OVERRIDING) ====================
    
    /**
     * Перевизначений метод додавання запису.
     * Додає інформаційне повідомлення перед викликом методу суперкласу.
     * 
     * @param phoneNumber номер телефону
     */
    @Override
    public void addEntry(String phoneNumber) {
        System.out.print("➕ Додавання запису... ");
        super.addEntry(phoneNumber);
    }
    
    /**
     * Перевизначений метод видалення запису.
     * Додає інформаційне повідомлення перед викликом методу суперкласу.
     * 
     * @param index індекс запису
     */
    @Override
    public void removeEntry(int index) {
        System.out.print("🗑️ Видалення запису... ");
        super.removeEntry(index);
    }
    
    /**
     * Перевизначений метод виведення заголовка.
     * Виводить заголовок таблиці.
     */
    @Override
    public void viewHeader() {
        System.out.println("\n📊 ТАБЛИЦЯ ТЕЛЕФОННИХ НОМЕРІВ");
        outDoubleLine();
        outHeader();
    }
    
    /**
     * Перевизначений метод виведення тіла.
     * Виводить тіло таблиці.
     */
    @Override
    public void viewBody() {
        outBody();
    }
    
    /**
     * Перевизначений метод виведення завершення.
     * Виводить завершальну лінію таблиці.
     */
    @Override
    public void viewFooter() {
        outFooter();
    }
    
    /**
     * Перевизначений метод повного виведення.
     * Виводить таблицю повністю.
     */
    @Override
    public void viewShow() {
        viewHeader();
        viewBody();
        viewFooter();
    }
}