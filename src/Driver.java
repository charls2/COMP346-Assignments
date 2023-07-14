import java.io.*;
import java.util.ArrayList;

public class Driver {

    static File file = new File("input.txt");

    static ArrayList<Process> processes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // First we need to read the file given (Format is standard)
        readFile();

        for (Process process : processes) {

        }
    }

    static void readFile() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            reader.readLine();
            String line = reader.readLine();

            while (line != null) {
                String[] split = line.split(", ");
                String[] ioSplit = split[2].split(" ");

                String pid = split[0];
                String numInstruct = split[1];
                String ioRequestAtInstruction = ioSplit[0];
                String ioDevicesRequested = ioSplit[1];

                // Create PCB for the process
                PCB pcb = new PCB(pid, 0);
                // Create the process
                Process p = new Process(pcb, numInstruct, ioRequestAtInstruction, ioDevicesRequested);
                processes.add(p);

                System.out.println(p.details());

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
