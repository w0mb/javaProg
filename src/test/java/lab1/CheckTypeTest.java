package lab1;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CheckTypeTest {

    @Test
    public void testCheckTypesFromFileInteger() {
        // Создаем тестовый файл с целыми числами
        String filename = "src/test/resources/test_integers.txt";

        // Записываем данные в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("123\n");
            writer.write("-456\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckType checkType = new CheckType();
        List<Object> dataList = checkType.checkTypesFromFile(filename);

        // Проверяем, что список содержит два целых числа
        assertEquals(2, dataList.size());
        assertTrue(dataList.get(0) instanceof Integer);
        assertTrue(dataList.get(1) instanceof Integer);
        assertEquals(123, dataList.get(0));
        assertEquals(-456, dataList.get(1));
    }

    @Test
    public void testCheckTypesFromFileLong() {
        // Создаем тестовый файл с большими числами
        String filename = "src/test/resources/test_longs.txt";

        // Записываем данные в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("12345678901234\n");
            writer.write("-98765432109876\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckType checkType = new CheckType();
        List<Object> dataList = checkType.checkTypesFromFile(filename);

        // Проверяем, что список содержит два длинных числа
        assertEquals(2, dataList.size());
        assertTrue(dataList.get(0) instanceof Long);
        assertTrue(dataList.get(1) instanceof Long);
        assertEquals(12345678901234L, dataList.get(0));
        assertEquals(-98765432109876L, dataList.get(1));
    }

    @Test
    public void testCheckTypesFromFileDouble() {
        // Создаем тестовый файл с вещественными числами
        String filename = "src/test/resources/test_doubles.txt";

        // Записываем данные в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("123.45\n");
            writer.write("-678.90\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckType checkType = new CheckType();
        List<Object> dataList = checkType.checkTypesFromFile(filename);

        // Проверяем, что список содержит два вещественных числа
        assertEquals(2, dataList.size());
        assertTrue(dataList.get(0) instanceof Double);
        assertTrue(dataList.get(1) instanceof Double);
        assertEquals(123.45, dataList.get(0));
        assertEquals(-678.90, dataList.get(1));
    }

    @Test
    public void testCheckTypesFromFileString() {
        // Создаем тестовый файл со строками
        String filename = "src/test/resources/test_strings.txt";

        // Записываем данные в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Hello\n");
            writer.write("World\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckType checkType = new CheckType();
        List<Object> dataList = checkType.checkTypesFromFile(filename);

        // Проверяем, что список содержит две строки
        assertEquals(2, dataList.size());
        assertTrue(dataList.get(0) instanceof String);
        assertTrue(dataList.get(1) instanceof String);
        assertEquals("Hello", dataList.get(0));
        assertEquals("World", dataList.get(1));
    }
}
