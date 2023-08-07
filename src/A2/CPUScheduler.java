package A2;

import A1.STATE;

import java.util.*;

public class CPUScheduler {

    // VARIABLES
    private static int time = 0;
    private int numCPUs;
    private int quantum;
    private static Queue<PCB> readyQueue = new LinkedList<>();
    private static List<PCB> pcbList = new ArrayList<>();
    private static ArrayList<CPU> cpus;

    static IODevice io;


    public CPUScheduler() {}

    public void init() {
        // Read File & Add processes to ready queue
        Driver.getMyFileReader().readFile();
        cpus = new ArrayList<>();
        io = new IODevice();
        // Create CPUs and add to ArrayList
        for (int i = 1; i <= numCPUs; i++) {
            CPU core = new CPU(i);
            cpus.add(core);
        }

        FCFS fcfs = new FCFS();
        fcfs.begin();
    }

    public static void displayReadyQueue() {
        System.out.print("Ready Queue: [ ");
        for (PCB p : pcbList) {
            if (p != null /*&& (p.getState().equals(STATE.READY)
                    || p.getState().equals(STATE.RUNNING))*/) {
                System.out.print(p.getPid() + ", ");
            }
        }
        System.out.println("]");
    }

    public static void displayWaitQueue() {
        System.out.print("Wait Queue: [ ");
        for (PCB p : io.getWaitQueue()) {
            if (p != null /*&& (p.getState().equals(STATE.READY)
                    || p.getState().equals(STATE.RUNNING))*/) {
                System.out.print(p.getPid() + ", ");
            }
        }
        System.out.println("]");
    }

    public static void displayCPUs() {
        for (CPU cpu : cpus) {
            if (cpu.isOccupied() && cpu.getPCB() != null) {
                System.out.println("[CPU #: " + cpu.getKey()
                        + "] [ <- ProcessID: " + cpu.getPCB().getPid()
                        + "] [Prog-Counter: " + cpu.getPCB().getPcounter() + "]");
            } else {
                System.out.println("[CPU #: " + cpu.getKey()
                        + "] [ <- ProcessID: N/A]");
            }
        }
    }

    public static void clearCPU(CPU cpu) {
        if (cpu.getPCB() != null
                && cpu.getPCB().getState().equals(STATE.TERMINATED)) {
            cpu.setOccupied(false);
            cpu.setPCB(null);
        }
    }

    // ****************** GETTERS & SETTERS ********************

    public static Queue<PCB> getReadyQueue() {
        return readyQueue;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getNumCPUs() {
        return numCPUs;
    }

    public void setNumCPU(String line) {
        this.numCPUs = Integer.parseInt(line);
    }

    public void setQuantum(String line) {
        this.quantum = Integer.parseInt(line);
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int newtime) {
        time = newtime;
    }

    public static ArrayList<CPU> getCpus() {
        return cpus;
    }

    public static List<PCB> getPCBList() {
        return pcbList;
    }
}
