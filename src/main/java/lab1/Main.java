package lab1;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String filename = "src/files/input.txt";
            CheckType checkType = new CheckType();
            List<Object> dataList = checkType.checkTypesFromFile(filename);  // Получаем все данные

            // Создаем объекты Distribution для каждого типа данных
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

        } catch (Exception e) {
            e.printStackTrace();  // Печать стека ошибок
        }
    }
}
