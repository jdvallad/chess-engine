import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessNew {
    public static final int QUEEN = 0;
    public static final int KNIGHT = 1;
    public static final int BISHOP = 2;
    public static final int ROOK = 3;
    public static final int KING = 4;
    public static final int PAWN = 5;
    public static final int EMPTY = 6;
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int QUEENSIDE = 0;
    public static final int KINGSIDE = 1;
    public static final long A_FILE = 72340172838076673l;
    public static final long B_FILE = 144680345676153346l;
    public static final long C_FILE = 289360691352306692l;
    public static final long D_FILE = 578721382704613384l;
    public static final long E_FILE = 1157442765409226768l;
    public static final long F_FILE = 2314885530818453536l;
    public static final long G_FILE = 4629771061636907072l;
    public static final long H_FILE = -9187201950435737472l;
    public static final long RANK_1 = 255l;
    public static final long RANK_2 = 65280l;
    public static final long RANK_3 = 16711680l;
    public static final long RANK_4 = 4278190080l;
    public static final long RANK_5 = 1095216660480l;
    public static final long RANK_6 = 280375465082880l;
    public static final long RANK_7 = 71776119061217280l;
    public static final long RANK_8 = -72057594037927936l;
    public static final byte FLAG_STANDARD = 0;
    public static final byte FLAG_CASTLE = 1;
    public static final byte FLAG_EN_PASSANT = 2;
    public static final byte FLAG_PROMOTION = 3;
    public static final int NEE_OFFSET = 10;
    public static final int NNE_OFFSET = 17;
    public static final int NNW_OFFSET = 15;
    public static final int NWW_OFFSET = 6;
    public static final int SWW_OFFSET = -10;
    public static final int SSW_OFFSET = -17;
    public static final int SSE_OFFSET = -15;
    public static final int SEE_OFFSET = -6;
    public static final int[] KNIGHT_OFFSETS = new int[] { NEE_OFFSET, NNE_OFFSET, NNW_OFFSET, NWW_OFFSET, SWW_OFFSET,
            SSW_OFFSET, SSE_OFFSET, SEE_OFFSET };
    public static final int[] PROMOTION_PIECES = { QUEEN, KNIGHT, BISHOP, ROOK };
    public static final byte[] FLAGS = new byte[] { FLAG_STANDARD, FLAG_CASTLE, FLAG_EN_PASSANT, FLAG_PROMOTION };
    public static final int[] PIECES = new int[] { QUEEN, KNIGHT, BISHOP, ROOK, KING, PAWN, EMPTY };
    public static final long[] FILES = new long[] { A_FILE, B_FILE, C_FILE, D_FILE, E_FILE, F_FILE, G_FILE, H_FILE };
    public static final long[] FILES_REVERSED = new long[] { H_FILE, G_FILE, F_FILE, E_FILE, D_FILE, C_FILE, B_FILE,
            A_FILE };
    public static final long[] RANKS = new long[] { RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8 };
    public static final long[] RANKS_REVERSED = new long[] { RANK_8, RANK_7, RANK_6, RANK_5, RANK_4, RANK_3, RANK_2,
            RANK_1 };
    public static final char[][] PIECE_CHARS_UNICODE = new char[][] { { '♛', '♞', '♝', '♜', '♚', '♟', ' ' },
            { '♕', '♘', '♗', '♖', '♔', '♙', '⚠' } }; // All empty squares will be white, ⚠ should never print
    public static final char[][] PIECE_CHARS_ASCII = new char[][] { { 'Q', 'N', 'B', 'R', 'K', 'P', ' ' },
            { 'q', 'n', 'b', 'r', 'k', 'p', ' ' } }; // All empty squares will be white, ⚠ should never print
    public static final int[] COLORS = new int[] { WHITE, BLACK };
    public static final int[] COLORS_REVERSED = new int[] { BLACK, WHITE };
    public static final int[] SIDES = new int[] { QUEENSIDE, KINGSIDE };
    public static final int[] SIDES_REVERSED = new int[] { KINGSIDE, QUEENSIDE };
    public static final long[][] DEFAULT_PIECEBOARDS = new long[][] {
            { 8l, 66l, 36l, 129l, 16l, 65280l, 281474976645120l },
            { 1152921504606846976l, 4755801206503243776l, 2594073385365405696l,
                    -9151314442816847872l, 576460752303423488l,
                    71776119061217280l, 0l }
    };
    public static final long[] ROOK_STARTING_FILES = new long[] { A_FILE, H_FILE };
    // Object instance variables
    int turn;
    long[][] pieceBoards;
    long[] combinedBoards;
    boolean[][] castleRights;
    long enPassantSquare;
    int halfMoveCount;
    int fullMoveCount;
    List<Short> legalMoves;
    List<Long> reversibleMoves;
    List<Short>[] pseudoLegalMoves;
    List<String> shortenedFenList;
    boolean inCheck;
    boolean gameOver;

    @SuppressWarnings("unchecked")

    public ChessNew() {
        turn = WHITE;
        inCheck = false;
        gameOver = false;
        enPassantSquare = 0l;
        halfMoveCount = 0;
        fullMoveCount = 1;
        reversibleMoves = new ArrayList<>();
        legalMoves = new ArrayList<>();
        shortenedFenList = new ArrayList<>();
        pseudoLegalMoves = (List<Short>[]) new List[COLORS.length];
        pieceBoards = new long[COLORS.length][PIECES.length];
        combinedBoards = new long[COLORS.length];
        castleRights = new boolean[COLORS.length][SIDES.length];
        for (int color : COLORS) {
            pseudoLegalMoves[color] = new ArrayList<>();
            for (int piece : PIECES) {
                pieceBoards[color][piece] = DEFAULT_PIECEBOARDS[color][piece];
                if (piece != EMPTY) {
                    combinedBoards[color] |= pieceBoards[color][piece];
                }
            }
            for (int side : SIDES) {
                castleRights[color][side] = true;
            }
        }
        updateLegalMoves();
    }

    public void print() {
        boolean flag = false;
        System.out.print("\r\n╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻\r\n│");
        for (long rank : RANKS_REVERSED) {
            if (flag) {
                System.out.print("\r\n│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│\r\n│");
            }
            flag = true;
            foundPiece: for (long file : FILES) {
                long square = rank & file;
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if ((pieceBoards[color][piece] & square) == square) {
                            System.out.print(" " + PIECE_CHARS_UNICODE[color][piece] + " │");
                            continue foundPiece;
                        }
                    }
                }
            }
        }
        System.out.println("\r\n╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹");
    }

    public void printLegalMoves() {
        System.out.print("[");
        for (short move : legalMoves) {
            long startingSquare = getStartingSquare(move);
            long endingSquare = getEndingSquare(move);
            char startingFile = (char) ('a' + getFileIndex(startingSquare));
            char startingRank = (char) ('1' + getRankIndex(startingSquare));
            char endingFile = (char) ('a' + getFileIndex(endingSquare));
            char endingRank = (char) ('1' + getRankIndex(endingSquare));
            System.out.print("" + startingFile + startingRank + endingFile + endingRank + ", ");
        }
        System.out.println("]");
    }

    public void printSimple() {
        boolean flag = false;
        System.out.print("\r\n|---|---|---|---|---|---|---|---|\r\n|");
        for (long rank : RANKS_REVERSED) {
            if (flag) {
                System.out.print("\r\n|---|---|---|---|---|---|---|---|\r\n|");
            }
            flag = true;
            foundPiece: for (long file : FILES) {
                long square = rank & file;
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if ((pieceBoards[color][piece] & square) == square) {
                            System.out.print(" " + PIECE_CHARS_ASCII[color][piece] + " |");
                            continue foundPiece;
                        }
                    }
                }
            }
        }
        System.out.println("\r\n|---|---|---|---|---|---|---|---|\r\n");

    }

    public void makeMove(short move) {
        if (!legalMoves.contains(move)) {
            return;
        }
        makeShallowMove(move);
        updateLegalMoves();
    }

    public void makeShallowMove(short move) {
        reversibleMoves.add(encodeReversibleMove(move));
        switch (getFlag(move)) {
            case FLAG_PROMOTION:
                makePromotionMove(move);
                break;
            case FLAG_CASTLE:
                makeCastleMove(move);
                break;
            case FLAG_EN_PASSANT:
                makeEnPassantMove(move);
                break;
            case FLAG_STANDARD:
                makeStandardMove(move);
                break;
            default:
                makeStandardMove(move);
                break;
        }
        if (turn == BLACK) {
            fullMoveCount += 1;
        }
        halfMoveCount += 1;
        this.turn ^= 1;
        shortenedFenList.add(getShortenedFen());
    }

    public long encodeReversibleMove(short move) {
        // Info needed to encode reversible move:
        // starting and ending square, starting and ending piece,
        // starting and ending piece color, enPassantSquare
        // castleRights, move flag, and half move count.
        // starting piece and starting color can be inferred from board state
        // so they do not need to be encoded. But they will just for ease.

        long startingSquareLong = getStartingSquare(move);
        int startingSquareInt = Long.numberOfTrailingZeros(startingSquareLong); // 6 bits
        int startingPiece = getPiece(startingSquareLong); // 3 bits
        int startingColor = getColor(startingSquareLong); // 1 bit
        long endingSquareLong = getEndingSquare(move);
        int endingSquareInt = Long.numberOfTrailingZeros(endingSquareLong); // 6 bits
        int endingPiece = getPiece(endingSquareLong); // 3 bits
        int endingColor = getColor(endingSquareLong); // 1 bit
        int enPassantSquareOffset = Long.numberOfTrailingZeros(enPassantSquare); // 6 bits
        int flag = getFlag(move); // (2 bits)
        // halfMoveCount (32 bits)
        // castleRights (4 bits)
        // 64 bits, a long! (niceee)
        long output = 0;
        long push = pushRight(1l, 6 - 1);
        while (push != 0) {
            output |= push & startingSquareInt;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 3);
        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            output |= push & startingPiece;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 1);
        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            output |= push & startingColor;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 6);
        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            output |= push & endingSquareInt;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 3);
        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            output |= push & endingPiece;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 1);
        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            output |= push & endingColor;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 6);
        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            output |= push & enPassantSquareOffset;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 2);
        push = pushRight(1l, 2 - 1);
        while (push != 0) {
            output |= push & flag;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 32);
        push = pushRight(1l, 32 - 1);
        while (push != 0) {
            output |= push & halfMoveCount;
            push = pushLeft(push, 1);
        }

        for (int color : COLORS) {
            for (int side : SIDES) {
                output = pushRight(output, 1);
                push = pushRight(1l, 1 - 1);
                while (push != 0) {
                    output |= push & (castleRights[color][side] ? 1l : 0l);
                    push = pushLeft(push, 1);
                }
            }
        }

        return output;
    }

    public void simpleUndoReversibleMove() {
        // castleRights (4 bits)
        halfMoveCount = 0; // (32 bits)
        int flag = 0; // (2 bits)
        int enPassantSquareOffset = 0; // 6 bits
        int endingColor = 0; // 1 bit
        int endingPiece = 0; // 3 bits
        int endingSquareOffset = 0; // 6 bits
        int startingColor = 0; // 1 bit
        int startingPiece = 0; // 3 bits
        int startingSquareOffset = 0; // 6 bits
        long endingSquare = 0l;
        long startingSquare = 0l;
        long input = reversibleMoves.remove(reversibleMoves.size() - 1);
        long push = 0;
        for (int color : COLORS_REVERSED) {
            for (int side : SIDES_REVERSED) {
                push = pushRight(1l, 1 - 1);
                while (push != 0) {
                    castleRights[color][side] = (push & input) != 0;
                    push = pushLeft(push, 1);
                }
                input = pushLeft(input, 1);
            }
        }

        push = pushRight(1l, 32 - 1);
        while (push != 0) {
            halfMoveCount |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 32);

        push = pushRight(1l, 2 - 1);
        while (push != 0) {
            flag |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 2);

        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            enPassantSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        enPassantSquare = pushRight(1l, enPassantSquareOffset);
        input = pushLeft(input, 6);

        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            endingColor |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 1);

        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            endingPiece |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 3);

        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            endingSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        endingSquare = pushRight(1l, endingSquareOffset);
        input = pushLeft(input, 6);

        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            startingColor |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 1);

        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            startingPiece |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 3);

        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            startingSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        startingSquare = pushRight(1l, startingSquareOffset);
        input = pushLeft(input, 6);

        add(startingSquare, startingColor, startingPiece);
        add(endingSquare, endingColor, endingPiece);
        switch (flag) {
            case FLAG_CASTLE:
                long back = getBackRank(startingColor);
                if ((ROOK_STARTING_FILES[QUEENSIDE] & back) == endingSquare) {
                    remove(back & C_FILE);
                    remove(back & D_FILE);
                } else {
                    remove(back & F_FILE);
                    remove(back & G_FILE);
                }
                break;
            case FLAG_PROMOTION:
                break;
            case FLAG_EN_PASSANT:
                long captureSquare = RANKS[getRankIndex(startingSquare)] & FILES[getFileIndex(endingSquare)];
                remove(captureSquare);
                break;
            case FLAG_STANDARD:
                break;
        }
        turn ^= 1;
        shortenedFenList.remove(shortenedFenList.size() - 1);
        return;
    }

    public void extendedUndoReversibleMove() {
        simpleUndoReversibleMove();
        updateLegalMoves();
    }

    public void makePromotionMove(short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        byte promotion = getPromotion(move);
        int startColor = getColor(start);
        int endColor = getColor(end);
        add(start, startColor, promotion);
        move(start, end);
        for (int side : SIDES) {
            if (castleRights[endColor][side]
                    && (ROOK_STARTING_FILES[side] & getBackRank(endColor)) == end) {
                castleRights[endColor][side] = false;
            }
        }
        enPassantSquare = 0l;
        halfMoveCount = -1;
        return;
    }

    public long getBackRank(int color) {
        return (color == BLACK) ? RANK_8 : RANK_1;
    }

    public void makeCastleMove(short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        int startColor = getColor(start);
        int endColor = getColor(end);
        long mainStartSquare = start;
        long helperStartSquare = end;
        long mainRank = getBackRank(startColor);
        long helperRank = getBackRank(endColor);
        long mainEndSquare = 0;
        long helperEndSquare = 0;
        if (getFileIndex(end) - getFileIndex(start) > 0) {// kingside castle if castling to the right
            mainEndSquare = mainRank & G_FILE;
            helperEndSquare = helperRank & F_FILE;
        } else {
            mainEndSquare = mainRank & C_FILE;
            helperEndSquare = helperRank & D_FILE;
        }
        move(mainStartSquare, mainEndSquare);
        move(helperStartSquare, helperEndSquare);
        enPassantSquare = 0l;
        for (int side : SIDES) {
            castleRights[startColor][side] = false;
        }
        return;
    }

    public void makeEnPassantMove(short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        long captureSquare = ChessNew.pushDown(end, end - start > 0 ? 1 : -1);
        remove(captureSquare);
        move(start, end);
        enPassantSquare = 0l;
        halfMoveCount = -1;
    }

    public void makeStandardMove(Short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = getColor(start);
        int endColor = getColor(end);
        move(start, end);
        enPassantSquare = 0l;
        if (startPiece == PAWN || endPiece != EMPTY) { // pawn move or capture, reset halfMoveCount
            halfMoveCount = -1;
        }
        if (startPiece == PAWN) { // update enPassantSquare
            int rankDifference = getRankIndex(end) - getRankIndex(start);
            if (Math.abs(rankDifference) == 2) {
                enPassantSquare = pushUp(start, rankDifference / 2);
            }
        }
        for (int side : SIDES) { // update castling rights
            if (castleRights[startColor][side] // update if moving king
                    && startPiece == KING) {
                castleRights[startColor][side] = false;
                continue;
            }
            if (castleRights[startColor][side] // update if moving an unmoved rook
                    && (ROOK_STARTING_FILES[side] & getBackRank(startColor)) == start) {
                castleRights[startColor][side] = false;
                continue;
            }
            if (castleRights[endColor][side] // update if capturing an unmoved rook
                    && (ROOK_STARTING_FILES[side] & getBackRank(endColor)) == end) {
                castleRights[endColor][side] = false;
                continue;
            }
        }
    }

    public void updateLegalMoves() {
        if (gameOver) {
            return;
        }
        pseudoLegalMoves[0].clear();
        pseudoLegalMoves[1].clear();
        legalMoves.clear();
        inCheck = calculateInCheck();
        if (shortenedFenList.size() > 0
                && Collections.frequency(shortenedFenList, shortenedFenList.get(shortenedFenList.size() - 1)) == 5) {
            gameOver = true; // 5 fold repetition
            return;
        }
        if (halfMoveCount == 75) {
            gameOver = true;
            return;
        }
        updatePseudoLegalMoves();
        for (short move : pseudoLegalMoves[this.turn]) {
            if (getFlag(move) != FLAG_CASTLE) {
                this.makeShallowMove(move);
                turn ^= 1;
                if (!calculateInCheck()) {
                    legalMoves.add(move);
                }
                turn ^= 1;
                simpleUndoReversibleMove();
            } else {
                if (inCheck) {
                    continue;
                }
                int offset = (getEndingSquare(move) == ROOK_STARTING_FILES[QUEENSIDE]) ? 1 : -1;
                pieceBoards[turn][KING] = pushLeft(pieceBoards[turn][KING], offset);
                if (calculateInCheck()) {
                    pieceBoards[turn][KING] = pushRight(pieceBoards[turn][KING], offset);
                    continue;
                }
                pieceBoards[turn][KING] = pushLeft(pieceBoards[turn][KING], offset);
                if (calculateInCheck()) {
                    pieceBoards[turn][KING] = pushRight(pieceBoards[turn][KING], 2 * offset);
                    continue;
                }
                pieceBoards[turn][KING] = pushRight(pieceBoards[turn][KING], 2 * offset);
                this.makeShallowMove(move);
                turn ^= 1;
                if (!calculateInCheck()) {
                    legalMoves.add(move);
                }
                turn ^= 1;
                simpleUndoReversibleMove();
            }
        }
        if (legalMoves.size() == 0) {
            gameOver = true;
        }
        return;
    }

    public boolean calculateInCheck() {
        long king = pieceBoards[turn][KING];
        turn ^= 1; // switch turn
        updatePseudoLegalMoves();
        for (short move : pseudoLegalMoves[this.turn]) { // moves your opponent could make if it was their turn
            if (getEndingSquare(move) == king) { // any of your opponents move capture your king
                turn ^= 1;
                return true;
            }
        }
        turn ^= 1;
        return false;
    }

    public void updatePseudoLegalMoves() {
        addPseudoLegalPawnMoves();
        addPseudoLegalKnightMoves();
        // addPseudoLegalBishopMoves();
        // addPseudoLegalRookMoves();
        // addPseudoLegalQueenMoves();
        // addPseudoLegalKingMoves();
        // addPseudoLegalCastleMoves();
        return;
    }

    public void addPseudoLegalPawnMoves() {
        int offset = (turn == BLACK) ? -1 : 1;
        long startingPawns = pieceBoards[turn][PAWN] & ((turn == BLACK) ? RANK_7 : RANK_2); // all pawns on starting
                                                                                            // square
        long emptySquares = pieceBoards[WHITE][EMPTY];

        startingPawns = pushUp(startingPawns, offset) & emptySquares; // advance the pawns one square
        startingPawns = pushUp(startingPawns, offset) & emptySquares; // advance the pawns one square
        long endingSquare = 0l;
        long startingSquare = 0l;
        short move = 0;
        for (long file : FILES) { // this loop adds double move pawns
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = pushDown(endingSquare, 2 * offset);
                move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                pseudoLegalMoves[turn].add(move);
            }
        }

        startingPawns = pieceBoards[turn][PAWN];
        emptySquares = pieceBoards[WHITE][EMPTY];
        startingPawns = pushUp(startingPawns, offset) & emptySquares;
        for (long file : FILES) { // this loop adds single move pawns
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = pushDown(endingSquare, offset);
                if (endingSquare == getBackRank(turn ^ 1)) {
                    for (int piece : PROMOTION_PIECES) {
                        move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                        pseudoLegalMoves[turn].add(move);
                    }
                } else {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }

        startingPawns = pieceBoards[turn][PAWN];
        emptySquares = pieceBoards[WHITE][EMPTY];
        startingPawns = pushUp(startingPawns, offset);
        startingPawns = pushRight(startingPawns, 1) & combinedBoards[turn ^ 1];
        for (long file : FILES) { // this loop adds pawn captures towards the H-file
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = pushDown(endingSquare, offset);
                startingSquare = pushLeft(startingSquare, 1);
                if (endingSquare == getBackRank(turn ^ 1)) {
                    for (int piece : PROMOTION_PIECES) {
                        move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                        pseudoLegalMoves[turn].add(move);
                    }
                } else {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }

        startingPawns = pieceBoards[turn][PAWN];
        emptySquares = pieceBoards[WHITE][EMPTY];
        startingPawns = pushUp(startingPawns, offset);
        startingPawns = pushRight(startingPawns, -1) & combinedBoards[turn ^ 1];
        for (long file : FILES) { // this loop adds pawn captures towards the A-file
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = pushDown(endingSquare, offset);
                startingSquare = pushLeft(startingSquare, -1);
                if (endingSquare == getBackRank(turn ^ 1)) {
                    for (int piece : PROMOTION_PIECES) {
                        move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                        pseudoLegalMoves[turn].add(move);
                    }
                } else {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }
        return;
    }

    public void addPseudoLegalKnightMoves() {
        long knights = pieceBoards[turn][KNIGHT];
        long emptySquares = pieceBoards[WHITE][EMPTY];
        long destinationSquares = 0;
        for (int offset : KNIGHT_OFFSETS) {
            destinationSquares |= pushRightOff(knights, offset) & emptySquares;
        }
        long endingSquare;
        long startingSquare;
        short move;
        for (long file : FILES) {
            for (long rank : RANKS) {
                endingSquare = file & rank & destinationSquares;
                if (endingSquare == 0) {
                    continue;
                }
                for (int offset : KNIGHT_OFFSETS) {
                    startingSquare = pushRightOff(endingSquare, offset) & knights;
                    if (startingSquare != 0) {
                        move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                }
            }
        }
        return;
    }

    public void move(long start, long end) {
        int startPiece = getPiece(start);
        int startColor = getColor(start);
        remove(start);
        add(end, startColor, startPiece);
    }

    public void move(String start, String end) {
        move(create(start), create(end));
    }

    public void move(String move) {
        move(move.substring(0, 2), move.substring(2));
    }

    public void swap(long start, long end) {
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = getColor(start);
        int endColor = getColor(end);
        add(start, endColor, endPiece);
        add(end, startColor, startPiece);
    }

    public void swap(String start, String end) {
        swap(create(start), create(end));
    }

    public void add(long square, int color, int piece) {
        int priorPiece = getPiece(square);
        int priorColor = getColor(square);
        pieceBoards[priorColor][priorPiece] &= ~square; // remove prior piece from square
        if (priorPiece != EMPTY) {
            combinedBoards[priorColor] &= ~square; // remove prior piece from combinedBoard if its not EMPTY
        }
        pieceBoards[color][piece] |= square; // add new piece to square
        if (piece != EMPTY) {
            combinedBoards[color] |= square; // add new piece to combinedBoard if its not EMPTY
        }
    }

    public void remove(long square) {
        add(square, WHITE, EMPTY);
    }

    public int getPiece(long square) {
        int color = getColor(square);
        for (int piece : PIECES) {
            if ((pieceBoards[color][piece] & square) == square) {
                return piece;
            }
        }
        return -1; // This shouldn't occur
    }

    public int getColor(long square) {
        return (combinedBoards[BLACK] & square) == square ? BLACK : WHITE;
    }

    public short encodeMove(long start, long end, int promotion, int flag) {
        byte origin = (byte) Long.numberOfTrailingZeros(start);
        byte destination = (byte) Long.numberOfTrailingZeros(end);
        return (short) (((63 & origin) << 10) | ((63 & destination) << 4) | ((3 & promotion) << 2) | (3 & flag));
    }

    public long getStartingSquare(short move) {
        byte start = (byte) (63 & (move >>> 10));
        return 1l << start;
    }

    public long getEndingSquare(short move) {
        byte end = (byte) (63 & (move >>> 4));
        return 1l << end;
    }

    public byte getPromotion(short move) {
        return (byte) (3 & (move >>> 2));
    }

    public byte getFlag(short move) {
        return (byte) (3 & move);
    }

    public String getFen() {
        StringBuilder build = new StringBuilder("");
        int count = 0;
        for (long rank : RANKS_REVERSED) {
            for (long file : FILES) {
                int piece = getPiece(rank & file);
                int color = getColor(rank & file);
                if (piece == EMPTY) {
                    count++;
                    if (count == FILES.length) {
                        build.append(count);
                        count = 0;
                    }
                    continue;
                }
                if (count > 0) {
                    build.append(count);
                    count = 0;
                }
                build.append(PIECE_CHARS_ASCII[color][piece]);
            }
            build.append("/");
        }
        build.setLength(build.length() - 1);
        build.append(" ");
        if (turn == WHITE) {
            build.append("w ");
        } else {
            build.append("b ");
        }
        boolean anyCastle = false;
        for (int color : COLORS) {
            for (int side : SIDES_REVERSED) {
                if (castleRights[color][side]) {
                    anyCastle = true;
                    char temp = side == KINGSIDE ? 'k' : 'q';
                    if (color == WHITE) {
                        temp = Character.toUpperCase(temp);
                    }
                    build.append(temp);
                }
            }
        }
        if (!anyCastle) {
            build.append("-");
        }
        build.append(" ");
        if (enPassantSquare == 0l) {
            build.append("-");
        } else {
            build.append((char) (getFileIndex(enPassantSquare) + 'a'));
            build.append((char) (getRankIndex(enPassantSquare) + '1'));
        }
        build.append(" ");
        build.append(halfMoveCount);
        build.append(" ");
        build.append(fullMoveCount);
        return build.toString();
    }

    public String getShortenedFen() {
        StringBuilder build = new StringBuilder("");
        int count = 0;
        for (long rank : RANKS_REVERSED) {
            for (long file : FILES) {
                int piece = getPiece(rank & file);
                int color = getColor(rank & file);
                if (piece == EMPTY) {
                    count++;
                    if (count == FILES.length) {
                        build.append(count);
                        count = 0;
                    }
                    continue;
                }
                if (count > 0) {
                    build.append(count);
                    count = 0;
                }
                build.append(PIECE_CHARS_ASCII[color][piece]);
            }
            build.append("/");
        }
        build.setLength(build.length() - 1);
        build.append(" ");
        if (turn == WHITE) {
            build.append("w ");
        } else {
            build.append("b ");
        }
        boolean anyCastle = false;
        for (int color : COLORS) {
            for (int side : SIDES_REVERSED) {
                if (castleRights[color][side]) {
                    anyCastle = true;
                    char temp = side == KINGSIDE ? 'k' : 'q';
                    if (color == WHITE) {
                        temp = Character.toUpperCase(temp);
                    }
                    build.append(temp);
                }
            }
        }
        if (!anyCastle) {
            build.append("-");
        }
        build.append(" ");
        if (enPassantSquare == 0l) {
            build.append("-");
        } else {
            build.append((char) (getFileIndex(enPassantSquare) + 'a'));
            build.append((char) (getRankIndex(enPassantSquare) + '1'));
        }
        return build.toString();
    }

    public void setFromFen(String fen) {
        int fileIndex = 0;
        int stringIndex = 0;
        char pointer = fen.charAt(stringIndex);
        for (long rank : RANKS_REVERSED) {
            fileIndex = 0;
            while (pointer != '/' && pointer != ' ') {
                if ('0' <= pointer && pointer <= '9') {
                    for (int i = 0; i < pointer - '0'; i++) {
                        add(FILES[fileIndex + i] & rank, WHITE, EMPTY);
                    }
                    fileIndex += pointer - '0';
                    stringIndex++;
                    pointer = fen.charAt(stringIndex);
                    continue;
                }
                int piece = getPieceFromChar(pointer);
                int color = getColorFromChar(pointer);
                add(FILES[fileIndex] & rank, color, piece);
                fileIndex++;
                stringIndex++;
                pointer = fen.charAt(stringIndex);
            }
            stringIndex++;
            pointer = fen.charAt(stringIndex);
        }
        stringIndex++;
        pointer = fen.charAt(stringIndex);
        turn = (pointer == 'w') ? WHITE : BLACK;
        stringIndex++;
        pointer = fen.charAt(stringIndex);
        if (pointer == '-') {
            for (int color : COLORS) {
                for (int side : SIDES) {
                    castleRights[color][side] = false;
                }
            }
        } else {
            while (pointer != ' ') {
                int color = getColorFromChar(pointer);
                int side = (Character.toUpperCase(pointer) == 'Q') ? QUEENSIDE : KINGSIDE;
                castleRights[color][side] = true;
                stringIndex++;
                pointer = fen.charAt(stringIndex);
            }
        }
        stringIndex++;
        pointer = fen.charAt(stringIndex);
        if (pointer == '-') {
            enPassantSquare = 0l;
        } else {
            long file = FILES[pointer - 'a'];
            stringIndex++;
            pointer = fen.charAt(stringIndex);
            long rank = RANKS[pointer - '1'];
            enPassantSquare = file & rank;
        }
        stringIndex += 2;
        pointer = fen.charAt(stringIndex);
        String temp = "";
        while (pointer != ' ') {
            temp += pointer;
            stringIndex++;
            pointer = fen.charAt(stringIndex);
        }
        halfMoveCount = Integer.parseInt(temp);
        stringIndex++;
        temp = fen.substring(stringIndex);
        fullMoveCount = Integer.parseInt(temp);
        return;
    }

    public int getPieceFromChar(char c) {
        if (c == ' ') {
            return EMPTY;
        }
        switch (Character.toUpperCase(c)) {
            case 'Q':
                return QUEEN;
            case 'R':
                return ROOK;
            case 'B':
                return BISHOP;
            case 'N':
                return KNIGHT;
            case 'K':
                return KING;
            case 'P':
                return PAWN;
            default:
                return -1;
        }
    }

    public int getSideFromChars(char right, char left) {
        int colorLeft = getColor(left);
        int colorRight = getColor(right);
        if (colorLeft == colorRight) {
            return QUEENSIDE;
        }
        return KINGSIDE;
    }

    public int getColorFromChar(char c) {
        if (c == ' ') {
            return WHITE;
        }
        return Character.isUpperCase(c) ? WHITE : BLACK;
    }

    // Long Board Methods
    public static int getRankIndex(long square) {
        int zeroes = Long.numberOfTrailingZeros(square);
        return (zeroes / 8);
    }

    public static int getFileIndex(long square) {
        int zeroes = Long.numberOfTrailingZeros(square);
        return (zeroes % 8);
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
        return FILES[x] & RANKS[y];
    }

    public static long create(String square) {
        return FILES[square.charAt(0) - 'a'] & RANKS[square.charAt(1) - '1'];
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