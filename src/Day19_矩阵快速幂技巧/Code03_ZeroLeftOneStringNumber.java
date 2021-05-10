package Day19_矩阵快速幂技巧;

/**
 * @Author : LiuYan
 * @create 2021/4/27 18:13
 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 * 如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
 * 返回有多少达标的字符串
 *
 * n=1 0 1 1个
 * n=2 00 01 10 11 2个
 * n=3 000 001 010 011 100 101 110 3个
 *
 * 方法一：观察法
 * 1 2 3 5 8 13 斐波那契数列f(n) = f(n-1) + f(n-2)
 * 方法二：第一个数只能是1
 *      1）第二位是1。那么就有f(i-1)种情况
 *      2）第二位是0。后一位必是1，那么就有f(i-2)种情况
 *      所以：f(n) = f(n-1) + f(n-2)
 */
public class Code03_ZeroLeftOneStringNumber {
    //f(n) = f(n-1) + f(n-2)
    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {
                {1, 1},
                {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }
    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = mulMatrix(res, tmp);
            }
            tmp = mulMatrix(tmp, tmp);
        }
        return res;
    }
    public static int[][] mulMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                for (int k = 0; k < m1[0].length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    public static int getNum2(int n) {
        if (n < 1) {
            return 0;
        } else if (n == 1 || n ==2) {
            return n;
        } else {
            int pre1 = 1;
            int pre2 = 2;
            int tmp = 0;
            for (int i = 2; i < n; i++) {
                tmp = pre1 + pre2;
                pre1 = pre2;
                pre2 = tmp;
            }
            return tmp;
        }
    }

    public static int getNum3(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }


    public static int process(int i, int n) {
        if (i == n) {
            return 1;
        }
        if (i == n - 1) {
            return 2;
        }
        // 后一位1，从i + 1位开始考虑；后一位0，从i + 2位开始考虑
        return process(i + 1, n) + process(i + 2, n);
    }

    public static void main(String[] args) {
        for (int i = 0; i != 20; i++) {
            System.out.println(getNum1(i));
            System.out.println(getNum2(i));
            System.out.println(getNum3(i));
            System.out.println("===================");
        }

    }
}
