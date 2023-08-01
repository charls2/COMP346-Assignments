package A2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyFileReader {

    private String file;

    public MyFileReader(File file) {
        this.file = String.valueOf(file);
    }

    public void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            Driver.getCpuScheduler().setNumCPU(String.valueOf(line.charAt(10))); // NUM OF CPUs
            line = reader.readLine();
            Driver.getCpuScheduler().setQuantum(String.valueOf(line.charAt(2))); // QUANTUM

            // Skip 2 lines
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                String[] split = line.split(" ");
                String ioStr = split[3].replaceAll("\\[\\]", "");

                String pid = split[0];
                String arrivalTime = split[1];
                String burstTime = split[2];

                Process process = new Process(
                        pid
                        , Integer.parseInt(arrivalTime)
                        , Integer.parseInt(burstTime)
                        , ioStr);
                CPUScheduler.getPCBList().add(process.getPcb());
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
