package sample;

public class testing{
    public static void main(String args []){
        int twenty = 20;
        System.out.println(trailingZeroes(twenty));
    }
    public static int trailingZeroes(int n) {
        int r = 0;
        while (n > 0) {
            n /= 5;
            System.out.println("n is currently " + n);
            r += n;
            System.out.println("r is currently " + r);
        }
        return r;
    }
}
