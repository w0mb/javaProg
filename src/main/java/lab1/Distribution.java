package lab1;

import java.io.*;

public class Distribution<Type> {
    private Class<Type> this_type;
    private boolean isFirstWrite = true;  // Флаг, чтобы очищать файлы только один раз
    private boolean need_delete;
    public Distribution(Class<Type> type) {
        this.this_type = type;
        need_delete = false;//перегрузить надо будет конструктор
    }
    public Distribution(Class<Type> type, boolean need_delete) {
        this.this_type = type;
        this.need_delete = need_delete;//перегрузить надо будет конструктор
    }

    public void makeDistribution(Type data) {
        String fileName = "";

        // Определяем имя файла в зависимости от типа
        if (this_type == Integer.class) {
            fileName = "src/files/integerTypes.txt";
        } else if (this_type == Long.class) {
            fileName = "src/files/longTypes.txt";
        } else if (this_type == Double.class) {
            fileName = "src/files/floatTypes.txt";
        } else if (this_type == String.class) {
            fileName = "src/files/stringTypes.txt";
        }

        // Если это первое добавление, очищаем файл
        if (isFirstWrite && need_delete) {
            clearFile(fileName);  // Очищаем файл
            isFirstWrite = false;  // После первого вызова не очищаем файл
        }

        // Запись новых данных в файл (пишем в конец, не очищая файл)
        try (FileWriter writer = new FileWriter(fileName, true)) {  // Флаг 'true' для добавления
            writer.write(data.toString() + "\n");
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex.getMessage());
        }
    }

    // Метод для очистки файла
    private void clearFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("");  // Очищаем файл
        } catch (IOException ex) {
            System.out.println("Ошибка очистки файла: " + fileName);
        }
    }

    public void resetFirstWriteFlag() {
        isFirstWrite = true;  // Сбрасываем флаг для последующей очистки
    }
}
