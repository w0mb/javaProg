package lab1.factory;

import lab1.processor.*;

public class DataProcessorFactory {
    public static DataProcessorStrategy getProcessor(String data) {
    if (data.matches("^-?\\d+$")) {
        try {
            Integer.parseInt(data);
            return new IntegerProcessor();
        } catch (NumberFormatException e1) {

                Long.parseLong(data);
                return new LongProcessor();
        }
    }
    else if (data.matches("^-?\\d*\\.\\d+$")) {
        return new DoubleProcessor();
    }
    else {
        return new StringProcessor();
    }
}

    }
