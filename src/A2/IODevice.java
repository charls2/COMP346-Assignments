package A2;

import java.util.*;

public class IODevice {

    private final int time = 1;
    private static Queue<PCB> waitQueue;


    public IODevice() {
        waitQueue = new LinkedList<>();
    }

    public Queue<PCB> getWaitQueue() {
        return waitQueue;
    }

    public int getTime() {
        return time;
    }
}
