public class PCB {

    STATE state = STATE.NEW;
    String pid;
    int pcounter;

    PCB(String pid, int pcounter) {
        this.pid = pid;
        this.pcounter = pcounter;
    }

}
