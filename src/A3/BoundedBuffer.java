package A3;

import A1.STATE;

import java.util.ArrayList;

import static A3.Driver.*;

public class BoundedBuffer extends Semaphore {

    private static ArrayList<Item> buffer;
    private final int bufferSize;
    private static STATE state = STATE.EMPTY;
    private static double q = 0; // user input
    static double p, c; // for item generation
    public static int time = 0;
    private Producer producer;
    private Consumer consumer;
    private static Semaphore mutex, empty, full;


    public BoundedBuffer() {
        super();
        bufferSize = 10;
        buffer = new ArrayList<>(bufferSize);

        // Binary semaphore which is used to acquire and release the lock
        this.setMutex(new Semaphore(1, "mutex"));
        // A counting semaphore whose intitial value
        // is the number of slots in the buffer,
        // since, initially all slots are empty
        this.setEmpty(new Semaphore(bufferSize, "empty"));
        // A counting semaphore whose initial value is 0, # of items in the buffer
        this.setFull(new Semaphore(0, "full"));
    }

    public void begin() {
        System.out.print("User please enter a value for q: ");
        q = getScan().nextDouble();
        if (!inRange(q)) {
            System.out.println("[*] Invalid Input.");
            System.exit(0);
        }
        producer = new Producer(q);
        consumer = new Consumer(1.0-q);

        // IMPLEMENT
        do {
            System.out.println("\n********* SYSTEM TIME: " + getTime() + " *********");

            p = Math.random();
            c = Math.random();

            producer.start();
            displayProducedItems();
            displayBuffer();
            displaySemaphores();

            consumer.start();
            displayConsumedItems();
            displayBuffer();
            displaySemaphores();
            System.out.println("Buffer State: " + getState());

            tick();
//            if (time == 25) { System.exit(0); }
        } while (time != 25);
    }

    private boolean inRange(double q) {
        if (q >= 0.0 && q <= 1.0) {
            System.out.println("Input is valid.");
            return true;
        } else {
            System.out.println("Input is not within the specified range.");
            return false;
        }
    }

    public void displayBuffer() {
        System.out.print("BUFFER: [ ");
        for (Item item : getBuffer()) {
            System.out.print("ItemID: " + item.getId() + ", ");
        }
        System.out.println("]");
    }

    public void displayProducedItems() {
        System.out.print("Produced Items: [ ");
        for (Item item : producer.getProducedItems()) {
            System.out.print("ItemID: " + item.getId() + ", ");
        }
        System.out.println("]");
    }
    public void displayConsumedItems() {
        System.out.print("Consumed Items: [ ");
        for (Item item : consumer.getConsumedItems()) {
            System.out.print("ItemID: " + item.getId() + ", ");
        }
        System.out.println("]");
    }

    public void displaySemaphores() {
        System.out.println("EMPTY: " + empty.getS()
                + "\nFULL: " + full.getS()
                + "\nMUTEX: " + mutex.getS());
    }


    // ********** GETTERS & SETTERS **********
    public static double getQ() {
        return q;
    }

    public void setQ(double i) {
        q = i;
    }

    public static ArrayList<Item> getBuffer() {
        return buffer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Producer getProducer() {
        return producer;
    }

    public static int getTime() {
        return time;
    }

    public static void tick() {
        time++;
    }

    public Semaphore getEmpty() {
        return empty;
    }

    public void setEmpty(Semaphore empty) {
        BoundedBuffer.empty = empty;
    }

    public Semaphore getFull() {
        return full;
    }

    public void setFull(Semaphore full) {
        this.full = full;
    }

    public void setMutex(Semaphore semaphore) {
        this.mutex = semaphore;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public static STATE getState() {
        return state;
    }

    public static void setState(STATE state) {
        BoundedBuffer.state = state;
    }
}
