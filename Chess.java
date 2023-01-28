
public class Chess {
    public static final int KING = 0;
    public static final int QUEEN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int PAWN = 5;
    public static final int EMPTY = 6;
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final long A_FILE = 72340172838076673l;
    public static final long B_FILE = 144680345676153346l;
    public static final long C_FILE = 289360691352306692l;
    public static final long D_FILE = 578721382704613384l;
    public static final long E_FILE = 1157442765409226768l;
    public static final long F_FILE = 2314885530818453536l;
    public static final long G_FILE = 4629771061636907072l;
    public static final long H_FILE = -9187201950435737472l;
    public static final long ROW_1 = 255l;
    public static final long ROW_2 = 65280l;
    public static final long ROW_3 = 16711680l;
    public static final long ROW_4 = 4278190080l;
    public static final long ROW_5 = 1095216660480l;
    public static final long ROW_6 = 280375465082880l;
    public static final long ROW_7 = 71776119061217280l;
    public static final long ROW_8 = -72057594037927936l;
    public static final int[] PIECES = new int[] { KING, QUEEN, KNIGHT, BISHOP, ROOK, PAWN, EMPTY };
    public static final long[] FILES = new long[] { A_FILE, B_FILE, C_FILE, D_FILE, E_FILE, F_FILE, G_FILE, H_FILE };
    public static final long[] FILES_REVERSED = new long[] { H_FILE, G_FILE, F_FILE, E_FILE, D_FILE, C_FILE, B_FILE,
            A_FILE };
    public static final long[] ROWS = new long[] { ROW_1, ROW_2, ROW_3, ROW_4, ROW_5, ROW_6, ROW_7, ROW_8 };
    public static final long[] ROWS_REVERSED = new long[] { ROW_8, ROW_7, ROW_6, ROW_5, ROW_4, ROW_3, ROW_2, ROW_1 };
    public static final char[][] PIECE_CHARS = new char[][] { { '♚', '♛', '♞', '♝', '♜', '♟', ' ' },
            { '♔', '♕', '♘', '♗', '♖', '♙', '#' } }; // All empty squares will be white, # should never print
    public static final int[] COLORS = new int[] { WHITE, BLACK };
    public static final long[][] DEFAULT_PIECEBOARDS = new long[][] {
            { 16l, 8l, 66l, 36l, 129l, 65280l, 281474976645120l },
            { 576460752303423488l, 1152921504606846976l, 4755801206503243776l, 2594073385365405696l,
                    -9151314442816847872l,
                    71776119061217280l, 0l }
    };
    int turn;
    long[][] pieceBoards;
    long[] combinedBoards;

    public Chess() {
        this.turn = WHITE;
        this.pieceBoards = new long[COLORS.length][PIECES.length];
        pieceBoards = new long[COLORS.length][PIECES.length];
        combinedBoards = new long[COLORS.length];
        for (int color : COLORS) {
            for (int piece : PIECES) {
                pieceBoards[color][piece] = DEFAULT_PIECEBOARDS[color][piece];
                if (piece != EMPTY) {
                    combinedBoards[color] |= pieceBoards[color][piece];
                }
            }
        }
    }

    public void switchTurn() {
        this.turn = (this.turn == WHITE) ? BLACK : WHITE;
    }

    public void print() {
        boolean flag = false;
        System.out.print("\r\n╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻\r\n│");
        for (long row : ROWS_REVERSED) {
            if (flag) {
                System.out.print("\r\n│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│\r\n│");
            }
            flag = true;
            foundPiece: for (long file : FILES) {
                long square = row & file;
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if ((pieceBoards[color][piece] & square) == square) {
                            System.out.print(" " + PIECE_CHARS[color][piece] + " │");
                            continue foundPiece;
                        }
                    }
                }
            }
        }
        System.out.println("\r\n╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹");
    }

    public void move(long start, long end) {
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = ((combinedBoards[WHITE] & start) == start) ? WHITE : BLACK;
        int endColor = ((combinedBoards[WHITE] & end) == end) ? WHITE : BLACK;
        remove(start, startColor, startPiece);
        remove(end, endColor, endPiece);
        add(start, WHITE, EMPTY);
        add(end, startColor, startPiece);
    }

    public void move(String start, String end) {
        move(create(start), create(end));
    }

    public void swap(long start, long end) {
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = ((combinedBoards[WHITE] & start) == start) ? WHITE : BLACK;
        int endColor = ((combinedBoards[WHITE] & end) == end) ? WHITE : BLACK;
        remove(start, startColor, startPiece);
        remove(end, endColor, endPiece);
        add(start, endColor, endPiece);
        add(end, startColor, startPiece);
    }

    public void swap(String start, String end) {
        swap(create(start), create(end));
    }

    public void add(long square, int color, int piece) {
        pieceBoards[color][piece] |= square;
        if (piece != EMPTY) {
            combinedBoards[color] |= square;
        }
    }

    public void remove(long square, int color, int piece) {
        pieceBoards[color][piece] &= ~square;
        if (piece != EMPTY) {
            combinedBoards[color] &= ~square;
        }
    }

    public int getPiece(long square) {
        for (int color : COLORS) {
            for (int piece : PIECES) {
                if ((pieceBoards[color][piece] & square) == square) {
                    return piece;
                }
            }
        }
        return -1; // This shouldn't occur
    }

    // Long Board Methods

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
        return FILES[x] & ROWS[y];
    }

    public static long create(String square) {
        return FILES[square.charAt(0) - 'a'] & ROWS[square.charAt(1) - '1'];
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