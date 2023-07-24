import java.util.LinkedList;
import java.util.Queue;

public class IODevice {

    private Queue<PCB> waitQueue;

    private final int timePerIO = 5;

    public IODevice() {
        waitQueue = new LinkedList<>();
    }

    public void execute(PCB waitPCB) {

        waitPCB.setState(STATE.RUNNING);
        for (int i = 0 ; i < timePerIO ; i++) {
            System.out.println("\nTIME UNIT: " + CPU.getTimeUnit());

            CPU.setTimeUnit(CPU.getTimeUnit() + 1); // Five time units to be fulfilled
            System.out.println("IO REQUEST... " + waitPCB.getPcounter());

        }
        System.out.println("IO REQUEST FULFILLED INSTRUCT: " + waitPCB.getPcounter());
        CPU.readyQueue.add(waitPCB);
        waitPCB.setState(STATE.READY);
    }

    public Queue<PCB> getWaitQueue() {
        return waitQueue;
    }
}
