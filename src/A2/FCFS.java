package A2;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FCFS {

    private int time;
    private Queue<PCB> readyQ;
    private ArrayList<CPU> cpus;
    private List<PCB> pcbList;

    public FCFS() {
        System.out.println("\n********** FCFS **********\n");

        time = CPUScheduler.getTime();
        readyQ = CPUScheduler.getReadyQueue();
        pcbList = CPUScheduler.getPCBList();
        cpus = CPUScheduler.getCpus();
    }

    public void begin() {
        // check arrival time of each and see which is lowest and start with that one
        // then go to the next and so on till finished
        // assign pcb to a CPU
        firstArrived(pcbList);

        while (!readyQ.isEmpty()) {

            System.out.println("\n**** SYSTEM TIME: " + time + " ****");
            CPUScheduler.displayReadyQueue();

            // Process the PCB logic
            // assign a process to an available CPU Core
            for (CPU cpu : CPUScheduler.getCpus()) {
                PCB current = readyQ.peek();

                if (!cpu.isRunning()) {
                    cpu.setRunning(true);
                    cpu.setPCB(current);
                }

                if (!current.getProcess().isCompleted()) {
                    execute(current);

                    //current.getProcess().execute();
                    tick();
                    continue; // next iteration of while loop until process is complete
                } else {
                    // current process is finished, so we remove it from the Queue
                    // and go on to the next process.
                    if (!readyQ.isEmpty()) { readyQ.remove(); }
                    if (!pcbList.isEmpty()) {
                        firstArrived(pcbList); // pcbList is missing PCB IN USE (or running)
                    }
                }
                // *********************
                tick();
            }
            // clear cpus
            for (CPU cpu : CPUScheduler.getCpus()) {
                if (cpu.isRunning()) {
                    cpu.setRunning(false);
                    cpu.setPCB(null);
                }
            }


        }
    }

    public void execute(PCB pcb) {
        int arrivalTime = pcb.getProcess().getArrivalTime();
        int burstTime = pcb.getProcess().getBurstTime();

        pcb.getPcounter(); // use this to increment every time

        for (int i = arrivalTime ; i <= burstTime; i++) {

        }
    }

    public void tick() {
        time++;
    }

    public void firstArrived(List<PCB> pcbList) {
        PCB firstPCB = pcbList.get(0);
        for (PCB pcb : pcbList) {
            if (pcb.getProcess().getArrivalTime() < firstPCB.getProcess().getArrivalTime()) {
                firstPCB = pcb;
            }
        }
        pcbList.remove(0);
        readyQ.add(firstPCB);
        //return firstPCB;
    }
}
