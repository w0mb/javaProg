package lab1;

import java.util.Random;

public class MultiThreadProcessor {
    public void processTask(int threadId, int taskLength) {
        long startTime = System.currentTimeMillis();
        Random random = new Random();
        StringBuilder progressBar = new StringBuilder("[");

        for (int i = 0; i <= taskLength; i += 10) {
            try {
                Thread.sleep(random.nextInt(200)); // Симуляция работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            progressBar.append("#");
            System.out.printf("Поток %d | ID: %d | Прогресс: %-10s %d%%%n",
                    threadId, Thread.currentThread().getId(), progressBar, (i * 100) / taskLength);
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.printf("Поток %d | ID: %d | Завершено за %d мс%n",
                threadId, Thread.currentThread().getId(), elapsedTime);
    }
}
