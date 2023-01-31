public class tester {

    public static void main(String[] args) {
        long temp = Chess.create(1,1);
        temp = Chess.pushUp(temp,-1);
        Chess.print(temp);
    }
}
