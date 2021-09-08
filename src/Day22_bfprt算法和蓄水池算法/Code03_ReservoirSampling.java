package Day22_bfprt算法和蓄水池算法;

/**
 * 蓄水池问题，取小球，每个小球取出来的概率相等。以 10/i 的概率确定是否取数
 * 比如 100号，就10/100概率取
 */
public class Code03_ReservoirSampling {
    public static void main(String[] args) {
        System.out.println("begin!");
        int test = 100000;
        int[] count = new int[1730];
        for (int i = 0; i < test; i++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int num = 1; num <= 1729; num++) { // 球的编号1~1729
                if (num <= 10) { // 一定取
                    bag[bagi++] = num;
                } else {
                    if (random(num) <= 10) { // 一定要把num球入袋子
                        bagi = (int)(Math.random() * 10);
                        bag[bagi] = num;
                    }
                }
            }
            for (int num : bag) {
                count[num]++;
            }
        }
        for (int i = 1; i <= 1729; i++) {
            System.out.println(count[i]);
        }
    }

    private static int random(int i) {
        return (int)(Math.random() * i) + 1;
    }
}
