package test;

import main.Main;

public class Test {
    public static void main(String[] args) {
        System.out.println("=== Запуск тесту програми ===\n");
        
        // Тест 1: без аргументів
        System.out.println("Тест 1: Запуск без аргументів");
        Main.main(new String[]{});
        System.out.println();
        
        // Тест 2: з одним аргументом
        System.out.println("Тест 2: Запуск з одним аргументом");
        Main.main(new String[]{"Привіт"});
        System.out.println();
        
        // Тест 3: з декількома аргументами
        System.out.println("Тест 3: Запуск з декількома аргументами");
        Main.main(new String[]{"Java", "програмування", "тест", "123"});
        System.out.println();
        
        // Тест 4: передаємо аргументи з Test в Main
        System.out.println("Тест 4: Передача аргументів з командного рядка Test");
        Main.main(args);
        
        System.out.println("\n=== Тестування завершено ===");
    }
}