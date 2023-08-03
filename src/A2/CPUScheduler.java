package A2;

import java.util.*;

public class CPUScheduler {

    // VARIABLES
    private static int time = 0;
    private int numCPUs;
    private int quantum;
    private static Queue<PCB> readyQueue = new LinkedList<>();
    private static List<PCB> pcbList = new ArrayList<>();
    private static ArrayList<CPU> cpus;

    public CPUScheduler() {}

    public void init() {
        // Read File & Add processes to ready queue
        Driver.getMyFileReader().readFile();
        cpus = new ArrayList<>();
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
        for (PCB p : readyQueue) {
            if (p != null) {
                System.out.print(p.getPid() + ", ");
            }
        }
        System.out.println("]");
    }

    public static void displayCPUs() {
        for (CPU cpu : cpus) {
            if (cpu.isInUse() && cpu.getPCB() != null) {
                System.out.println("[CPU #: " + cpu.getKey()
                        + "] [ <- ProcessID: " + cpu.getPCB().getPid() + "]");
            } else {
                System.out.println("[CPU #: " + cpu.getKey()
                        + "] [ <- ProcessID: N/A]");
            }
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
