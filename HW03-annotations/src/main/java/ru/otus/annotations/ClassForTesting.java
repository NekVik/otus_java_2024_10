package ru.otus.annotations;

import org.assertj.core.api.Assertions;
import ru.otus.annotations.framework.annotations.After;
import ru.otus.annotations.framework.annotations.Before;
import ru.otus.annotations.framework.annotations.Test;

@SuppressWarnings({"java:S1186", "java:S125", "java:S5960"})
public class ClassForTesting {

    public ClassForTesting() {}

    @Before
    public void beforeTest() {}

    @Before
    public void beforeTest2() {
        // throw new RuntimeException();
    }

    @Test
    public void test1() {
        var expected = 1;
        Assertions.assertThat(1).isEqualTo(expected);
    }

    @Test
    public void test2() {
        var expected = 10;
        Assertions.assertThat(10).isEqualTo(expected);
    }

    @Test
    public void test3Error() {
        var expected = 1;
        Assertions.assertThat(3).isEqualTo(expected);
    }

    @Test
    private void test4() {
        var expected = 3;
        Assertions.assertThat(3).isEqualTo(expected);
    }

    @Test
    public void test5Error() {
        int i = 1 / 0;
        Assertions.assertThat(i).isEqualTo(0);
    }

    @After
    public void afterTest() {}

    @After
    public void afterTest2() {
        // throw new RuntimeException();
    }
}
