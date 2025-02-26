package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartFileCreating {
    public static void startCreating(Scanner scanner, List<String> inputFiles) {
        System.out.print("Enter number of threads and total objects: ");
            int THREAD_COUNT = scanner.nextInt();
            int TOTAL_OBJECTS = scanner.nextInt();
            System.out.print("Enter filename for thread output: ");
            String FILENAME = scanner.next();

            System.out.print("Are you want to make this file input for DataProcessor? (y/n): ");
            String answer2 = scanner.next();
            if (answer2.equals("y")) {inputFiles.add(FILENAME);}

            List<MultiThreadProcessor.RandomObjectWriter> threads = new ArrayList<>();

            for (int i = 0; i < THREAD_COUNT; i++) {
                System.out.print("Thread " + (i + 1) + ": [                                                  ] 0%\n");
            }

            for (int i = 0; i < THREAD_COUNT; i++) {
                MultiThreadProcessor.RandomObjectWriter thread = new MultiThreadProcessor.RandomObjectWriter(i + 1, TOTAL_OBJECTS / THREAD_COUNT, FILENAME);
                thread.start();
                threads.add(thread);
            }

            for (MultiThreadProcessor.RandomObjectWriter thread : threads) {
                try {
                    thread.join();
                    System.out.println("Thread #" + thread.getThreadNumber() + " (ID: " + thread.getId() + ") completed in " + thread.getExecutionTime() + " ms");
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                }
            }
    }
}
