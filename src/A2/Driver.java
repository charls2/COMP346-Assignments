package A2;

import java.io.File;

public class Driver {

    private static final MyFileReader myFileReader = new MyFileReader(new File("a2_input1.txt"));
    private static CPUScheduler cpuScheduler;


    public static void main(String[] args) {
        cpuScheduler = new CPUScheduler();
        cpuScheduler.init();
    }

    public static MyFileReader getMyFileReader() {
        return myFileReader;
    }

    public static CPUScheduler getCpuScheduler() {
        return cpuScheduler;
    }
}
