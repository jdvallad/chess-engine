public class tester {

    public static int NE_OFFSET = 5;
    public static int NW_OFFSET = 261;
    public static int SE_OFFSET = 133;
    public static int SW_OFFSET = 389;

    public static int N_OFFSET = 1;
    public static int S_OFFSET = 129;
    public static int E_OFFSET = 4;
    public static int W_OFFSET = 260;

    public static void main(String[] args) {
        System.out.println(ChessNew.encodeDirection(1, 1));
        System.out.println(ChessNew.encodeDirection(-1, 1));
        System.out.println(ChessNew.encodeDirection(1, -1));
        System.out.println(ChessNew.encodeDirection(-1, -1));

        System.out.println(ChessNew.encodeDirection(0, 1));
        System.out.println(ChessNew.encodeDirection(0, -1));
        System.out.println(ChessNew.encodeDirection(1, 0));
        System.out.println(ChessNew.encodeDirection(-1, 0));
    }
}