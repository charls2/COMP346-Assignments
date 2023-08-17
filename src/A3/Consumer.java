package A3;

import A1.STATE;

import java.util.ArrayList;

import static A3.Driver.getRand;

public class Consumer extends BoundedBuffer {

    private ArrayList<Item> consumedItems;
    private static STATE state = STATE.READY;
    private final double probability;

    public Consumer(double probability) {
        this.probability = probability;
        this.consumedItems = new ArrayList<>();
    }

    public void start() {
            // wait until full > 0 and then decrement 'full' && acquire lock
            if (this.wait(this.getFull())) {
                if (this.wait(this.getMutex())) {
                    //
                } else {
                    this.getFull().setS(getFull().getS()+1);
                    this.getMutex().setS(getMutex().getS()+1);
                    return;
                }
            } else {
                this.getFull().setS(getFull().getS()+1);
                return;
            }

            // Remove data from buffer
            consumeItem();

            // release lock
            this.signal(this.getMutex());
            this.signal(this.getEmpty());
    }

    public void consumeItem() {
        if (c < 1 - BoundedBuffer.getQ()) {
            System.out.println("CONSUME ITEM");

            if (BoundedBuffer.getBuffer().isEmpty()) {
                BoundedBuffer.setState(STATE.EMPTY);
            } else {
                getConsumedItems().add(BoundedBuffer.getBuffer().remove(0));
            }
//        if (this.getFull().getS() == 0) {
//            state = STATE.READY;
//        }
//        if (this.getFull().getS() > 0) {
//            // Consume
//            Item item = BoundedBuffer.getBuffer()[0];
//            Arrays.fill(consumedItems, item);
//        }
        }
    }

    public static STATE getState() {
        return state;
    }

    public static void setState(STATE state) {
        Consumer.state = state;
    }

    public double getProbability() {
        return probability;
    }

    public ArrayList<Item> getConsumedItems() {
        return this.consumedItems;
    }
}
