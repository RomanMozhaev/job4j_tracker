package ru.job4j.tracker;

/**
 * the interface for the possible actions of the Tracker.
 */
public interface UserAction {
    /**
     * the method returns the key of the action
     *
     * @return - the key
     */
    String key();

    /**
     * the main method for action executing
     *
     * @param input   - user answers
     * @param tracker - tickets' tracker
     */
    void execute(Input input, ITracker tracker);

    /**
     * the method returns th sort description of the action.
     *
     * @return - the string with description
     */
    String info();
}
