package ex05;

import java.util.concurrent.TimeUnit;
import ex03.View;
import ex03.ViewResult;

/**
 * Консольна команда для паралельного виконання.
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class ExecuteParallelCommand implements Command {
    
    private View view;
    
    public ExecuteParallelCommand(View view) {
        this.view = view;
    }
    
    public char getKey() {
        return 'p';
    }
    
    @Override
    public String toString() {
        return "p'arallel";
    }
    
    @Override
    public void execute() {
        if (!(view instanceof ViewResult)) {
            System.out.println("❌ Помилка: view не підтримує обробку даних");
            return;
        }
        
        ViewResult viewResult = (ViewResult) view;
        
        if (viewResult.getItems().isEmpty()) {
            System.out.println("❌ Колекція порожня! Спочатку додайте дані.");
            return;
        }
        
        CommandQueue queue1 = new CommandQueue();
        CommandQueue queue2 = new CommandQueue();
        
        MaxCommand maxCommand = new MaxCommand(viewResult);
        AvgCommand avgCommand = new AvgCommand(viewResult);
        MinMaxCommand minMaxCommand = new MinMaxCommand(viewResult);
        
        System.out.println("\n🚀 ПАРАЛЕЛЬНА ОБРОБКА ДАНИХ...");
        
        queue1.put(minMaxCommand);
        queue2.put(maxCommand);
        queue2.put(avgCommand);
        
        try {
            while (minMaxCommand.running() || maxCommand.running() || avgCommand.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            
            queue1.shutdown();
            queue2.shutdown();
            
            TimeUnit.SECONDS.sleep(1);
            
        } catch (InterruptedException e) {
            System.err.println("❌ Помилка: " + e);
        }
        
        System.out.println("\n📊 РЕЗУЛЬТАТИ:");
        if (minMaxCommand.getResultMin() != -1) {
            System.out.println("   Мінімум: " + viewResult.getItems().get(minMaxCommand.getResultMin()).getFormattedNumber());
        }
        if (minMaxCommand.getResultMax() != -1) {
            System.out.println("   Максимум: " + viewResult.getItems().get(minMaxCommand.getResultMax()).getFormattedNumber());
        }
        System.out.printf("   Середня довжина номера: %.2f\n", avgCommand.getResult());
    }
}