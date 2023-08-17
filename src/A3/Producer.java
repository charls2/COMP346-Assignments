package A3;

import A1.STATE;

import java.util.ArrayList;
import java.util.Arrays;

import static A3.Driver.getRand;

public class Producer extends BoundedBuffer {

    // VARS
    private ArrayList<Item> producedItems;
    private STATE state = STATE.READY;
    private double probability;

    public Producer(double probability) {
        this.probability = probability;
        this.producedItems = new ArrayList<>();
    }

    public void start() {
        // check to see if any empty slots in buffer && acquire lock
        if (this.wait(this.getEmpty())) { // if empty spots

            if (this.wait(this.getMutex())) { // if unlocked
                // produced item
            } else {
                this.getEmpty().setS(getEmpty().getS()+1);
                this.getMutex().setS(getMutex().getS()+1);
                return;
            }
        } else {
            this.getEmpty().setS(getEmpty().getS()+1);
            return;
        }

        // Add data to buffer
        produceItem(); // if we produce an item we cannot consume in the same time

        this.signal(this.getMutex());
        this.signal(this.getFull());
    }

    public void produceItem() {
        if (p < BoundedBuffer.getQ()) {
            System.out.println("PRODUCE ITEM");
//            addItem();
            BoundedBuffer.setState(STATE.FULL);
            if (Math.random() < getQ()) { // no items produced
                return;
            } else if (getQ() == 1) {
                addItem();
                return;
            } else if (getQ() == 0.5) {
                if (getRand().nextBoolean()) {
                    addItem();
                }
            } else if (getQ() == 0.67) {
                if (Math.random() <= 0.67) {
                    System.out.println("Generated: 2 ITEMs");
                    addItem();
                    addItem();
                }
            } else if (getQ() > 0.0 && getQ() < 0.5) {

            }
        }
    }

    public void addItem() {
        Item item = new Item(getRand().nextInt(21));
        getProducedItems().add(item);
        BoundedBuffer.getBuffer().add(item);
    }

    public double getProbability() {
        return probability;
    }

    public ArrayList<Item> getProducedItems() {
        return this.producedItems;
    }
}
