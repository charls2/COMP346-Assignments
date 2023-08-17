package A3;

public class Semaphore {

    private int s;
    private String type;

    public Semaphore(int s, String type) {
        this.s = s;
        this.type = type;
    }

    public Semaphore() {}

    public boolean wait(Semaphore s) {

        switch (s.getType()) {
            case "empty" -> { // # of empty spots in buffer
                // do stuff
                if (s.s > 0) { // meaning: if there r empty spots
                    s.s--;
                    return true;
                } else if (s.getS() == 0) {
                    return false;
                }
            }
            case "full" -> { // # of items in buffer
                if (s.s > 0) { // items are in the buffer
                    s.s--;
                    return true;
                } else {
                    return false;
                }
            }
            case "mutex" -> { // lock
                if (s.s == 1) { // unlocked
                    s.s--;
                    return true;
                } else { // locked
                    return false;
                }
            }
            default -> {
                return false;
            }
        }
        return false;
    }

    public void signal(Semaphore s) { // increments
//        s.s++;
//        System.out.println("Incremented S (" + s.getType() + ") from " + (s.s-1) + " to: " + s.s);

        switch (s.getType()) {
            case "empty" -> { // # of empty spots in buffer
                if (s.s < BoundedBuffer.getBuffer().size()) { // meaning: if there r empty spots
                    s.s++;
                }
            }
            case "full" -> { // # of items in buffer
                if (s.s <= BoundedBuffer.getBuffer().size()) { // items are in the buffer
                    s.s++;
                }
            }
            case "mutex" -> { // lock
                if (s.s == 0) { // unlocked
                    s.s++;
                }
            }
            default -> {
                break;
            }
        }
    }

    public int getS() {
        return s;
    }

    public String getType() {
        return type;
    }

    protected void setS(int i) {
        this.s = i;
    }
}
