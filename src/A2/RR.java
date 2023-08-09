package A2;

import A1.STATE;

import javax.print.DocFlavor;
import java.util.*;

public class RR extends CPUScheduler {

    private int time;
    private Queue<PCB> readyQ;
//    private final ArrayList<CPU> cpus;
    private List<PCB> pcbList;

    public RR() {
        System.out.println("\n********** SJF **********");

        time = CPUScheduler.getTime();
        readyQ = CPUScheduler.getReadyQueue();
        pcbList = CPUScheduler.getPCBList();
//        cpus = CPUScheduler.getCpus();
    }

    public void begin() {
        sortPCBListArrival(getPCBList()); // increasing order (arrival time)

        do {
            System.out.println("\n**** SYSTEM TIME: " + time + " ****");

            for (CPU cpu : getCpus()) {

                // assign a process to an available CPU Core
                if (!cpu.isOccupied()) {

                    for (PCB pcb : getPCBList()) {
                        if (readyQ.isEmpty() && pcb != null && !getPCBList().isEmpty()
                                && (!pcb.getState().equals(STATE.RUNNING)
                                || !pcb.getState().equals(STATE.TERMINATED)
                                || !pcb.getState().equals(STATE.WAITING))) {
                            if (pcb.getProcess().getArrivalTime() == time) {
                                if (pcb.getState().equals(STATE.NEW)) {
                                    pcb.setState(STATE.READY);
                                    getReadyQueue().add(pcb);
                                }
                            } else if (pcb.getState().equals(STATE.READY)) {
                                getReadyQueue().add(pcb);
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
                    // if cpu is occupied by a process
                    execute(cpu);
                    clearCPU(cpu); // Clear CPUs if process complete
                }
            }
            displayReadyQueue();
//          displayWaitQueue();
            displayCPUs();
//            System.exit(0);

            tick();
//            if (time == 25) return;
        } while (!getReadyQueue().isEmpty() || !getPCBList().isEmpty());

        System.out.print("\n****** SYSTEM TERMINATING (FCFS ALGORITHM) ******");
    }

    public void execute(CPU cpu) {

        // cpu.getPCB().getProcess().getArrivalTime() + CPUScheduler.getQuantum();

        if (cpu.getPCB() == null)
            return;

        cpu.getPCB().setState(STATE.RUNNING);
        cpu.setOccupied(true);

//        // Check if IO at system time
        if (cpu.getPCB().getProcess().getIORequest()) {
            cpu.getPCB().getProcess().checkIO(time, cpu);
        }
        // Check if IO at system time
        if (cpu.getPCB().getState().equals(STATE.WAITING)) {
            handleIO(cpu);
            System.out.println("WAITING " + cpu.getPCB().getPid());
            return;
        }
        if (cpu.getPCB().getQuantumCount() <= CPUScheduler.getQuantum()) {
//            System.out.println(cpu.getPCB().getQuantumCount()+ " QUANTUM COUNT FOR CPU:"  + cpu.getKey() + " :" + cpu.getPCB().getPid());
            cpu.getPCB().setQuantumCount(cpu.getPCB().getQuantumCount() + 1);
        } else {
            cpu.getPCB().setState(STATE.READY);
            cpu.getPCB().setQuantumCount(1);
            cpu.setOccupied(false);
            cpu.setPCB(null);
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
//                    cpu.getPCB().setQuantumCount(cpu.getPCB().getQuantumCount() + 1);
            getPCBList().remove(cpu.getPCB());
            cpu.setOccupied(false);
            cpu.setPCB(null);
            return;
        }



        cpu.getPCB().setPcounter(cpu.getPCB().getPcounter() + 1);
    }

    public void sortPCBListArrival(List<PCB> pcbList) {
        pcbList.sort(Comparator.comparingInt(o -> o.getProcess().getArrivalTime()));
    }

    public void tick() { time++; }
}
