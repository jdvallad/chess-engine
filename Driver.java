public class Driver {
    public static void main(String[] args) {
        long temp = 1l;
        temp = temp << 1l;
        print(temp);
    }

    public static void print(long input) {
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.print(" " + (input & 1l));
            input = input >> 1;
        }
        System.out.println();

    }
}