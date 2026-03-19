package ex04;

/**
 * Інтерфейс консольної команди.
 * <p>
 * Розширює {@link Command} додаванням гарячої клавіші.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public interface ConsoleCommand extends Command {
    
    /**
     * Повертає гарячу клавішу для команди.
     * 
     * @return символ гарячої клавіші
     */
    char getKey();
}