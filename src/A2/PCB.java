package A2;

import A1.STATE;

import java.util.ArrayList;

public class PCB {

    private STATE state = STATE.NEW;
    private String pid;
    private int pcounter;
    private int ioCounter;
    private boolean ioComplete = false;
    private int quantumCount = 1;


    private ArrayList<Object> register;

    private Process process;

    public PCB(Process process) {
        this.process = process;
        this.pid = process.getPid();
        this.pcounter = 0;
        this.ioCounter = 0;
        this.register = new ArrayList<>();
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

    public boolean getIOComplete() {
        return this.ioComplete;
    }

    public void setIOComplete(boolean b) {
        this.ioComplete = b;
    }

    public int getQuantumCount() {
        return quantumCount;
    }

    public void setQuantumCount(int quantumCount) {
        this.quantumCount = quantumCount;
    }
}
