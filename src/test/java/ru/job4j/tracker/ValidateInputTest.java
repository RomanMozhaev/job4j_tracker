package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for ValidateInput
 */
public class ValidateInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;
    List<Integer> range = new ArrayList<>();

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
        for (int i = 0; i < 7; i++) {
            range.add(i);
        }
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    /**
     * Test for throwable case "NumberFormatException"
     */
    @Test
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(new StubInput(new String[] {"invalid", "1"}));
        input.ask("Enter", range);
        assertThat(
                this.mem.toString(),
                is(
                        String.format("Wrong enter. Please try again.%n")
                )
        );
    }
    /**
     * Test for throwable case "MenuOutException"
     */
    @Test
    public void whenOutOfRangeInput() {
        ValidateInput input = new ValidateInput(new StubInput(new String[] {"7", "1"}));
        input.ask("Enter", range);
        assertThat(
                this.mem.toString(),
                is(
                        String.format("Please select the valid menu option.%n")
                )
        );
    }
}

