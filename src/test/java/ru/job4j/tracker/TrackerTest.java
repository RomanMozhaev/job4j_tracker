package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * the tests for Tracker's methods.
 * @author RomanM
 * @version 1.1 May 9, 2019
 */
public class TrackerTest {
    /**
     * Test for Add.
     * It adds one ticket, finds the ticket by ID and compares the names.
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        long created = System.currentTimeMillis();
        Item item = new Item("test1", "testDescription", created);
        tracker.add(item);
        Item result = tracker.findById(item.getId());
        assertThat(result.getName(), is(item.getName()));
    }

    /**
     * test for Replace
     * it replace one ticket to another and checks the name.
     */
    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2", 1234L);
        tracker.replace(previous.getId(), next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    /**
     * test for Delete
     * it creates three tickets, removes the second ticket and checks
     * that the current second ticket has the name of the previous third ticket.
     */
    @Test
    public void whenDeleteSecondThenSecondIsThird() {
        Tracker tracker = new Tracker();
        long created = System.currentTimeMillis();
        Item first = new Item("test1", "testDescription", created);
        tracker.add(first);
        Item second = new Item("test2", "testDescription2", created + 1);
        tracker.add(second);
        Item third = new Item("test3", "testDescription3", created + 2);
        tracker.add(third);
        String id = second.getId();
        tracker.delete(id);
        List<Item> result = tracker.findAll();
        assertThat(result.get(1).getName(), is("test3"));
    }

    /**
     * test for FindAll
     * it adds three tickets and check the aggregated name of all added tickets
     */
    @Test
    public void whenFindAllThenAllNames() {
        Tracker tracker = new Tracker();
        long created = System.currentTimeMillis();
        Item first = new Item("test1", "testDescription", created);
        tracker.add(first);
        Item second = new Item("test2", "testDescription2", created + 1);
        tracker.add(second);
        Item third = new Item("test3", "testDescription3", created + 2);
        tracker.add(third);
        List<Item> result = tracker.findAll();
        List<Item> expect = new ArrayList<>();
        expect.add(first);
        expect.add(second);
        expect.add(third);
        assertThat(expect, is(result));
    }

    /**
     * test for FindByName
     * it adds five tickets and checks that the method returns the array where all tickets have same name.
     */
    @Test
    public void whenFindByNameThenAllTest2() {
        Tracker tracker = new Tracker();
        long created = System.currentTimeMillis();
        Item first = new Item("test1", "testDescription", created);
        tracker.add(first);
        Item second = new Item("test2", "testDescription2", created + 1);
        tracker.add(second);
        Item third = new Item("test3", "testDescription3", created + 2);
        tracker.add(third);
        Item fourth = new Item("test2", "testDescription4", created + 3);
        tracker.add(fourth);
        Item fifth = new Item("test2", "testDescription5", created + 4);
        tracker.add(fifth);
        List<Item> result = tracker.findByName("test2");
        List<Item> expect = new ArrayList<>();
        expect.add(second);
        expect.add(fourth);
        expect.add(fifth);

        assertThat(result, is(expect));
    }

}