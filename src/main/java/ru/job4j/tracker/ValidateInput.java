package ru.job4j.tracker;

import java.util.List;

public class ValidateInput implements Input {

    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }
    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }
    @Override
    public int ask(String string, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(string, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Please select the valid menu option.");
            } catch (NumberFormatException nfe) {
                System.out.println("Wrong enter. Please try again.");
            }
        } while (invalid);
        return value;
    }
}
