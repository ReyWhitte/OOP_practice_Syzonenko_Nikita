package ex04;

import java.util.ArrayList;
import java.util.List;

/**
 * Макрокоманда (шаблон Command).
 * <p>
 * Дозволяє виконувати групу команд як одну.
 * Демонструє поняття "макрокоманда" з завдання.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class MacroCommand implements Command {
    
    /** Список команд, що входять до макрокоманди */
    private List<Command> commands = new ArrayList<>();
    
    /**
     * Додає команду до макрокоманди.
     * 
     * @param command команда для додавання
     * @return додана команда
     */
    public Command add(Command command) {
        commands.add(command);
        return command;
    }
    
    /**
     * Видаляє команду з макрокоманди.
     * 
     * @param command команда для видалення
     * @return true, якщо команда була видалена
     */
    public boolean remove(Command command) {
        return commands.remove(command);
    }
    
    /**
     * Повертає кількість команд у макрокоманді.
     * 
     * @return кількість команд
     */
    public int size() {
        return commands.size();
    }
    
    /**
     * Очищає макрокоманду.
     */
    public void clear() {
        commands.clear();
    }
    
    /**
     * Виконує всі команди макрокоманди послідовно.
     */
    @Override
    public void execute() {
        System.out.println("🔄 Виконання макрокоманди (" + commands.size() + " команд)");
        for (Command cmd : commands) {
            cmd.execute();
        }
        System.out.println("✅ Макрокоманда виконана");
    }
    
    /**
     * Скасовує всі команди макрокоманди у зворотному порядку.
     */
    @Override
    public void undo() {
        System.out.println("🔄 Скасування макрокоманди...");
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
        System.out.println("✅ Макрокоманда скасована");
    }
}