package ru.outs.aop.proxy;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.outs.aop.MyObject;
import ru.outs.aop.annotation.Log;

public class TestLogging implements TestLoggingInterface {

    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

    public void calculation(int param) {
        logger.info("выполняется метод calculation, с параметром {}", param);
    }

    @Log
    public String calculation(int param1, int param2) {
        logger.info("выполняется метод calculation, с параметрами: {}, {}", param1, param2);
        return "null";
    }

    public void calculation(int param1, int param2, String param3) {
        logger.info("выполняется метод calculation, с параметрами: {}, {}, {}", param1, param2, param3);
    }

    @Log
    public List<Integer> calculation(int param1, int param2, MyObject param3) {
        logger.info("выполняется метод calculation, с параметрами: {}, {}, {}", param1, param2, param3);
        return Collections.emptyList();
    }
}
