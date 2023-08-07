package A2;

public class CPU {

    private int key;
    private PCB occupier;
    private boolean occupied;

    public CPU(int key) {
        this.key = key;
        this.occupied = false;
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

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
