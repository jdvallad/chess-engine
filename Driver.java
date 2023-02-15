
public class Driver {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < Chess.VERTICAL_OFFSETS.length; i++) {
            if (i < 8) {
                Chess.print(~Long.reverse(Chess.VERTICAL_OFFSETS[i]));
            } else {
                Chess.print(~Chess.VERTICAL_OFFSETS[i]);
            }
        }
    }
}
