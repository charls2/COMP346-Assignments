package A2;

import A1.STATE;

import java.util.*;

public class FCFS {

    private int time;
    private Queue<PCB> readyQ;
    private final ArrayList<CPU> cpus;
    private List<PCB> pcbList;

    public FCFS() {
        System.out.println("\n********** FCFS **********");

        time = CPUScheduler.getTime();
        readyQ = CPUScheduler.getReadyQueue();
        pcbList = CPUScheduler.getPCBList();
        cpus = CPUScheduler.getCpus();
    }

    public void begin() {

        do {
            if (!pcbList.isEmpty()) sortPCBList(pcbList);

            System.out.println("\n**** SYSTEM TIME: " + time + " ****");

            for (CPU cpu : CPUScheduler.getCpus()) {

                // assign a process to an available CPU Core
                if (!cpu.isOccupied()) {

                    if (readyQ.isEmpty()) {
                        for (PCB pcb : pcbList) {
                            if (pcb.getState().equals(STATE.RUNNING)
                                    || pcb.getState().equals(STATE.TERMINATED)
                                    || pcb.getState().equals(STATE.WAITING)) {
                                continue;
                            } else {

                                if (pcb.getProcess().getArrivalTime() == time) {
                                    readyQ.add(pcb);
                                }
                            }
                        }
                    }

                    PCB currentProcess = null;
                    if (!readyQ.isEmpty())
                        currentProcess = readyQ.poll();

                    cpu.setPCB(currentProcess);
                    execute(cpu);

                    CPUScheduler.clearCPU(cpu); // Clear CPUs if process complete

                } else {
                    // if cpu is in use
                    execute(cpu);
                    CPUScheduler.clearCPU(cpu); // Clear CPUs if process complete
                }
            }
            CPUScheduler.displayReadyQueue();
            CPUScheduler.displayWaitQueue();
            CPUScheduler.displayCPUs();

            tick();
            if (time == 20) return;
        } while (!readyQ.isEmpty() || !pcbList.isEmpty());
        System.out.print("\n****** SYSTEM TERMINATING (FCFS ALGORITHM) ******");
    }

    public void execute(CPU cpu) {
//        int arrivalTime = cpu.getPCB().getProcess().getArrivalTime();
//        int burstTime = cpu.getPCB().getProcess().getBurstTime();

        if (cpu.getPCB() == null)
            return;

        // Check if IO at system time
        cpu.getPCB().getProcess().checkIO(time);
        if (cpu.getPCB().getState().equals(STATE.WAITING)) {
//            if (!CPUScheduler.io.getWaitQueue().isEmpty() && !CPUScheduler.io.getWaitQueue().element().equals(cpu.getPCB())) {
                CPUScheduler.io.getWaitQueue().add(cpu.getPCB());
//            }
            CPUScheduler.io.execute();
            return;
        }

        cpu.getPCB().setState(STATE.RUNNING);
        cpu.setOccupied(true);

        // Check if reached burst
        if (cpu.getPCB().getPcounter() == cpu.getPCB().getProcess().getBurstTime()) {
            cpu.getPCB().getProcess().setComplete(true);
        }

        if (cpu.getPCB().getProcess().isCompleted()) {
            System.out.println("CPU #: " + cpu.getKey() + " [COMPLETED: "
                    + cpu.getPCB().getPid() + "]");
            cpu.getPCB().setState(STATE.TERMINATED);

            pcbList.remove(cpu.getPCB());
            cpu.setOccupied(false);
            cpu.setPCB(null);
            return;
        }

        cpu.getPCB().setPcounter(cpu.getPCB().getPcounter() + 1);
    }

    public void tick() {
        time++;
    }

    public void sortPCBList(List<PCB> pcbList) {
        pcbList.sort(Comparator.comparingInt(o -> o.getProcess().getArrivalTime()));

    }

    public ArrayList<CPU> getCpus() {
        return cpus;
    }
//    private void firstArrived(List<PCB> pcbList, PCB pcbFromList) {
//        pcbList.remove(pcbFromList);
//        while (!pcbList.isEmpty() && pcbList.get(0).getProcess().getArrivalTime() <= time) {
//            PCB arrivedProcess = pcbList.remove(0);
//            readyQ.add(arrivedProcess);
//        }
////        pcbList.add(pcbFromList);
//    }
}
