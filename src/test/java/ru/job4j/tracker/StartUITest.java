package ru.job4j.tracker;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for StartUI
 */
public class StartUITest {
    /**
     * Common fields for two new test with Before/ After
     */
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final Consumer<String> output = new Consumer<>() {
        private final PrintStream stdout = new PrintStream(out);

        @Override
        public void accept(String s) {
            stdout.printf(s);
        }
    };

    Tracker tracker = new Tracker();
    DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
    Item item1 = this.tracker.add(new Item("test first", "description of first", 123L));
    Item item2 = this.tracker.add(new Item("test second", "description of second", 124L));
    Item item3 = this.tracker.add(new Item("test third", "description of third", 125L));
    Item item4 = this.tracker.add(new Item("test second", "description of second number two", 126L));
    String menu = new StringBuilder()
            .append("Menu:")
            .append(System.lineSeparator())
            .append("0. Add New Ticket")
            .append(System.lineSeparator())
            .append("1. Edit The Ticket")
            .append(System.lineSeparator())
            .append("2. Delete The Ticket")
            .append(System.lineSeparator())
            .append("3. Review All Tickets")
            .append(System.lineSeparator())
            .append("4. Find  Tickets by Name")
            .append(System.lineSeparator())
            .append("5. Find A Ticket by ID")
            .append(System.lineSeparator())
            .append("6. Exit")
            .append(System.lineSeparator())
            .toString();

    /**
     * the method implements before tests
     */
    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    /**
     * the method implements after tests
     */
    @After
    public void backOutput() {
        this.tracker = null;
    }

    /**
     * the test of console output for review command
     */
    @Test
    public void whenReviewAllN2() {
        String[] answers = {"3", "n", "6", "y"};
        Input input = new StubInput(answers);
        new StartUI(input, this.tracker, this.output).init();
        String expect = new StringBuilder()
                .append(menu)
                .append("------------ All tickets review --------------")
                .append(System.lineSeparator())
                .append("Order number: 1|")
                .append(System.lineSeparator())
                .append("Ticket's ID: " + this.item1.getId())
                .append(System.lineSeparator())
                .append("Ticket's name: test first")
                .append(System.lineSeparator())
                .append("Ticket's description: description of first")
                .append(System.lineSeparator())
                .append("Ticket's date creation: " + this.simple.format(123L))
                .append(System.lineSeparator())
                .append("----------------------")
                .append(System.lineSeparator())
                .append("Order number: 2|")
                .append(System.lineSeparator())
                .append("Ticket's ID: " + this.item2.getId())
                .append(System.lineSeparator())
                .append("Ticket's name: test second")
                .append(System.lineSeparator())
                .append("Ticket's description: description of second")
                .append(System.lineSeparator())
                .append("Ticket's date creation: " + this.simple.format(124L))
                .append(System.lineSeparator())
                .append("----------------------")
                .append(System.lineSeparator())
                .append("Order number: 3|")
                .append(System.lineSeparator())
                .append("Ticket's ID: " + this.item3.getId())
                .append(System.lineSeparator())
                .append("Ticket's name: test third")
                .append(System.lineSeparator())
                .append("Ticket's description: description of third")
                .append(System.lineSeparator())
                .append("Ticket's date creation: " + this.simple.format(125L))
                .append(System.lineSeparator())
                .append("----------------------")
                .append(System.lineSeparator())
                .append("Order number: 4|")
                .append(System.lineSeparator())
                .append("Ticket's ID: " + this.item4.getId())
                .append(System.lineSeparator())
                .append("Ticket's name: test second")
                .append(System.lineSeparator())
                .append("Ticket's description: description of second number two")
                .append(System.lineSeparator())
                .append("Ticket's date creation: " + this.simple.format(126L))
                .append(System.lineSeparator())
                .append("----------------------")
                .append(System.lineSeparator())
                .append(menu)
                .toString();
        assertThat(this.out.toString(), is(expect));
    }

    /**
     * the test for console output of FindByName command
     */
    @Test
    public void whenFindByNameTestThird() {
        String[] answers = {"4", "test third", "y"};
        Input input = new StubInput(answers);
        new StartUI(input, this.tracker, this.output).init();
        String expect = new StringBuilder()
                .append(menu)
                .append("------------ Finding all tickets with a specific name --------------")
                .append(System.lineSeparator())
                .append("------------ The tickets with the specific name review --------------")
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Ticket's ID: " + this.item3.getId())
                .append(System.lineSeparator())
                .append("Ticket's name: test third")
                .append(System.lineSeparator())
                .append("Ticket's description: description of third")
                .append(System.lineSeparator())
                .append("Ticket's date creation: " + this.simple.format(125L))
                .append(System.lineSeparator())
                .append("----------------------")
                .append(System.lineSeparator())
                .toString();
        assertThat(this.out.toString(), is(expect));
    }

    /**
     * Test for add method
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "y"});
        new StartUI(input, tracker, this.output).init();
        assertThat(tracker.findAll().iterator().next().getName(), is("test name"));
    }

    /**
     * Test for Replace method
     */
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc", 123L));
        Input input = new StubInput(new String[]{"1", item.getId(), "test replace", "заменили заявку", "y"});
        new StartUI(input, tracker, this.output).init();
        assertThat(tracker.findById(item.getId()).getName(), is("test replace"));
    }

    /**
     * Test for Delete method
     */
    @Test
    public void whenDeleteThenTrackerDoesNotHaveFirst() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test first", "description of first", 123L));
        Item item2 = tracker.add(new Item("test second", "description of second", 124L));
        Input input = new StubInput(new String[]{"2", item1.getId(), "y"});
        new StartUI(input, tracker, this.output).init();
        assertThat(tracker.findAll().get(0).getName(), is("test second"));
    }

    /**
     * Test for findAll method
     */
    @Test
    public void whenReviewThenTrackerLengthIs2() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test first", "description of first", 123L));
        Item item2 = tracker.add(new Item("test second", "description of second", 124L));
        Input input = new StubInput(new String[]{"3", "y"});
        new StartUI(input, tracker, this.output).init();
        assertThat(tracker.findAll().size(), is(2));
    }

    /**
     * Test for find by name method
     */
    @Test
    public void whenFindByNameThenTestSecondTwice() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test first", "description of first", 123L));
        Item item2 = tracker.add(new Item("test second", "description of second", 124L));
        Item item3 = tracker.add(new Item("test third", "description of third", 125L));
        Item item4 = tracker.add(new Item("test second", "description of second number two", 126L));
        List<Item> expect = new ArrayList<>();
        expect.add(item2);
        expect.add(item4);
        Input input = new StubInput(new String[]{"4", "test second", "y"});
        new StartUI(input, tracker, this.output).init();
        assertThat(tracker.findByName("test second"), is(expect));
    }

    /**
     * Test for find by id method
     */
    @Test
    public void whenFindByIdThenTestSecondTwice() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test first", "description of first", 123L));
        Item item2 = tracker.add(new Item("test second", "description of second", 124L));
        Item item3 = tracker.add(new Item("test third", "description of third", 125L));
        Item item4 = tracker.add(new Item("test second", "description of second number two", 126L));
        Input input = new StubInput(new String[]{"5", item3.getId(), "y"});
        new StartUI(input, tracker, this.output).init();
        assertThat(tracker.findById(item3.getId()).getName(), is("test third"));
    }
}
