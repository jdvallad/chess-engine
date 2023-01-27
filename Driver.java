public class Driver {
    public static void main(String[] args) throws Exception {
        long temp = create("c3");
        print(temp);

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

    public static long pushLeft(long input, long shift) {
        if (shift > 63) {
            return 0l;
        }
        if (shift < 0) {
            return pushRight(input,-shift);
        }
        return (input >>> shift);
    }

    public static long pushRight(long input, long shift) {
        if (shift > 63) {
            return 0l;
        }
        if(shift < 0){
            return pushLeft(input, -shift);
        }
        return (input << shift);
    }

    public static long pushUp(long input, long shift){
        return pushRight(input, 8l * shift);
    }

    public static long pushDown(long input, long shift){
        return pushLeft(input, 8l * shift);
    }
    
    public static long push(long input, long rightShift, long upShift){
        input = pushRight(input, rightShift);
        input = pushUp(input, upShift);
        return input;
    }

    public static long create(long x, long y){
        long output = 1l;
        output = pushRight(output,x);
        output = pushUp(output, y);
        return output;
    }

    public static long create(String square){
        long x = square.toLowerCase().charAt(0) - 'a';
        long y = square.toLowerCase().charAt(1) - '1';
        return create(x,y);
    }
}