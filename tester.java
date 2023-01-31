public class tester {

    public static void main(String[] args) {
        long temp = 63;
        long output = 0;
        long push = Chess.pushRight(1l, 6);
        while (push != 0) {
            output |= push & temp;
            push = Chess.pushLeft(push, 1);
        }
        output = Chess.pushRight(output, 6);

        Chess.print(temp);
        Chess.print(output);
    }
}
