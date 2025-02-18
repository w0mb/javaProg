package lab1.factory;

import lab1.processor.*;

public class DataProcessorFactory {
    public static DataProcessorStrategy getProcessor(String data) {
        if (data.matches("^-?\\d+$"))
        {
            return new IntegerProcessor();
        }
        else if (data.matches("^-?\\d+[lL]?$"))
        {
            return new LongProcessor();
        }
        else if (data.matches("^-?\\d*\\.\\d+$"))
        {
            return new DoubleProcessor();
        }
        else
        {
            return new StringProcessor();
        }
    }
}