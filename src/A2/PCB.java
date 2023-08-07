package A2;

import A1.STATE;

import java.util.ArrayList;

public class PCB {

    private STATE state = STATE.NEW;
    private String pid;
    private int pcounter;
    private int ioCounter;

    private ArrayList<Object> register;

    private Process process;

    public PCB(Process process) {
        this.process = process;
        this.pid = process.getPid();
        this.pcounter = 0;
        this.ioCounter = 0;
        this.register = new ArrayList<>();

        register.add("xyz"); // assigned value (same for each A1.PCB)
        register.add(44213); // assigned value
        register.add('c'); // assigned value
    }

    public STATE getState() {
        return state;
    }

    public String getPid() {
        return pid;
    }

    public int getPcounter() {
        return pcounter;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public ArrayList<Object> getRegister() {
        return register;
    }

    public Process getProcess() {
        return process;
    }

    public void setPcounter(int currentInstruction) {
        this.pcounter = currentInstruction;
    }

    public int getIoCounter() {
        return ioCounter;
    }

    public void setIoCounter(int ioCounter) {
        this.ioCounter = ioCounter;
    }
}
