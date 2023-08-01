package A2;

public class Process implements Comparable<Process> {

    private String pid;
    private int arrivalTime;
    private int burstTime;
    private String io_RequestAtTimes;
    private boolean ioRequest = false;
    private boolean completed = false;

    private PCB pcb;

    public Process(String pid, int arrivalTime, int burstTime, String io_RequestAtTimes) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.io_RequestAtTimes = io_RequestAtTimes;
        this.pcb = new PCB(this);

        // check if IO requested (IOBound or CPUBound)
        if (!io_RequestAtTimes.equals("")) {
            ioRequest = true;
        }
    }

    public void execute() {
//        for (int i = arrivalTime ; i <= burstTime ; i++) {
//            if (CPUScheduler.getTime() == i) {
//                // begin execution
//                //checkIO();
//                System.out.println("Process: " + pid + " [Executing...]");
//
//                if (i == burstTime && checkIO()) {
//                    System.out.println("Process: " + pid + " [Finished Executing]");
//                    completed = true;
//                }
//                CPUScheduler.setTime(CPUScheduler.getTime() + 1);
//
//            } else {
//                // IDLING
//                System.out.println("System Status: [IDLING...]");
//                break;
//            }
//        }

    }

    public void checkIO() {
        // Check for IO Request
        if (ioRequest) {
            // Format IO at Instruction
            io_RequestAtTimes = io_RequestAtTimes.replaceAll("\\[|\\]", "");
            String[] split = io_RequestAtTimes.split(",");
            System.out.println("PROCESS: " + pid + " IOREQUESTATTIMES: " + io_RequestAtTimes);
            // loop check every number if matches current instruction.
            for (int i = 0  ; i < split.length ; i++) {
                // FCFS

                // SJF

                // RR
            }
            // break out of loop
        }
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public String getIo_RequestAtTimes() {
        return io_RequestAtTimes;
    }

    public void setIo_RequestAtTimes(String io_RequestAtTimes) {
        this.io_RequestAtTimes = io_RequestAtTimes;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public int compareTo(Process p) {
        return Integer.compare(this.getArrivalTime(), p.getArrivalTime());
    }

    public PCB getPcb() {
        return pcb;
    }
}
