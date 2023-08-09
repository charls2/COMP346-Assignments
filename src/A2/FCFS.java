package A2;

import A1.STATE;

import java.util.*;

public class FCFS extends CPUScheduler {

    private int time;
    private Queue<PCB> readyQ;
    private final ArrayList<CPU> cpus;
    private List<PCB> pcbList;

    private MathCPU mathCPU;

    public FCFS() {
        System.out.println("\n********** FCFS **********");

        time = CPUScheduler.getTime();
        readyQ = CPUScheduler.getReadyQueue();
        pcbList = CPUScheduler.getPCBList();
        cpus = CPUScheduler.getCpus();

        mathCPU = CPUScheduler.getMathCPU();
    }

    public void begin() {

        do {
            if (!pcbList.isEmpty()) sortPCBList(pcbList);

            System.out.println("\n**** SYSTEM TIME: " + time + " ****");

            for (CPU cpu : getCpus()) {

                // assign a process to an available CPU Core
                if (!cpu.isOccupied()) {

                    if (getReadyQueue().isEmpty()) {
                        for (PCB pcb : getPCBList()) {
                            if (pcb.getState().equals(STATE.RUNNING)
                                    || pcb.getState().equals(STATE.TERMINATED)
                                    || pcb.getState().equals(STATE.WAITING)) {
                                continue;
                            } else {

                                if (pcb.getProcess().getArrivalTime() == time) {
                                    getReadyQueue().add(pcb);
                                    pcb.setState(STATE.READY);
                                }
                            }
                        }
                    }

                    PCB currentProcess = null;
                    if (!getReadyQueue().isEmpty())
                        currentProcess = getReadyQueue().poll();

                    cpu.setPCB(currentProcess);
                    execute(cpu);

                    clearCPU(cpu); // Clear CPUs if process complete

                } else {
                    // if cpu is in occupied
                    execute(cpu);
                    clearCPU(cpu); // Clear CPUs if process complete
                }
            }
            displayReadyQueue();
            displayWaitQueue();
            displayCPUs();

            // Math Stuff

            tick();
            if (time == 30) return;
        } while (!getReadyQueue().isEmpty() || !getPCBList().isEmpty());

        // Math stuff
//        mathCPU.display();

        System.out.print("\n****** SYSTEM TERMINATING (FCFS ALGORITHM) ******");
    }

    public void execute(CPU cpu) {

        if (cpu.getPCB() == null)
            return;


        // Check if IO at system time
        if (cpu.getPCB().getProcess().getIORequest()) {
            cpu.getPCB().getProcess().checkIO2(time, cpu);
        }
        // Check if IO at system time
        if (cpu.getPCB().getState().equals(STATE.WAITING)) {
            CPUScheduler.handleIO(cpu);
            return;
        }

        // Check if reached burst
        if (cpu.getPCB().getPcounter() == cpu.getPCB().getProcess().getBurstTime()) {
            cpu.getPCB().getProcess().setComplete(true);
        }

        if (cpu.getPCB().getProcess().isCompleted()) {
            System.out.println("CPU #: " + cpu.getKey() + " [COMPLETED: "
                    + cpu.getPCB().getPid() + "]");
            cpu.getPCB().setState(STATE.TERMINATED);
            getPCBList().remove(cpu.getPCB());
            cpu.setOccupied(false);
            cpu.setPCB(null);
            return;
        }


        cpu.getPCB().setState(STATE.RUNNING);
        cpu.setOccupied(true);
        cpu.getPCB().setPcounter(cpu.getPCB().getPcounter() + 1);

        // Still running +1 program counter
    }


//    public static <T> boolean hasDuplicates(Queue<T> queue) {
//        Set<T> set = new HashSet<>();
//        for (T element : queue) {
//            if (!set.add(element)) {
//                CPUScheduler.io.getWaitQueue().remove(element);
//                return true; // Found a duplicate
//            }
//        }
//        return false; // No duplicates found
//    }

    public void tick() {
        time++;
    }

    public void sortPCBList(List<PCB> pcbList) {
        pcbList.sort(Comparator.comparingInt(o -> o.getProcess().getArrivalTime()));

    }

//    public ArrayList<CPU> getCpus() {
//        return cpus;
//    }
//
//    public MathCPU getMathCPU() {
//        return mathCPU;
//    }
}
