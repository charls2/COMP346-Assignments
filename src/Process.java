public class Process {

    private int pid;
    private String numInstruct;
    private String ioRequestAtInstruction;
    private String ioDevicesRequested;
    private boolean ioRequest = false;

    private PCB pcb;

    private int currentInstruction = 0;
    private final int timePerProcess = 2;

    private IODevice IO1;
    private IODevice IO2;



    Process(int pid, String numInstruct, String ioRequestAtInstruction, String ioDevicesRequested) {
        this.pid = pid;
        this.pcb = new PCB(this);
        this.numInstruct = numInstruct;
        this.ioRequestAtInstruction = ioRequestAtInstruction;
        this.ioDevicesRequested = ioDevicesRequested;
        readyProcess(); // Process allocated to CPU
    }

    void readyProcess() {
        this.pcb.setState(STATE.READY);

        // check if IO requested (IOBound or CPUBound)
        if (!ioRequestAtInstruction.equals("[]")) {
            ioRequest = true;
        }
    }

    public void executeInstruction() {

        this.pcb.setState(STATE.RUNNING);
        // two time units per process
        for (int i = 0 ; i < timePerProcess ; i++) {
            System.out.println("\nTIME UNIT: " + CPU.getTimeUnit());

            if (currentInstruction == Integer.parseInt(numInstruct) + 1) {
                System.out.println("PROCESS ID: " + this.pid + " FINISHED EXECUTING ALL INSTRUCTIONS");
                pcb.setState(STATE.TERMINATED);
                CPU.setTimeUnit(CPU.getTimeUnit() + 1);
                CPU.readyQueue.remove();
                break;
            }

            checkIO();
            //System.out.println("PROCESS ID: " + this.pid + ", IO Request at Instruction: " + currentInstruction);
            //return;

//        }

            CPU.readyQueue.add(pcb);
            System.out.println("PROCESS ID: " + this.pid + ", Current Instruction: " + currentInstruction);
            CPU.setTimeUnit(CPU.getTimeUnit() + 1);
            pcb.setPcounter(currentInstruction);
            currentInstruction++;

        }
        this.pcb.setState(STATE.READY);
    }

    public void checkIO() {
        // Check for IO Request
        if (ioRequest) {
            // Format IO at Instruction
            ioRequestAtInstruction = ioRequestAtInstruction.replaceAll("\\[|\\]", "");
            String[] split = ioRequestAtInstruction.split(",");

            // Format IODevicesRequested
            ioDevicesRequested = ioDevicesRequested.replaceAll("\\[|\\]", "");
            String[] split2 = ioDevicesRequested.split(",");

            // loop check every number if matches current instruction.
            for (int i = 0  ; i < split.length ; i++) {
                // IO Request at specific instruction
                if (Integer.parseInt(split[i]) == currentInstruction) {
                    System.out.println("PROCESS ID: " + this.pid + ", IO Request at Instruction: " + currentInstruction);

                    pcb.setPcounter(currentInstruction);
                    pcb.setState(STATE.WAITING);

                    //CPU.waitingQueue.add(CPU.currentPCB);


                    // Check which device is wanted and initiate it
                    if (Integer.parseInt(split2[i]) == 1) {
                        //CPU.IO1 = new IODevice(this);
                        CPU.IO1.getWaitQueue().add(pcb);
                        //return IO1 = new IODevice();
                    } else {
                        //CPU.IO2 = new IODevice(this);
                        CPU.IO2.getWaitQueue().add(pcb);
                        //return IO2 = new IODevice();
                    }
                    break;
                }
            }
            // break out of loop


        }
        //return null;
    }

    public void setCurrentInstruction(int currentInstruction) {
        this.currentInstruction = currentInstruction;
    }

    public void setIoRequest(boolean ioRequest) {
        this.ioRequest = ioRequest;
    }

    public void setNumInstruct(String numInstruct) {
        this.numInstruct = numInstruct;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }

    public PCB getPCB() {
        return this.pcb;
    }

    public int getCurrentInstruction() {
        return currentInstruction;
    }

    public int getPid() {
        return pid;
    }

    public boolean isIoRequest() {
        return ioRequest;
    }

    public IODevice getIO2() {
        return IO2;
    }

    public IODevice getIO1() {
        return IO1;
    }

    public int getNumInstruct() {
        return Integer.parseInt(numInstruct);
    }

    public int getTimePerProcess() {
        return timePerProcess;
    }
}
