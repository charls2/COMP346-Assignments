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

            System.out.println("PROCESS ID: " + waitPCB.getPid() + ", IO Request at Instruction: " + waitPCB.getPcounter());

            CPU.setTimeUnit(CPU.getTimeUnit() + 1); // Five time units to be fulfilled
            System.out.println("IO REQUEST... " + waitPCB.getPcounter());

            System.out.print("READY QUEUE: [");
            for (PCB p : CPU.readyQueue) {
                System.out.print("PID: " + p.getPid() + " ");
            }
            System.out.println("]");
            System.out.print("WAIT QUEUE IO1: [");
            for (PCB p : CPU.IO1.getWaitQueue()) {
                System.out.print("PID: " + p.getPid() + " ");
            }
            System.out.println("]");
            System.out.print("WAIT QUEUE IO2: [");
            for (PCB p : CPU.IO2.getWaitQueue()) {
                System.out.print("PID: " + p.getPid() + " ");
            }
            System.out.println("]");
        }
        System.out.println("\nIO REQUEST FULFILLED @ INSTRUCTION #: " + waitPCB.getPcounter());
        waitPCB.setState(STATE.READY);
    }

    public Queue<PCB> getWaitQueue() {
        return waitQueue;
    }
}
