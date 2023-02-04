import java.util.HashMap;
import java.util.Map;

public class Driver {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        Chess game = new Chess();
        game.setFromFen("n1n5/PPPk4/8/8/8/8/4Kppp/5N1N b - - 0 1");
        game.Perft(4, true);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime); // divide by 1000000 to get milliseconds.
        System.out.println("This took " + duration + " nanoseconds.");
    }
}
