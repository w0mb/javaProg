package lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CheckType {

    // Метод для проверки всех типов данных из файла и их возврата в виде списка
    public List<Object> checkTypesFromFile(String filename) {
        List<Object> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Проверка на Integer (целые числа)
                if (line.matches("^-?\\d+$")) {
                    dataList.add(Integer.parseInt(line));
                }
                // Проверка на Long (целые числа с буквой L в конце)
                else if (line.matches("^-?\\d+$")) {
                    try {
                        dataList.add(Long.parseLong(line));  // Пытаемся записать как Long
                    } catch (NumberFormatException e) {
                        // Игнорируем ошибку, если не получилось
                    }
                }
                // Проверка на Double (вещественные числа)
                else if (line.matches("^-?\\d*\\.\\d+$")) {
                    dataList.add(Double.parseDouble(line));
                }
                // Строки: если не попали под вышеописанные условия
                else {
                    dataList.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Ошибка чтения файла: " + ex.getMessage());
        }
        return dataList; // Возвращаем все собранные данные
    }
}
