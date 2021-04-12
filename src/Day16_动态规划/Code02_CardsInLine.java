package Day16_动态规划;

import com.sun.org.apache.regexp.internal.RE;

/**
 * @Author : LiuYan
 * @create 2021/4/10 10:27
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数。
 * [50, 100, 20, 10] 先手先拿10，后手只能拿50或者20。先手再拿100。先手赢。
 *
 * 尝试！！！！
 * 先手函数：
 *  只剩一张牌，拿走。
 *  不止一张：max 拿走左牌，arr[l] + 后手函数 ； 拿走右牌，arr[r] + 后手函数
 * 后手函数：
 *  剩一张牌，获得0。
 *  不止一张：min 调用先手函数，在现有的里面挑最好
 *
 * 状态转移：
 *  L == R，dpf的对角线位置就是arr[L], dpg的对角线位置就是0
 *  假设：arr[0..4]
 *  dpf[0][4]：max(dpg[1][4], dp[0][3])
 *  dpg[0][4]：min(dpf[1][4], dpf[0][3])
 */
public class Code02_CardsInLine {
    // 暴力递归
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return Math.max(f(arr, 0, arr.length - 1), g(arr, 0, arr.length - 1));
    }
    // 先手函数 arr[L..R] 返回第一个人能拿的最大值
    private static int f(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int p1 = arr[L] + g(arr, L + 1, R);
        int p2 = arr[R] + g(arr, L, R - 1);
        return Math.max(p1, p2);
    }
    // 后手函数 arr[L..R] 返回第二个人能拿的最大值
    private static int g(int[] arr, int L, int R) {
        if (L == R) { // 只有一张，被先手拿了
            return 0;
        }
        // 第一人拿了左
        int p1 = f(arr, L + 1, R);
        // 第一人拿了右
        int p2 = f(arr, L, R - 1);
        return Math.min(p1, p2);
    }

    // 傻缓存
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // dpf[i][j]：先手arr[i..j]，分数是多少
        int[][] dpf = new int[N][N];
        // dpg[i][j]：后手arr[i..j]，分数是多少
        int[][] dpg = new int[N][N];
        for (int i = 0; i <N; i++) {
            for (int j = 0; j < N; j++) {
                dpf[i][j] = -1;
                dpg[i][j] = -1;
            }
        }
        return Math.max(f2(arr, 0, arr.length - 1, dpf, dpg), g2(arr, 0, arr.length - 1, dpf, dpg));
    }
    // 先手函数 arr[L..R] 返回第一个人能拿的最大值
    private static int f2(int[] arr, int L, int R, int[][] dpf, int[][]dpg) {
        if (dpf[L][R] != -1) {
            return dpf[L][R];
        }
        int ans = 0;
        if (L == R) {
            ans = arr[L];
        } else {
            int p1 = arr[L] + g2(arr, L + 1, R, dpf, dpg);
            int p2 = arr[R] + g2(arr, L, R - 1, dpf, dpg);
            ans = Math.max(p1, p2);
        }
        dpf[L][R] = ans;
        return ans;
    }
    // 后手函数 arr[L..R] 返回第二个人能拿的最大值
    private static int g2(int[] arr, int L, int R, int[][] dpf, int[][]dpg) {
        if (dpg[L][R] != -1) {
            return dpg[L][R];
        }
        int ans = 0; // 只有一张，被先手拿了
        if (L != R) {
            // 第一人拿了左
            int p1 = f2(arr, L + 1, R, dpf, dpg);
            // 第一人拿了右
            int p2 = f2(arr, L, R - 1, dpf, dpg);
            ans = Math.min(p1, p2);
        }
        dpg[L][R] = ans;
        return ans;
    }

    // 动态规划 状态转移
    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // dpf[i][j]：先手arr[i..j]，分数是多少
        int[][] dpf = new int[N][N];
        // dpg[i][j]：后手arr[i..j]，分数是多少
        int[][] dpg = new int[N][N];
        // 对角线赋值
        for (int i = 0; i < N; i++) {
            dpf[i][i] = arr[i];
        }
        // 按对角线方向，从中间往右上填写
        for (int i = 1; i < N; i++) {
           int L = 0;
           int R = i;
           while (R < N) {
               dpf[L][R] = Math.max(arr[R] + dpg[L][R - 1], arr[L] + dpg[L + 1][R]);
               dpg[L][R] = Math.min(dpf[L][R - 1], dpf[L + 1][R]);
               L++;
               R++;
           }
        }
        return Math.max(dpf[0][N - 1], dpg[0][N -1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

    }
}
