package A2;

public class Process {

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

    public PCB getPcb() {
        return pcb;
    }

    public void setComplete(boolean b) {
        this.completed = b;
    }
}
