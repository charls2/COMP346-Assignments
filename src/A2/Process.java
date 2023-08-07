package A2;

import A1.STATE;

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

    public void checkIO(int time) {
        if (pcb.getState().equals(STATE.WAITING)) {
//            pcb.setIoCounter(pcb.getIoCounter()+1);
            return;
        }
        if (ioRequest) {

            io_RequestAtTimes = io_RequestAtTimes.replaceAll("\\[|\\]", "");
            String[] split = io_RequestAtTimes.split(",");

            for (int i = 0  ; i < split.length ; i++) {
                if (Integer.parseInt(split[i]) + pcb.getProcess().getArrivalTime() == time
                        && !IODevice.hasDuplicates(CPUScheduler.io.getWaitQueue())) { // io at sys-time
                    pcb.setState(STATE.WAITING);
                    break;
                }
            }
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

    public boolean getIORequest() {
        return ioRequest;
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setComplete(boolean b) {
        this.completed = b;
    }
}
