
public class Chess {
    public static final int KING = 0;
    public static final int QUEEN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int PAWN = 5;
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int A_FILE = 0;
    public static final int B_FILE = 1;
    public static final int C_FILE = 2;
    public static final int D_FILE = 3;
    public static final int E_FILE = 4;
    public static final int F_FILE = 5;
    public static final int G_FILE = 6;
    public static final int H_FILE = 7;
    public static final int ROW_1 = 0;
    public static final int ROW_2 = 1;
    public static final int ROW_3 = 2;
    public static final int ROW_4 = 3;
    public static final int ROW_5 = 4;
    public static final int ROW_6 = 5;
    public static final int ROW_7 = 6;
    public static final int ROW_8 = 7;
    public static final int[] PIECES = new int[] { KING, QUEEN, KNIGHT, BISHOP, ROOK, PAWN };
    public static final int[] FILES = new int[] { A_FILE, B_FILE, C_FILE, D_FILE, E_FILE, F_FILE, G_FILE, H_FILE };
    public static final int[] FILES_REVERSED = new int[] { H_FILE, G_FILE, F_FILE, E_FILE, D_FILE, C_FILE, B_FILE,
            A_FILE };
    public static final int[] ROWS = new int[] { ROW_1, ROW_2, ROW_3, ROW_4, ROW_5, ROW_6, ROW_7, ROW_8 };
    public static final int[] ROWS_REVERSED = new int[] { ROW_8, ROW_7, ROW_6, ROW_5, ROW_4, ROW_3, ROW_2, ROW_1 };
    public static final char[][] PIECE_CHARS = new char[][] { { '♚', '♛', '♞', '♝', '♜', '♟' },
            { '♔', '♕', '♘', '♗', '♖', '♙' } };
    public static final int[] COLORS = new int[] { WHITE, BLACK };
    public static final long[][] DEFAULT_PIECEBOARDS = new long[][] {
            { 16l, 8l, 66l, 36l, 129l, 65280l },
            { 576460752303423488l, 1152921504606846976l, 4755801206503243776l, 2594073385365405696l,
                    -9151314442816847872l,
                    71776119061217280l }
    };
    int turn; // false = white's turn
    long[][] pieceBoards;

    public Chess() {
        this.turn = WHITE;
        this.pieceBoards = new long[COLORS.length][PIECES.length];
        pieceBoards = new long[COLORS.length][PIECES.length];
        for (int color : COLORS) {
            for (int piece : PIECES) {
                pieceBoards[color][piece] = DEFAULT_PIECEBOARDS[color][piece];
            }
        }
    }

    public void switchTurn() {
        this.turn = (this.turn == WHITE) ? BLACK : WHITE;
    }

    public void print() {
        boolean flag = false;
        System.out.print("\r\n╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻\r\n│");
        for (int row : ROWS_REVERSED) {
            if (flag) {
                System.out.print("\r\n│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│\r\n|");
            }
            flag = true;
            foundPiece: for (int file : FILES) {
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if (get(pieceBoards[color][piece], file, row) == 1l) {
                            System.out.print(" " + PIECE_CHARS[color][piece] + " │");
                            continue foundPiece; 
                        }
                    }
                }
                System.out.print("   │"); // This line only executes if no piece is found;
            }
        }
        System.out.println("\r\n╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹");
    }

    public static void print(long input) {
        for (long i = 63; i >= 0; i--) {
            if (i % 8 == 7) {
                System.out.println();
            }
            long index = 8 * (i / 8) + (7 - (i % 8));
            System.out.print(" " + ((input >>> index) & 1l));
        }
        System.out.println();
        System.out.println();

    }

    public static long pushLeft(long input, int shift) {
        if (shift > 63) {
            return 0l;
        }
        if (shift < 0) {
            return pushRight(input, -shift);
        }
        return (input >>> shift);
    }

    public static long pushRight(long input, int shift) {
        if (shift > 63) {
            return 0l;
        }
        if (shift < 0) {
            return pushLeft(input, -shift);
        }
        return (input << shift);
    }

    public static long pushUp(long input, int shift) {
        return pushRight(input, 8 * shift);
    }

    public static long pushDown(long input, int shift) {
        return pushLeft(input, 8 * shift);
    }

    public static long push(long input, int rightShift, int upShift) {
        input = pushRight(input, rightShift);
        input = pushUp(input, upShift);
        return input;
    }

    public static long create(int x, int y) {
        long output = 1l;
        output = pushRight(output, x);
        output = pushUp(output, y);
        return output;
    }

    public static long create(String square) {
        int x = square.toLowerCase().charAt(0) - 'a';
        int y = square.toLowerCase().charAt(1) - '1';
        return create(x, y);
    }

    public static long get(long input, int x, int y) {
        input = pushLeft(input, x);
        input = pushDown(input, y);
        return 1l & input;
    }

    public static long set(long input, int x, int y) {
        return input | create(x, y);
    }

    public static long unset(long input, int x, int y) {
        return input & (~create(x, y));
    }

    public static long toggle(long input, int x, int y) {
        return (get(input, x, y) == 1l) ? unset(input, x, y) : set(input, x, y);
    }
}