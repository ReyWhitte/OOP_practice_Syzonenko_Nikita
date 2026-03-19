package ex04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Клас меню як контейнер команд (шаблон Command).
 * <p>
 * Реалізує інтерфейс {@link Command} і містить колекцію консольних команд.
 * Підтримує історію команд для скасування (undo).
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class Menu implements Command {
    
    /** Колекція консольних команд */
    private List<ConsoleCommand> commands = new ArrayList<>();
    
    /** Історія виконаних команд для undo */
    private Stack<Command> history = new Stack<>();
    
    /**
     * Додає нову команду до меню.
     * 
     * @param command команда для додавання
     * @return додана команда
     */
    public ConsoleCommand add(ConsoleCommand command) {
        commands.add(command);
        return command;
    }
    
    /**
     * Повертає рядкове представлення меню.
     * 
     * @return рядок з усіма командами
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n📋 МЕНЮ:\n");
        for (ConsoleCommand cmd : commands) {
            sb.append("  ").append(cmd).append("\n");
        }
        sb.append("  u|ndo (скасування)\n");
        sb.append("  q|uit (вихід)\n");
        sb.append("👉 Виберіть команду: ");
        return sb.toString();
    }
    
    /**
     * Виконує головний цикл меню.
     * Очікує введення користувача та виконує відповідні команди.
     */
    @Override
    public void execute() {
        String input = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        mainLoop: while (true) {
            System.out.print(this);
            
            try {
                input = in.readLine();
            } catch (IOException e) {
                System.err.println("Помилка введення: " + e);
                System.exit(0);
            }
            
            if (input == null || input.isEmpty()) continue;
            
            char key = input.charAt(0);
            
            // Вихід
            if (key == 'q') {
                System.out.println("👋 Програма завершує роботу.");
                break mainLoop;
            }
            
            // Undo
            if (key == 'u') {
                undo();
                continue;
            }
            
            // Пошук команди за гарячою клавішею
            for (ConsoleCommand cmd : commands) {
                if (key == cmd.getKey()) {
                    System.out.println("▶ Виконання команди: " + cmd);
                    cmd.execute();
                    history.push(cmd); // додаємо до історії
                    continue mainLoop;
                }
            }
            
            System.out.println("❌ Невідома команда!");
        }
    }
    
    /**
     * Скасовує останню виконану команду.
     */
    public void undo() {
        if (history.isEmpty()) {
            System.out.println("ℹ Немає команд для скасування");
            return;
        }
        
        Command lastCmd = history.pop();
        System.out.println("↩ Скасування команди: " + lastCmd);
        lastCmd.undo();
    }
    
    /**
     * Виконує макрокоманду (демонстрація).
     * 
     * @param macro макрокоманда для виконання
     */
    public void executeMacro(MacroCommand macro) {
        macro.execute();
        history.push(macro);
    }
}