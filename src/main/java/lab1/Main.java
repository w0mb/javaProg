package lab1;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String filename = "src/files/input.txt";

//            Scanner in = new Scanner(System.in);
//            System.out.print("Вводите данные для input файла:");
//            String input_data = in.nextLine();
//            try(FileWriter writer = new FileWriter(filename, true)){
//                writer.write(input_data.toString()+"\n");
//            }
//            catch(IOException ex){System.out.println("ошибка ввода"+ex.getMessage());}
            CheckType checkType = new CheckType();
            List<Object> dataList = checkType.checkTypesFromFile(filename);

            Distribution<Integer> intDistribution = new Distribution<>(Integer.class);
            Distribution<Long> longDistribution = new Distribution<>(Long.class);
            Distribution<Double> doubleDistribution = new Distribution<>(Double.class);
            Distribution<String> stringDistribution = new Distribution<>(String.class);

            // Обрабатываем все данные и передаем их в соответствующий Distribution
            for (Object data : dataList) {
                if (data instanceof Integer) {
                    intDistribution.makeDistribution((Integer) data);
                } else if (data instanceof Long) {
                    longDistribution.makeDistribution((Long) data);
                } else if (data instanceof Double) {
                    doubleDistribution.makeDistribution((Double) data);
                } else if (data instanceof String) {
                    stringDistribution.makeDistribution((String) data);
                }
            }
            if (args[0].equals("-s")) {
                System.out.println(checkType.getIntCount());
                System.out.println(checkType.getDoubleCount());
                System.out.println(checkType.getStringCount());
                System.out.println(checkType.getLongCount());
            }
            if (args[0].equals("-f")) {
                System.out.println("колво целых типа:" + checkType.getIntCount());
                System.out.println("Колво дабл типа:" + checkType.getDoubleCount());
                System.out.println("Колво строк типа:" + checkType.getStringCount());
                System.out.println("Колво Лонг типа:" + checkType.getLongCount());
                System.out.println("Сумма:" + checkType.getSum());
                System.out.println("Максимальное" + checkType.getMax());
                System.out.println("Минимальное:" + checkType.getMin());
                System.out.println("Среднее:" + checkType.getSum().divide(BigDecimal.valueOf(checkType.getIntCount()), 5, BigDecimal.ROUND_HALF_UP));
                System.out.println("Мин строка" + checkType.getStringMin());
                System.out.println("Макс строка" + checkType.getStringMax());

            }
        } catch (Exception e) {
            e.printStackTrace();  // Печать стека ошибок
        }
    }
}
