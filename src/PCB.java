import java.util.ArrayList;

public class PCB {

    private STATE state = STATE.NEW;
    private int pid;
    private int pcounter;

    private ArrayList<Object> register;

    private Process process;

    PCB(Process process) {
        this.process = process;
        this.pid = process.getPid();
        this.pcounter = process.getCurrentInstruction();
        this.register = new ArrayList<>();

        register.add("xyz"); // assigned value (same for each PCB)
        register.add(44213); // assigned value
        register.add('c'); // assigned value
    }

    public STATE getState() {
        return state;
    }

    public int getPid() {
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

    public void executeInstructions() {
        process.execute();
    }

    public void setPcounter(int currentInstruction) {
        this.pcounter = currentInstruction;
    }
}
