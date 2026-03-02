package main;

public class Main {
    public static void main(String[] args) {
        System.out.println("Програма запущена!");
        System.out.println("Отримано аргументів: " + args.length);
        
        if (args.length == 0) {
            System.out.println("Аргументи командного рядка відсутні.");
        } else {
            System.out.println("Список аргументів:");
            for (int i = 0; i < args.length; i++) {
                System.out.println((i + 1) + ": " + args[i]);
            }
        }
    }
}