package ru.job4j.tracker;

/**
 * the abstract class for all actions
 *
 * @author RomanM
 * @version 1.0 May 20, 2019
 */
public abstract class BaseAction implements UserAction {
    /**
     * field with key-number and description of action
     */
    private final String key;
    private final String info;

    /**
     * the constructor assigns the value for
     *
     * @param info - description of the action
     * @param key  - key number
     */
    protected BaseAction(String info, String key) {
        this.info = info;
        this.key = key;
    }

    /**
     * the method returns a key-number
     *
     * @return
     */
    @Override
    public String key() {
        return this.key;
    }

    /**
     * the method returns a description
     *
     * @return
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.info);
    }
}
