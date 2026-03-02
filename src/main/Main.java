package main;

public class Main {

    public static int countArgs(String[] args) {
        return args.length;
    }

    public static void printArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Аргументи відсутні.");
        } else {
            for (int i = 0; i < args.length; i++) {
                System.out.println("Аргумент " + (i + 1) + ": " + args[i]);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Кількість аргументів: " + countArgs(args));
        printArgs(args);
    }
}
