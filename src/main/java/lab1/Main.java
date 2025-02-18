package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Are you want to create new file with data? (y/n): ");
        String answer1 = scanner.next();

        List<String> inputFiles = new ArrayList<>();

        if (answer1.equals("y")) {
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

        if (answer1.equals("n") || !inputFiles.isEmpty()) {
            try {
                CommandLineArgs commandLineArgs = new CommandLineArgs(args);
                DataProcessor dataProcessor = new DataProcessor(commandLineArgs);
                inputFiles.addAll(commandLineArgs.getInputFiles()); // Добавляем файлы из аргументов командной строки

                for (String filename : inputFiles) {
                    try {
                        dataProcessor.setFilename(filename);
                        dataProcessor.processFile();
                    } catch (Exception e) {
                        System.out.println("Error uploading file " + filename + ": " + e.getMessage());
                    }
                }

                dataProcessor.printStatistics(commandLineArgs);
                dataProcessor.writeResults();
            } catch (Exception e) {
                System.out.println("Error during file write: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
