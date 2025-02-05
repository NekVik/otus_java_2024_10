package ru.outs.aop.proxy;

import java.util.List;
import ru.outs.aop.MyObject;

public interface TestLoggingInterface {

    void calculation(int param);

    String calculation(int param1, int param2);

    void calculation(int param1, int param2, String param3);

    List<Integer> calculation(int param1, int param2, MyObject param3);
}
