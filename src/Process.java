public class Process {

    String numInstruct;
    String ioRequestAtInstruction;
    String ioDevicesRequested;
    boolean ioRequest = false;

    PCB pcb;

    int timeUnit;
    int currentInstruction;

    Process(PCB pcb, String numInstruct, String ioRequestAtInstruction, String ioDevicesRequested) {
        this.pcb = pcb;
        this.numInstruct = numInstruct;
        this.ioRequestAtInstruction = ioRequestAtInstruction;
        this.ioDevicesRequested = ioDevicesRequested;
        this.currentInstruction = 0;
        readyProcess(); // Process allocated to CPU
    }

    void readyProcess() {
        this.pcb.state = STATE.READY;
        // check if IO requested
        if (!ioRequestAtInstruction.equals("[]")) {
            ioRequest = true;
        }
    }

    public void execute() {
        pcb.state = STATE.RUNNING;
        timeUnit = 0;



        timeUnit++; // increment timeUnit at end of process execution
    }


    public String details() {
        return "ProcessID: " /*+ this.pid*/ + " NumInstructions: " + this.numInstruct + " IORequestAtInstruction: "
                + this.ioRequestAtInstruction + " IODevicesRequested: " + this.ioDevicesRequested + " CurrentState: " /*+ this.state*/;
    }

}
