package A2;

public class CPU {

    private int key;
    private PCB occupier;
    private boolean inUse;

    public CPU(int key) {
        this.key = key;
        this.inUse = false;
    }

    public int getKey() {
        return key;
    }

    public PCB getPCB() {
        return occupier;
    }
    public void setPCB(PCB pcb) {
        this.occupier = pcb;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
