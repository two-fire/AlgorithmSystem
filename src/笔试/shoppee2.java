package 笔试;

public class shoppee2 {
    public int padovanSequence(int n) {
        if (n <= 3) {
            return 1;
        }
        int a = 1;
        int b = 1;
        int c = 1;
        int d = 2;
        for (int i = 4; i <= n; i++) {
            d = a + b;
            a = b;
            b = c;
            c = d;
        }
        return c;
    }
}
