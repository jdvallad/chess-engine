import java.util.Map;
import java.util.Set;

public abstract class Chess {

    boolean gameOver;
    Set<String> legalMoves;

    public abstract Chess clone();

    public abstract Chess setFromFen(String fen);

    public abstract String getFen();

    public abstract Chess move(String move);

    public abstract Chess move(String... move);

    public abstract Chess undo();

    public abstract Set<String> getLegalMoves();

    public abstract Set<String> getLegalMoves(Set<String> output);

    public abstract Map<String, Integer> perftMap(int depth);

    public abstract int perft(int depth, boolean verbose);

    public abstract int perft(int depth);

    public abstract void printBoard(int perspective);

    public abstract void printEntireGame();

    public abstract void printLegalMoves();
}
