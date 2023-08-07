package A2;

import A1.STATE;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class IODevice {

    private final int time = 2;
    private static Queue<PCB> waitQueue;

    public IODevice() {
        waitQueue = new LinkedList<>();
    }

    public void execute() {
//        PCB pcb = waitQueue.peek();


//        for (CPU cpu : CPUScheduler.getCpus()) {
//            if (!cpu.isOccupied()) return;
        System.out.println(hasDuplicates(waitQueue));

            for (PCB pcb : waitQueue) {
                System.out.println(hasDuplicates(waitQueue));
//                if (cpu.getPCB() == pcb) {
                    if (pcb != null && pcb.getProcess().getIORequest()) {
                        System.out.println("IO @: " + pcb.getPid() + ", IOCount: " + pcb.getIoCounter());

                        if (pcb.getIoCounter() == time) {
                            pcb.setState(STATE.RUNNING);
                            pcb.setIoCounter(0);
                            waitQueue.remove();
//                            System.out.println(pcb.getPid()+ " TEST " + pcb.getIoCounter());
                            break;
                        } else if (pcb.getIoCounter() < time) {
//                            System.out.println(pcb.getPid()+ " BOP " + pcb.getIoCounter());
                            pcb.setIoCounter(pcb.getIoCounter() + 1);
                            break;
                        } else if (pcb.getIoCounter() > time) {
                            //
//                            System.out.println("TESTTTTT");
                            break;
                        }
                    }
//                }


            }
    }

    public static <T> boolean hasDuplicates(Queue<T> queue) {
        Set<T> set = new HashSet<>();
        for (T element : queue) {
            if (!set.add(element)) {
                waitQueue.remove(element);
                return true; // Found a duplicate
            }
        }
        return false; // No duplicates found
    }

    public Queue<PCB> getWaitQueue() {
        return waitQueue;
    }

    public int getTime() {
        return time;
    }
}
