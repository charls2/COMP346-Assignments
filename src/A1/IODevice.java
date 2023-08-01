package A1;

import java.util.LinkedList;
import java.util.Queue;

public class IODevice {

    private Queue<PCB> waitQueue;

    private final int timePerIO = 5;

    public IODevice() {
        waitQueue = new LinkedList<>();
    }

    public void execute(PCB waitPCB) {

        for (int i = 0 ; i < timePerIO ; i++) {
            System.out.println("\nTIME UNIT: " + CPU.getTimeUnit() + " *******************");

            System.out.println("\nA1.PCB #" + waitPCB.getPid());
            System.out.println("PROCESS ID: " + waitPCB.getPid() + ", IO Request at Instruction: " + waitPCB.getPcounter());

            CPU.setTimeUnit(CPU.getTimeUnit() + 1); // Five time units to be fulfilled
            System.out.println("IO REQUEST... " + waitPCB.getPcounter());
            System.out.println("A1.Process State: " + waitPCB.getState());

            CPU.print();
        }
        System.out.println("\nIO REQUEST FULFILLED @ INSTRUCTION #: " + waitPCB.getPcounter());
        waitPCB.setState(STATE.READY);
    }

    public Queue<PCB> getWaitQueue() {
        return waitQueue;
    }
}
