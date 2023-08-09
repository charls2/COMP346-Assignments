package A2;

import A1.STATE;

import java.util.ArrayList;
import java.util.Arrays;

public class Process {

    private String pid;
    private int arrivalTime;
    private int burstTime;
    private int execTime;
    private String io_RequestAtTimes;
    private boolean ioRequest = false;
    private boolean completed = false;
    private ArrayList<String> ioAtTimes = new ArrayList<>();

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

            io_RequestAtTimes = io_RequestAtTimes.replaceAll("\\[|\\]", "");
            String[] split = io_RequestAtTimes.split(",");

            ioAtTimes.addAll(Arrays.asList(split));
        }
    }

    public void checkIO2(int time, CPU cpu) {
        if (cpu.getPCB().getState().equals(STATE.WAITING)) {
            return;
        }
        for (String io : cpu.getPCB().getProcess().getIoAtTimes()) {
            if (Integer.parseInt(io) + cpu.getPCB().getProcess().getArrivalTime() == time) { // io at sys-time
                cpu.getPCB().setState(STATE.WAITING);
                cpu.getPCB().setIoCounter(0);
                System.out.println("PROCCESS: " + cpu.getPCB().getPid() + " IO NOW");
                ioAtTimes.remove(io);
                break;
            }
        }
    }

    public void checkIO(int time, CPU cpu) {
        if (cpu.getPCB().getState().equals(STATE.WAITING)) {
            return;
        }
        for (String io : cpu.getPCB().getProcess().getIoAtTimes()) {
            if (Integer.parseInt(io) + getExecTime() == time) { // io at sys-time
                cpu.getPCB().setState(STATE.WAITING);
                cpu.getPCB().setIoCounter(0);
                System.out.println("PROCCESS: " + cpu.getPCB().getPid() + " IO NOW");
                ioAtTimes.remove(io);
                break;
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

    public ArrayList<String> getIoAtTimes() {
        return ioAtTimes;
    }

    public int getExecTime() {
        return execTime;
    }

    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }
}
