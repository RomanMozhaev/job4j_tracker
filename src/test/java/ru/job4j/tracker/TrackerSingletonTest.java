package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * the tests for Tracker's methods.
 *
 * @author RomanM
 * @version 1.1 May 9, 2019
 */
public class TrackerSingletonTest {

    @Test
    public void whenTrackerIsSingletonStaticField() {
        TrackerSingleton1 trackerFirst = TrackerSingleton1.getInstance();
        TrackerSingleton1 trackerSecond = TrackerSingleton1.getInstance();
        assertTrue(trackerFirst == trackerSecond);
    }

    @Test
    public void whenTrackerIsSingletonStaticFinalField() {
        TrackerSingleton2 trackerFirst = TrackerSingleton2.getInstance();
        TrackerSingleton2 trackerSecond = TrackerSingleton2.getInstance();
        assertTrue(trackerFirst == trackerSecond);
    }

    @Test
    public void whenTrackerIsSingletonStaticFinalClass() {
        TrackerSingleton3 trackerFirst = TrackerSingleton3.getInstance();
        TrackerSingleton3 trackerSecond = TrackerSingleton3.getInstance();
        assertTrue(trackerFirst == trackerSecond);
    }

    @Test
    public void whenTrackerIsSingletonEnum() {
        TrackerSingleton4 trackerFirst = TrackerSingleton4.INSTANCE;
        TrackerSingleton4 trackerSecond = TrackerSingleton4.INSTANCE;
        assertTrue(trackerFirst == trackerSecond);
    }


}