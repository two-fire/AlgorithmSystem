package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/10 16:45
 * 给定两个长度都为N的数组weights和values，
 * weights[i]和values[i]分别代表 i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，
 * 你装的物品不能超过这个重量。
 * 返回你能装下最多的价值是多少?【不可分】
 *
 * 尝试模型：
 *  从左往右尝试模型
 *     [0 1 2] 0号要还是不要，1号要还是不要，2号要还是不要。路线是一个二叉树
 */
public class Code03_Knapsack {
    public static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return f(0, w, v, bag);
    }

    // 来到index处，背包还剩rest
    private static int f(int index, int[] w, int[] v, int rest) {
        if (rest < 0) { // 上游处理
            return -1;
        }
        if (index == w.length) {
            return 0;
        }
        // 不选择index
        int p1 = f(index + 1, w, v, rest);
        // 选择index
        int p2 = 0;
        int next =  f(index + 1, w, v, rest - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }

    // 状态转移
    private static int dp(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;
        int[][] dp = new int[N][bag + 1];
        // 从下往上
        for (int i = N - 2; i >= 0; i--) {
            for (int j = 0; j <= bag; j++) {
                // 不选择index
                int p1 = dp[i + 1][j];
                // 选择index
                int p2 = 0;
                int next = j - w[i] < 0 ? -1 : dp[i + 1][j - w[i]];
                if (next != -1) {
                    p2 = v[i] + next;
                }
                dp[i][j] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
