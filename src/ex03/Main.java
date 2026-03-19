package ex03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Головний клас програми для завдання №4.
 * Реалізує інтерактивний консольний інтерфейс для роботи з колекцією
 * телефонних номерів та визначення мобільних операторів.
 * У завданні №4 додано підтримку табличного виведення даних.
 * 
 * @author Сизоненко Нікіта
 * @version 3.0
 * @see View
 * @see ViewTable
 * @see ViewableTable
 */
public class Main {
    
    /** 
     * Об'єкт для відображення даних.
     * Використовує поліморфізм для роботи з різними типами відображення.
     */
    protected View view;
    
    /**
     * Конструктор з параметром.
     * 
     * @param view об'єкт, що реалізує інтерфейс {@link View}
     */
    public Main(View view) {
        this.view = view;
    }
    
    /**
     * Повертає поточний об'єкт відображення.
     * 
     * @return об'єкт типу {@link View}
     */
    protected View getView() {
        return view;
    }
    
    /**
     * Відображає головне меню програми та обробляє команди користувача.
     */
    protected void menu() {
        String s = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        do {
            System.out.println("\n========== МЕНЮ (Завдання №4) ==========");
            System.out.println("'0'  - вихід");
            System.out.println("'1'  - переглянути колекцію");
            System.out.println("'2'  - згенерувати тестові дані");
            System.out.println("'3'  - додати новий номер");
            System.out.println("'4'  - видалити номер за індексом");
            System.out.println("'5'  - зберегти у файл");
            System.out.println("'6'  - відновити з файлу");
            System.out.println("'7'  - змінити ширину таблиці (завдання №4)");
            System.out.println("'8'  - вивести з тимчасовою шириною (Завдання №4)");
            System.out.println("'9'  - інформація про таблицю (Завдання №4)");
            System.out.println("'i'  - довідка");
            System.out.print("Введіть команду: ");
            
            try {
                s = in.readLine();
            } catch (IOException e) {
                System.out.println("Помилка введення: " + e);
                System.exit(0);
            }
            
            if (s == null || s.isEmpty()) continue;
            
            switch (s) {
                case "0":
                    System.out.println("Програма завершує роботу.");
                    break;
                case "1":
                    System.out.println("Перегляд колекції:");
                    view.viewShow();
                    break;
                case "2":
                    handleGenerateCommand(in);
                    break;
                case "3":
                    handleAddCommand(in);
                    break;
                case "4":
                    handleRemoveCommand(in);
                    break;
                case "5":
                    handleSaveCommand();
                    break;
                case "6":
                    handleRestoreCommand();
                    break;
                case "7":
                    handleWidthCommand(in);
                    break;
                case "8":
                    handleTempWidthCommand(in);
                    break;
                case "9":
                    handleInfoCommand();
                    break;
                case "i":
                    showHelp();
                    break;
                default:
                    System.out.println("✗ Невідома команда!");
            }
        } while (!s.equals("0"));
    }
    
    /**
     * Обробляє команду генерації тестових даних.
     * Демонструє перевантаження (overloading) методу init().
     * 
     * @param in буфер для читання введення користувача
     */
    private void handleGenerateCommand(BufferedReader in) {
        System.out.println("Генерація тестових даних:");
        if (view instanceof ViewTable) {
            System.out.println("1 - init()");
            System.out.println("2 - init(width)");
            System.out.println("3 - init(width, stepX)");
            System.out.print("Виберіть варіант: ");
            try {
                String choice = in.readLine();
                ViewTable table = (ViewTable) view;
                switch (choice) {
                    case "1": 
                        table.viewInit(); 
                        break;
                    case "2": 
                        System.out.print("Ширина: ");
                        table.init(Integer.parseInt(in.readLine())); 
                        break;
                    case "3":
                        System.out.print("Ширина: ");
                        int w = Integer.parseInt(in.readLine());
                        System.out.print("Крок: ");
                        double step = Double.parseDouble(in.readLine());
                        table.init(w, step);
                        break;
                    default: 
                        table.viewInit();
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        } else {
            view.viewInit();
        }
        view.viewShow();
    }
    
    /**
     * Обробляє команду додавання нового номера.
     * Демонструє перевизначення (overriding) методу addEntry().
     * 
     * @param in буфер для читання введення користувача
     */
    private void handleAddCommand(BufferedReader in) {
        System.out.println("Додавання номера:");
        System.out.print("Введіть номер: ");
        try {
            String number = in.readLine();
            if (view instanceof ViewTable) {
                ((ViewTable) view).addEntry(number);
            } else {
                ((ViewResult) view).addEntry(number);
            }
            view.viewShow();
        } catch (IOException e) {
            System.out.println("Помилка введення");
        }
    }
    
    /**
     * Обробляє команду видалення номера за індексом.
     * Демонструє перевизначення (overriding) методу removeEntry().
     * 
     * @param in буфер для читання введення користувача
     */
    private void handleRemoveCommand(BufferedReader in) {
        System.out.println("Видалення номера:");
        System.out.print("Введіть індекс: ");
        try {
            int index = Integer.parseInt(in.readLine()) - 1;
            if (view instanceof ViewTable) {
                ((ViewTable) view).removeEntry(index);
            } else {
                ((ViewResult) view).removeEntry(index);
            }
            view.viewShow();
        } catch (Exception e) {
            System.out.println("✗ Невірний формат!");
        }
    }
    
    /**
     * Обробляє команду збереження даних у файл.
     * Використовує серіалізацію для збереження колекції.
     */
    private void handleSaveCommand() {
        try {
            view.viewSave();
        } catch (IOException e) {
            System.out.println("✗ Помилка: " + e);
        }
    }
    
    /**
     * Обробляє команду відновлення даних з файлу.
     * Використовує десеріалізацію для відновлення колекції.
     */
    private void handleRestoreCommand() {
        try {
            view.viewRestore();
            view.viewShow();
        } catch (Exception e) {
            System.out.println("✗ Помилка: " + e);
        }
    }
    
    /**
     * Обробляє команду зміни ширини таблиці.
     * 
     * @param in буфер для читання введення користувача
     */
    private void handleWidthCommand(BufferedReader in) {
        if (view instanceof ViewTable) {
            System.out.print("Нова ширина таблиці: ");
            try {
                int w = Integer.parseInt(in.readLine());
                ((ViewTable) view).setWidth(w);
                view.viewShow();
            } catch (Exception e) {
                System.out.println("✗ Невірний формат!");
            }
        } else {
            System.out.println("✗ Поточний view не підтримує табличний вивід!");
        }
    }
    
    /**
     * Обробляє команду виведення з тимчасовою шириною.
     * Демонструє перевантаження (overloading) методу viewShow().
     * 
     * @param in буфер для читання введення користувача
     */
    private void handleTempWidthCommand(BufferedReader in) {
        if (view instanceof ViewTable) {
            System.out.print("Тимчасова ширина: ");
            try {
                int w = Integer.parseInt(in.readLine());
                ((ViewTable) view).viewShow(w);
            } catch (Exception e) {
                System.out.println("✗ Невірний формат!");
            }
        } else {
            System.out.println("✗ Поточний view не підтримує табличний вивід!");
        }
    }
    
    /**
     * Обробляє команду виведення інформації про таблицю.
     */
    private void handleInfoCommand() {
        if (view instanceof ViewTable) {
            ViewTable table = (ViewTable) view;
            System.out.println("\n📊 ІНФОРМАЦІЯ ПРО ТАБЛИЦЮ:");
            System.out.println("   Ширина: " + table.getWidth());
            System.out.println("   Записів: " + table.getItems().size());
            System.out.println("   Клас: " + table.getClass().getSimpleName());
        } else {
            System.out.println("   Записів: " + ((ViewResult) view).getItems().size());
            System.out.println("   Клас: " + view.getClass().getSimpleName());
        }
    }
    
    /**
     * Виводить довідкову інформацію.
     * Містить коди мобільних операторів України та
     * пояснення до команд завданя №4.
     */
    protected void showHelp() {
        System.out.println("\n===== КОДИ МОБІЛЬНИХ ОПЕРАТОРІВ =====");
        System.out.printf("%-15s %s\n", "Київстар", "67, 68, 96, 97, 98");
        System.out.printf("%-15s %s\n", "Vodafone", "50, 66, 95, 99");
        System.out.printf("%-15s %s\n", "lifecell", "63, 73, 93");
        System.out.printf("%-15s %s\n", "3Mob", "91");
        System.out.printf("%-15s %s\n", "Інтертелеком", "89, 94");
        System.out.printf("%-15s %s\n", "Peoplenet", "92");
        System.out.println("\n📊 Команди завдання №4:");
        System.out.println("  'w' - змінити ширину таблиці");
        System.out.println("  't' - вивести з тимчасовою шириною");
        System.out.println("  'i' - інформація про таблицю");
        System.out.println("\nФормат введення: +380671234567, 0671234567, (067) 123-45-67");
    }
    
    /**
     * Головний метод програми.
     * Демонструє поліморфізм - створює об'єкт {@link ViewTable} через фабрику
     * {@link ViewableTable}, але працює з ним через інтерфейс {@link View}.
     * 
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("======= Завдання №4 =======");
        System.out.println("Визначення мобільного оператора + табличний вивід\n");
        
        ViewableTable creator = new ViewableTable();
        View view = creator.getView(60);
        
        Main main = new Main(view);
        main.menu();
    }
}