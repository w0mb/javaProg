package lab1;

import java.util.List;
import java.util.Scanner;

public class StartFileCreating {
    public static void startCreating(Scanner scanner, List<String> inputFiles) {
        System.out.print("Enter number of threads and total objects: ");
        int threadCount = scanner.nextInt();
        int totalObjects = scanner.nextInt();

        System.out.print("Enter filename for thread output: ");
        String filename = scanner.next();

        System.out.print("Are you want to make this file input for DataProcessor? (y/n): ");
        if (scanner.next().equalsIgnoreCase("y")) {
            inputFiles.add(filename);
        }

        FileGenerator generator = new FileGenerator.Builder()
        .setThreadCount(threadCount)
        .setTotalObjects(totalObjects)
        .setFilename(filename)
        .build();
        generator.generate();
    }
}
