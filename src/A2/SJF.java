package A2;

import A1.STATE;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class SJF extends CPUScheduler {

    private int time;
    private Queue<PCB> readyQ;
    private final ArrayList<CPU> cpus;
    private List<PCB> pcbList;
    private boolean fcfsFirst;

    private MathCPU mathCPU;

    public SJF() {
        System.out.println("\n********** SJF **********");

        time = CPUScheduler.getTime();
        readyQ = CPUScheduler.getReadyQueue();
        pcbList = CPUScheduler.getPCBList();
        cpus = CPUScheduler.getCpus();
        fcfsFirst = true;
    }

    public void begin() {

        do {
            System.out.println("\n**** SYSTEM TIME: " + time + " ****");

            for (CPU cpu : CPUScheduler.getCpus()) {

                // assign a process to an available CPU Core
                if (!cpu.isOccupied()) {

                    if (fcfsFirst) {
                        sortPCBListArrival(getPCBList()); // increasing order (arrival time)
                        for (PCB pcb : getPCBList()) {
                            if (!getPCBList().isEmpty()
                                    && (!pcb.getState().equals(STATE.RUNNING)
                                    || !pcb.getState().equals(STATE.TERMINATED)
                                    || !pcb.getState().equals(STATE.WAITING))) {
                                if (pcb.getProcess().getArrivalTime() == time) {
                                    if (pcb.getState().equals(STATE.NEW)) {
                                        pcb.setState(STATE.READY);
                                        getReadyQueue().add(pcb);
                                    }
                                }
                            }
                        }
                    } else {
                        sortPCBListBurst(getPCBList());
                        for (PCB pcb : getPCBList()) {
                            if (!getPCBList().isEmpty()
                                    && (!pcb.getState().equals(STATE.RUNNING)
                                    || !pcb.getState().equals(STATE.TERMINATED)
                                    || !pcb.getState().equals(STATE.WAITING))) {
                                if (pcb.getState().equals(STATE.NEW)) {
                                    pcb.setState(STATE.READY);
                                    getReadyQueue().add(pcb);
                                }
                            }
                        }
                    }

                    PCB currentProcess = null;
                    if (!getReadyQueue().isEmpty())
                        currentProcess = getReadyQueue().poll();

                    cpu.setPCB(currentProcess);

                    execute(cpu);

                    CPUScheduler.clearCPU(cpu); // Clear CPUs if process complete

                } else {
                    // if cpu is occupied by a process
                    execute(cpu);
                    fcfsFirst = false;
                    CPUScheduler.clearCPU(cpu); // Clear CPUs if process complete
                }
            }

            CPUScheduler.displayReadyQueue();
//            CPUScheduler.displayWaitQueue();
            CPUScheduler.displayCPUs();
//            System.exit(0);

            tick();
//            if (time == 25) return;
        } while (!getReadyQueue().isEmpty() || !getPCBList().isEmpty());

        // Math stuff
//        mathCPU.display();
//        System.out.println("STATUS: " + isComplete());

        System.out.print("\n****** SYSTEM TERMINATING (FCFS ALGORITHM) ******");
    }

    public void execute(CPU cpu) {

        if (cpu.getPCB() == null)
            return;

        if (cpu.getPCB().getState().equals(STATE.READY)) {
            cpu.getPCB().getProcess().setExecTime(time);
            System.out.println("EXEC TIME : " + cpu.getPCB().getProcess().getExecTime());
        }
        cpu.getPCB().setState(STATE.RUNNING);
        cpu.setOccupied(true);

        // Check if IO at system time
        if (cpu.getPCB().getProcess().getIORequest()) {
            cpu.getPCB().getProcess().checkIO(time, cpu);
        }
        // Check if IO at system time
        if (cpu.getPCB().getState().equals(STATE.WAITING)) {
            handleIO(cpu);
            System.out.println("WAITING " + cpu.getPCB().getPid());
            return;
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

        // Check if reached burst
        if (cpu.getPCB().getPcounter() == cpu.getPCB().getProcess().getBurstTime()) {
            cpu.getPCB().getProcess().setComplete(true);
        }
        cpu.getPCB().setPcounter(cpu.getPCB().getPcounter() + 1);
        // Still running +1 program counter
    }

    public void sortPCBListArrival(List<PCB> pcbList) {
        pcbList.sort(Comparator.comparingInt(o -> o.getProcess().getArrivalTime()));
    }
    public void sortPCBListBurst(List<PCB> pcbList) {
        pcbList.sort(Comparator.comparingInt(o -> o.getProcess().getBurstTime()));
    }
    private void tick() {
        time++;
    }
}
