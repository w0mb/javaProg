package lab1;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DistributionTest {

    private static final String INT_FILE = "src/files/integerTypes.txt";
    private static final String LONG_FILE = "src/files/longTypes.txt";
    private static final String DOUBLE_FILE = "src/files/floatTypes.txt";
    private static final String STRING_FILE = "src/files/stringTypes.txt";

    @BeforeEach
    public void setUp() {
        // Очистка файлов перед каждым тестом
        clearFile(INT_FILE);
        clearFile(LONG_FILE);
        clearFile(DOUBLE_FILE);
        clearFile(STRING_FILE);
    }

    @Test
    public void testIntegerDistribution() {
        Distribution<Integer> distribution = new Distribution<>(Integer.class);
        distribution.makeDistribution(123);

        // Проверяем, что файл не пуст
        assertTrue(isFileNotEmpty(INT_FILE));
    }

    @Test
    public void testLongDistribution() {
        Distribution<Long> distribution = new Distribution<>(Long.class);
        distribution.makeDistribution(123L);

        assertTrue(isFileNotEmpty(LONG_FILE));
    }

    @Test
    public void testDoubleDistribution() {
        Distribution<Double> distribution = new Distribution<>(Double.class);
        distribution.makeDistribution(123.456);

        assertTrue(isFileNotEmpty(DOUBLE_FILE));
    }

    @Test
    public void testStringDistribution() {
        Distribution<String> distribution = new Distribution<>(String.class);
        distribution.makeDistribution("TestString");

        assertTrue(isFileNotEmpty(STRING_FILE));
    }

    @Test
    public void testFileClearingBeforeWriting() {
        // Создаем экземпляр класса Distribution и сбрасываем флаг очистки перед каждым использованием
        Distribution<Integer> distribution = new Distribution<>(Integer.class, true);

        // Очистка файла перед первой записью


        // Запишем что-то в файл
        distribution.makeDistribution(123);
        distribution.resetFirstWriteFlag();
        // Запишем что-то новое, файл должен быть очищен перед этим
        distribution.makeDistribution(456);

        // Проверим, что файл содержит только одно значение
        try {
            String content = new String(Files.readAllBytes(Paths.get(INT_FILE)));
            assertEquals("456\n", content);
        } catch (IOException e) {
            fail("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private void clearFile(String fileName) {
        try {
            Files.write(Paths.get(fileName), "".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isFileNotEmpty(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.length() > 0;
    }
}
