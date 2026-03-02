package test;

import main.Main;   // ← ДУЖЕ ВАЖЛИВО

public class Test {

    public static void main(String[] args) {

        String[] testArgs = {"One", "Two", "Three"};

        int result = Main.countArgs(testArgs);

        System.out.println("Очікується: 3");
        System.out.println("Отримано: " + result);

        if (result == 3) {
            System.out.println("Тест пройдено ✅");
        } else {
            System.out.println("Тест НЕ пройдено ❌");
        }
    }
}