package A3;

import java.util.Random;
import java.util.Scanner;

public class Driver {

    public static Scanner scan = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        // Stuff to implement
        BoundedBuffer solution = new BoundedBuffer();
        solution.begin();
        scan.close();
    }

    public static Scanner getScan() {
        return scan;
    }

    public static Random getRand() {
        return rand;
    }
}
