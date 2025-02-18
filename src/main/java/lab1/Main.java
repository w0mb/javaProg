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
            StartFileCreating.startCreating(scanner, inputFiles);
        }

        if (answer1.equals("n") || !inputFiles.isEmpty()) {
            StartFileProcess.start(inputFiles, args);
        }

        scanner.close();
    }
}
