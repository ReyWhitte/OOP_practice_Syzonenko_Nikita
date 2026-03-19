package ex05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Клас меню програми.
 * 
 * @author Сизоненко Нікіта
 * @version 2.0
 */
public class Menu {
    
    private Map<Character, Command> commands = new HashMap<>();
    private List<Command> commandList = new ArrayList<>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Додає команду до меню.
     * 
     * @param cmd команда для додавання
     */
    public void add(Command cmd) {
        if (cmd instanceof AddCommand) {
            commands.put('3', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof RemoveCommand) {
            commands.put('4', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof ViewCommand) {
            commands.put('1', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof SaveCommand) {
            commands.put('5', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof RestoreCommand) {
            commands.put('6', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof GenerateCommand) {
            commands.put('2', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof ExecuteParallelCommand) {
            commands.put('7', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof UndoCommand) {
            commands.put('8', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof RedoCommand) {
            commands.put('9', cmd);
            commandList.add(cmd);
        } else if (cmd instanceof ClearCommand) {
            commands.put('0', cmd);
            commandList.add(cmd);
        }
    }
    
    /**
     * Виводить меню.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n📌 МЕНЮ ПРОГРАМИ:\n");
        sb.append("   ┌─────┬─────────────────────┐\n");
        for (Command cmd : commandList) {
            sb.append(String.format("   │  %s  │ %-19s │\n", getKeyForCommand(cmd), cmd));
        }
        sb.append("   ├─────┼─────────────────────┤\n");
        sb.append("   │  q  │ quit                │\n");
        sb.append("   └─────┴─────────────────────┘\n");
        sb.append("👉 Ваш вибір: ");
        return sb.toString();
    }
    
    /**
     * Отримує ключ для команди.
     */
    private char getKeyForCommand(Command cmd) {
        if (cmd instanceof AddCommand) return '3';
        if (cmd instanceof RemoveCommand) return '4';
        if (cmd instanceof ViewCommand) return '1';
        if (cmd instanceof SaveCommand) return '5';
        if (cmd instanceof RestoreCommand) return '6';
        if (cmd instanceof GenerateCommand) return '2';
        if (cmd instanceof ExecuteParallelCommand) return '7';
        if (cmd instanceof ClearCommand) return '0';
        if (cmd instanceof RedoCommand) return '9';
        if (cmd instanceof UndoCommand) return '8';
        
        return '?';
    }
    
    /**
     * Запускає меню.
     */
    public void run() {
        while (true) {
            System.out.print(this);
            
            try {
                String input = reader.readLine();
                if (input == null || input.trim().isEmpty()) continue;
                
                char key = input.trim().charAt(0);
                
                if (key == 'q') {
                    System.out.println("👋 Програма завершена.");
                    break;
                }
                
                Command cmd = commands.get(key);
                if (cmd != null) {
                    cmd.execute();
                } else {
                    System.out.println("❌ Невідома команда!");
                }
                
            } catch (IOException e) {
                System.err.println("❌ Помилка введення: " + e.getMessage());
            }
        }
    }
}