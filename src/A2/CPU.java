package A2;

import java.util.LinkedList;
import java.util.Queue;

public class CPU {

    private int key;
    private PCB inUse;
    private boolean running;

    public CPU(int key) {
        this.key = key;
        this.running = false;
    }

    public int getKey() {
        return key;
    }

    public PCB getPCB() {
        return inUse;
    }
    public void setPCB(PCB pcb) {
        this.inUse = pcb;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
