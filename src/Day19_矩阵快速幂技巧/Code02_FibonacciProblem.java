package Day19_矩阵快速幂技巧;

/**
 * @Author : LiuYan
 * @create 2021/4/27 18:13
 *
 * 求斐波那契数列矩阵乘法的方法
 *   1）斐波那契数列的线性求解（O(N)）的方式非常好理解
 *   2）同时利用线性代数，也可以改写出另一种表示
 *    | F(N) , F(N-1) | = | F(2), F(1) |  *  某个二阶矩阵的N-2次方
 *   3）求出这个二阶矩阵，进而最快求出这个二阶矩阵的N-2次方
 *
 * 类似斐波那契数列的递归优化
 *   如果某个递归，除了初始项之外，具有如下的形式
 *   F(N) = C1 * F(N) + C2 * F(N-1) + … + Ck * F(N-k) ( C1…Ck 和k都是常数)
 *   并且这个递归的表达式是严格的、不随条件转移的
 *   那么都存在类似斐波那契数列的优化，时间复杂度都能优化成O(logN)
 *    | F(N) , F(N-1),.., F(N-K+1 ) | = |F(K),.., F(2), F(1) |  *  某个K阶矩阵的N-2次方
 *
 *
 * 斐波那契数列矩阵乘法方式的实现
 *
 *
 */
public class Code02_FibonacciProblem {
    // 斐波那契数列
    // 1 1 2 3 5
    public static int f1(int n) {
        if (n < 1) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        } else {
            return f1(n - 1) + f1(n - 2);
        }
    }
    
    public static int f2(int n) {
        if (n < 1) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        } else {
            int pre1 = 1;
            int pre2 = 1;
            int tmp = 0;
            for (int i = 2; i < n; i++) {
                tmp = pre1 + pre2;
                pre1 = pre2;
                pre2 = tmp;
            }
            return tmp;
        }
    }
    // O(logN)
    public static int f3(int n) {
        int[][] base = { // 单位矩阵
                {1, 1},
                {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0]; // 斐波那契的第n项
    }
    // 10^75 = 10^(2^6) * 10^(2^3) * 10^(2^1) *  10^(2^0)   75=1001011
    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        } // 矩阵中的1
        int[][] tmp = m; // 矩阵1次方
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, tmp);
            }
            tmp = muliMatrix(tmp, tmp);
        }
        return res;
    }
    public static int[][] muliMatrix(int[][] m1, int[][] m2) {
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

    // 第一年1只成熟母牛，往后每年：
    // 每只成熟母牛生一只母牛；新出生的在第三年后成熟；母牛不死
    // F(n) = F(n-1)+F(N-3) 3年前的牛会生小牛，去年的牛还存在
    public static int c1(int n) {
        if (n < 1) {
            return 0;
        } else if (n == 1 || n == 2 || n == 3) {
            return n;
        } else {
            return c1(n - 1) + c1(n - 3);
        }
    }
    public static int c2(int n) {
        if (n < 1) {
            return 0;
        } else if (n == 1 || n == 2 || n == 3) {
            return n;
        } else {
            int pre1 = 1;
            int pre2 = 2;
            int pre3 = 3;
            int tmp = 0;
            for (int i = 3; i < n; i++) {
                tmp = pre1 + pre3;
                pre1 = pre2;
                pre2 = pre3;
                pre3 = tmp;
            }
            return tmp;
        }
    }
    public static int c3(int n) {
        int[][] base = {
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}};
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + 1 * res[2][0];
    }

    public static void main(String[] args) {
        int n = 19;
        // 斐波那契数列三种算法
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println(f3(n));
        System.out.println("===");

//        System.out.println(s1(n));
//        System.out.println(s2(n));
//        System.out.println(s3(n));
//        System.out.println("===");

        System.out.println(c1(n));
        System.out.println(c2(n));
        System.out.println(c3(n));
        System.out.println("===");
    }

}
