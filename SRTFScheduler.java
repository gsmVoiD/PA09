/*
 * NAME: Raghav Pant
 * PID:  A16671820
 */

import java.util.*;

/**
 *
 */
public class SRTFScheduler {

    /**
     *
     */
    private class Task implements Comparable {

        private String name;
        private int length, remainingTime;

        public Task(String name, int length) {
            if (name == null || length < 1) throw new IllegalArgumentException();
            this.name = name;
            this.length = length;
            this.remainingTime = length;
        }

        public boolean run() {
            if (this.remainingTime <= 0) return false;
            this.remainingTime--;
            return true;
        }

        @Override
        public String toString() {
            /* Returns a string representation of the task. */
            return "Task(" +  this.name + ", " +
                    this.length + ", " + (this.remainingTime - 1) + ")";
        }

        @Override
        public int compareTo(Object o) {
            /* Overrides the original compareTo function with some additions
            * for comparing remaining time and the name. */
            Task tObject = (Task) o;
            if (this.remainingTime < tObject.remainingTime) {
                return -1;
            }
            else if (this.remainingTime > tObject.remainingTime) {
                return 1;
            }
            else {
                if (this.name.compareTo(tObject.name) < 0) {
                    return -1;
                }
                else if (this.name.compareTo(tObject.name) > 0) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }
    }

    /* instance variables */
    private PriorityQueue<Task> waitlist;
    private int time;
    private List<String> log;

    public SRTFScheduler() {
        /* Constructor for a SRTF Scheduler */
        waitlist = new PriorityQueue<>();
        time = 0;
        log = new ArrayList<>();


    }

    public void addTask(String name, int length) {
        /* Adds a task to the scheduler. */
        Task toAdd = new Task(name, length);
        this.waitlist.add(toAdd);
    }

    public boolean run() {
        /* Runs an item from the scheduler. */
        time++;
        Task item = waitlist.peek();
        if (this.waitlist.isEmpty()) {
            log.add(time + " <IDLE>");
            return false;
        }
        else {
            if (item.remainingTime > 1) {
                log.add(time + " " + item.toString());
                Task a = waitlist.remove();
                waitlist.add(item);
                return a.run();
            }
            else {
                log.add(time + " " + item.toString());
                Task a = waitlist.remove();
                return a.run();
            }
        }
    }

    public List<String> getLog() {
        /* Returns the log */
        return log;
    }
}
