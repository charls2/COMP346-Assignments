package A2;

import A1.STATE;

import java.util.*;

public class FCFS {

    private int time;
    private Queue<PCB> readyQ;
    private ArrayList<CPU> cpus;
    private List<PCB> pcbList;

    public FCFS() {
        System.out.println("\n********** FCFS **********");

        time = CPUScheduler.getTime();
        readyQ = CPUScheduler.getReadyQueue();
        pcbList = CPUScheduler.getPCBList();
        cpus = CPUScheduler.getCpus();
    }

    public void begin() {

        if (!pcbList.isEmpty()) sortPCBList(pcbList);

        while (!readyQ.isEmpty() || !pcbList.isEmpty()) {

            System.out.println("\n**** SYSTEM TIME: " + time + " ****");
            // assign a process to an available CPU Core
            for (CPU cpu : CPUScheduler.getCpus()) {

                if (!cpu.isInUse()) {
                    PCB pcbFromList = null;
                    if (!pcbList.isEmpty()) {
                        pcbFromList = pcbList.get(0);
                    }
                    if (!pcbList.isEmpty() && time == pcbFromList.getProcess().getArrivalTime()) {
                        pcbFromList.setState(STATE.READY);
                        readyQ.add(pcbFromList);
                        pcbList.remove(0);
                        sortPCBList(pcbList);
                    }
                    PCB current = readyQ.poll();
                    // current process not running on another cpu*/

                    // Clear CPUs if process complete
                    if (cpu.getPCB() != null && cpu.getPCB().getState().equals(STATE.TERMINATED)) {
                        cpu.setInUse(false);
                        cpu.setPCB(null);
                    }

                    if (current != null && current.getState().equals(STATE.READY)) {
                        cpu.setPCB(current);
                        cpu.setInUse(true);
                        //pcbList.add(current);
                        sortPCBList(pcbList);
                        //readyQ.add(current);

                        if (cpu.getPCB() != null)
                            execute(cpu);
                        continue;
                    }
                    if (current != null && current.getState().equals(STATE.RUNNING)) {
                        if (cpu.getPCB() != null)
                            execute(cpu);
                        readyQ.add(current);
                    }


                } else {
                    // if cpu is in use
                    if (cpu.getPCB() != null
                            && (cpu.getPCB().getState().equals(STATE.READY)
                            || cpu.getPCB().getState().equals(STATE.RUNNING))) {
                        execute(cpu);
                        readyQ.add(cpu.getPCB());
                        continue;
                    }

                    // IO
                    if (cpu.getPCB() != null && cpu.getPCB().getState().equals(STATE.WAITING)) {
                        // do the io stuff
                    }

                    // Clear CPUs if process complete
                    if (cpu.getPCB() != null && cpu.getPCB().getState().equals(STATE.TERMINATED)) {
                        cpu.setInUse(false);
                        cpu.setPCB(null);
                    }

                }
            }

            CPUScheduler.displayReadyQueue();
            CPUScheduler.displayCPUs();

            tick();
//            if (time == 20) {
//                System.exit(0);
//            }
        }
        System.out.println("****** SYSTEM TERMINATING (FCFS ALGORITHM) ******");
    }

    public void execute(CPU cpu) {
//        int arrivalTime = cpu.getPCB().getProcess().getArrivalTime();
//        int burstTime = cpu.getPCB().getProcess().getBurstTime();
        if (cpu.getPCB() == null)
            return;

        int progCounter = cpu.getPCB().getPcounter(); // use this to increment every time

        // Check if IO
        


        // Check if reached burst
        if (progCounter == cpu.getPCB().getProcess().getBurstTime()) {
            cpu.getPCB().getProcess().setComplete(true);
        }

        if (cpu.getPCB().getProcess().isCompleted()) {
            System.out.println("CPU #: " + cpu.getKey() + " [COMPLETED: "
                    + cpu.getPCB().getPid() + "]");
            cpu.getPCB().setState(STATE.TERMINATED);
            return;
        }

        System.out.println("CPU #: " + cpu.getKey() + " [Currently Executing: "
                + cpu.getPCB().getPid() + " Prog Counter: " + progCounter + "]");
        cpu.getPCB().setPcounter(progCounter + 1);
        cpu.getPCB().setState(STATE.RUNNING);
    }

    public void tick() {
        time++;
    }

    public List<PCB> sortPCBList(List<PCB> pcbList) {
//        PCB firstPCB = pcbList.get(0);
//        for (PCB pcb : pcbList) {
//            if (pcb.getProcess().getArrivalTime() < firstPCB.getProcess().getArrivalTime()) {
//                firstPCB = pcb;
//            }
//        }
        //pcbList.remove(0);
//        firstPCB.setState(STATE.READY);
        //readyQ.add(firstPCB);
        pcbList.sort(Comparator.comparingInt(o -> o.getProcess().getArrivalTime()));

        for (PCB pcb : pcbList) {
            //pcb.setState(STATE.READY);
//            System.out.println("ID" + pcb.getPid());
        }
        return pcbList;
    }
}
