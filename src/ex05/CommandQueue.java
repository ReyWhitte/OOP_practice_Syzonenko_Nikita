package ex05;

import java.util.Vector;

/**
 * Реалізація черги завдань з використанням шаблону Worker Thread.
 * <p>
 * Створює окремий потік-обробник, який виконує завдання з черги.
 * </p>
 * 
 * @author Сизоненко Нікіта
 * @version 1.0
 */
public class CommandQueue implements Queue {
    
    /** Черга завдань */
    private Vector<Command> tasks;
    
    /** Флаг очікування */
    private boolean waiting;
    
    /** Флаг завершення */
    private boolean shutdown;
    
    /**
     * Конструктор.
     * Ініціалізує чергу та запускає потік-обробник.
     */
    public CommandQueue() {
        tasks = new Vector<Command>();
        waiting = false;
        new Thread(new Worker()).start();
    }
    
    /**
     * Встановлює флаг завершення.
     */
    public void shutdown() {
        shutdown = true;
    }
    
    /**
     * Додає завдання до черги.
     * Якщо обробник очікує, пробуджує його.
     * 
     * @param cmd завдання для виконання
     */
    @Override
    public void put(Command cmd) {
        tasks.add(cmd);
        if (waiting) {
            synchronized (this) {
                notifyAll();
            }
        }
    }
    
    /**
     * Вилучає завдання з черги.
     * Якщо черга порожня, переходить в режим очікування.
     * 
     * @return завдання для виконання
     */
    @Override
    public Command take() {
        if (tasks.isEmpty()) {
            synchronized (this) {
                waiting = true;
                try {
                    wait();
                } catch (InterruptedException ie) {
                    waiting = false;
                }
            }
        }
        return tasks.remove(0);
    }
    
    /**
     * Внутрішній клас - обробник потоку (Worker Thread).
     * Виконує завдання з черги в окремому потоці.
     */
    private class Worker implements Runnable {
        
        /**
         * Головний цикл обробника.
         * Постійно вилучає завдання з черги та виконує їх.
         */
        @Override
        public void run() {
            while (!shutdown) {
                Command cmd = take();
                cmd.execute();
            }
        }
    }
}