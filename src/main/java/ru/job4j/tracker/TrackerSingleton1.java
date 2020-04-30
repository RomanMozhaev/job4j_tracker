package ru.job4j.tracker;

import java.util.Arrays;

/**
 * The Tracker collects tickets.
 * The singleton version. Static field. Lazy loading.
 *
 * @author RomanM
 * @version 1.0 May 23, 2019
 */
public class TrackerSingleton1 {

    private final Item[] items = new Item[100];
    private int position = 0;
    private static TrackerSingleton1 instance;

    private TrackerSingleton1() {

    }

    public static TrackerSingleton1 getInstance() {
        if (instance == null) {
            instance = new TrackerSingleton1();
        }
        return instance;
    }

    /**
     * The method adds a new ticket
     *
     * @param item - contains parameters of the ticket
     * @return - return the ticket
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items[this.position++] = item;
        return item;
    }

    /**
     * The method generetes a unique ID for tickets
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
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getId().equals(id)) {
                this.items[i] = item;
                this.items[i].setId(id);
                result = true;
                break;
            }
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
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getId().equals(id)) {
                System.arraycopy(this.items, (i + 1), this.items, i, (this.items.length - 1 - i));
                this.items[this.items.length - 1] = null;
                this.position--;
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * the method returns the array with all tickets
     *
     * @return - the array with all tickets
     */
    public Item[] findAll() {

        return Arrays.copyOf(this.items, position);
    }

    /**
     * the method searches the tickets with the forwarded name.
     *
     * @param key - forwarded key-word
     * @return - the array with founded tickets
     */
    public Item[] findByName(String key) {
        Item[] itemsKey = new Item[this.position];
        int index = 0;
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getName().equals(key)) {
                itemsKey[index++] = this.items[i];
            }
        }
        return Arrays.copyOf(itemsKey, index);
    }

    /**
     * the method searches the ticket with forwarded ID
     *
     * @param id - forwarded ID
     * @return - the founded ticket
     */
    public Item findById(String id) {
        Item itemReturn = null;
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getId().equals(id)) {
                itemReturn = this.items[i];
                break;
            }
        }
        return itemReturn;
    }

}
