package A1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class CPU {

    static File file = new File("input.txt");

    static Queue<PCB> readyQueue = new LinkedList<>();
    static PCB currentPCB;

    static IODevice IO1 = new IODevice();
    static IODevice IO2 = new IODevice();

    static int timeUnit = 0;



    public static void main(String[] args) throws IOException {
        // First we need to read the file given (Format is standard)
        readFile(); // populate readyQueue
        begin(); //
    }

    static void readFile() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = reader.readLine();

            while (line != null) {
                String[] split = line.split(", ");
                String[] ioSplit = split[2].split(" ");

                String pid = split[0];
                String numInstruct = split[1];
                String ioRequestAtInstruction = ioSplit[0];
                String ioDevicesRequested = ioSplit[1];

                // Create the process
                Process p = new Process(Integer.parseInt(pid) ,numInstruct, ioRequestAtInstruction, ioDevicesRequested);

                readyQueue.add(p.getPCB());

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void begin() {
        // Move Processes from readyQueue to running state
        while (!readyQueue.isEmpty() || !IO1.getWaitQueue().isEmpty()
                || !IO2.getWaitQueue().isEmpty()) {

            // WAITING QUEUE
            if (!IO1.getWaitQueue().isEmpty()) {
                PCB waitPCB = IO1.getWaitQueue().poll();
                if (waitPCB != null) {
                    IO1.execute(waitPCB);
                }
            } else if (!IO2.getWaitQueue().isEmpty()) {
                PCB waitPCB = IO2.getWaitQueue().poll();
                if (waitPCB != null) {
                    IO2.execute(waitPCB);
                }
            }

            // READY QUEUE
            currentPCB = readyQueue.poll(); // retrieve and remove head of queue
            if (currentPCB != null) {
                currentPCB.getProcess().execute();
                // Switch process
            }
        }

        System.out.println("\nTERMINATING SYSTEM");
        System.exit(0);
    }

    public static void print() {
        System.out.print("READY QUEUE: [");
        for (PCB p : readyQueue) {
            System.out.print("PID: " + p.getPid() + " ");
        }
        System.out.println("]");
        System.out.print("WAIT QUEUE IO1: [");
        for (PCB p : IO1.getWaitQueue()) {
            System.out.print("PID: " + p.getPid() + " ");
        }
        System.out.println("]");
        System.out.print("WAIT QUEUE IO2: [");
        for (PCB p : IO2.getWaitQueue()) {
            System.out.print("PID: " + p.getPid() + " ");
        }
        System.out.println("]");
    }

    public static int getTimeUnit() {
        return timeUnit;
    }

    public static void setTimeUnit(int time) {
        timeUnit = time;
    }
}
