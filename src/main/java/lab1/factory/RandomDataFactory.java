package lab1.factory;

import java.util.Random;

public class RandomDataFactory {
    private static final Random RANDOM = new Random();

    public static Object getRandomObject() {
        switch (RANDOM.nextInt(4)) {
            case 0: return RANDOM.nextInt();
            case 1: return RANDOM.nextDouble();
            case 2: return RANDOM.nextLong();
            case 3: return getRandomString();
            default: return null;
        }
    }

    private static String getRandomString() {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        int size = RANDOM.nextInt(10) + 1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(symbols.charAt(RANDOM.nextInt(symbols.length())));
        }
        return result.toString();
    }
}
