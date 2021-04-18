package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/18 16:37
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。
 * n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 */
public class Code20_NQueens {
    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process(n, 0, record);

    }

    // 在第i行。record[x] = y 之前第x行的皇后在y列上
    private static int process(int n, int i, int[] record) {
        if (i == n) {
            return 1;
        }
        int ans = 0;
        // i行皇后放在j列
        for (int j = 0; j < n; j++) {
            if (isValid(i, j, record)) {
                record[i] = j;
                ans += process(n, i + 1, record);
            }
        }
        return ans;
    }

    // 第i行j列能不能摆放皇后
    private static boolean isValid(int i, int j, int[] record) {
        for (int k = 0; k < i; k++) {
            if (record[k] == j || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }

    // 请不要超过32皇后问题
    public static int num2(int n) {
         if (n < 1) {
             return 0;
         }
         return process2((1 << n) - 1, 0, 0, 0);
    }

    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {
            return 1;
        }
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim)); //1是可以尝试放皇后的位置
        int mostRightOne = 0;// 提取最右的1
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos -= mostRightOne;
            res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 10;
        long start = System.currentTimeMillis();
        System.out.println(num1(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num2(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
    }
}
