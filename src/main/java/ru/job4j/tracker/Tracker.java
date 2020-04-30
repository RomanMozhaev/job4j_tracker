package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tracker collects tickets.
 *
 * @author RomanM
 * @version 1.1 May 9, 2019
 */
public class Tracker implements ITracker {

    private final List<Item> items = new ArrayList<>();

    /**
     * The method adds a new ticket
     *
     * @param item - contains parameters of the ticket
     * @return - return the ticket
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * The method generates a unique ID for tickets
     *
     * @return - the unique ID
     */
    private String generateId() {
        long time = System.currentTimeMillis();
        long random = (long) (Math.random() * 100000);
        return Long.toString(time + random);
    }

    /**
     * the method replaces the parameters of one ticket with another
     *
     * @param id   - the unique ID of the ticket for replacement
     * @param item - a new parameters
     * @return true - the operations completed successful
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        int index = 0;
        for (Item elmnt : this.items) {
            if (elmnt.getId().equals(id)) {
                item.setId(id);
                this.items.set(index, item);
                result = true;
                break;
            }
            index++;
        }
        return result;
    }

    /**
     * the method deletes the ticket with the forwarded ID
     *
     * @param id - forwarded ID
     * @return true if the operation is completed successful
     */
    public boolean delete(String id) {
        boolean result = false;
        int index = 0;
        for (Item elmnt : items) {
            if (elmnt.getId().equals(id)) {
                this.items.remove(index);
                result = true;
                break;
            }
            index++;
        }
        return result;
    }

    /**
     * the method returns the array with all tickets
     *
     * @return - the array with all tickets
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * the method searches the tickets with the forwarded name.
     *
     * @param key - forwarded key-word
     * @return - the array with founded tickets
     */
    public List<Item> findByName(String key) {
        List<Item> oneName = new ArrayList<>();
        for (Item elmnt : this.items) {
            if (elmnt.getName().equals(key)) {
                oneName.add(elmnt);
            }
        }
        return oneName;
    }

    /**
     * the method searches the ticket with forwarded ID
     *
     * @param id - forwarded ID
     * @return - the founded ticket
     */
    public Item findById(String id) {
        Item itemReturn = null;
        for (Item elmnt : this.items) {
            if (elmnt.getId().equals(id)) {
                itemReturn = elmnt;
                break;
            }
        }
        return itemReturn;
    }

    @Override
    public void close() throws Exception {

    }
}
