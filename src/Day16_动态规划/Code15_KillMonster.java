package Day16_动态规划;

import com.sun.org.apache.regexp.internal.RE;

/**
 * @Author : LiuYan
 * @create 2021/4/15 20:06
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量
 * 到底流失多少？每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 *
 */
public class Code15_KillMonster {
    public static double killP1(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        return (double) process(N, M, K) / Math.pow(M + 1, K);
    }

    // 怪兽还剩restN滴血，restK次打击
    private static long process(int restN, int M, int restK) {
        if (restK == 0) {
            return restN <= 0 ? 1 : 0;
        }
        long ans = 0;
        for (int i = 0; i <= M; i++) {
            ans += process(restN - i, M, restK - 1);
        }
        return ans;
    }
    public static double dp1(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long[][] dp = new long[N + 1][K + 1];
        for (int restK = 1; restK <= K; restK++) { // 从右往左填
            for (int restN = 0; restN <= N; restN++) {
                long ans = 0;
                for (int i = 0; i <= M; i++) {
                    ans += pick(dp, restN - i, restK - 1, M);
                }
                dp[restN][restK] = ans;
            }
        }
        return (double) dp[N][K] / Math.pow(M + 1, K);
    }

    private static long pick(long[][] dp, int restN, int restK, int M) {
        if (restK == 0) {
            return restN <= 0 ? 1 : 0;
        } else if (restN <= 0) { // 因为如果还剩restK刀，但此时已经血量空了，那剩下的路都是生存点
            return (long) Math.pow(M + 1, restK);
        }
        return dp[restN][restK];
    }

    public static double dp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long[][] dp = new long[N + 1][K + 1];
        dp[0][0] = 1;
        for (int restK = 1; restK <= K; restK++) { // 从右往左填
            dp[0][restK] = (long) Math.pow(M + 1, restK);
            for (int restN = 1; restN <= N; restN++) {
                long ans = dp[restN][restK - 1] + dp[restN - 1][restK];
                if (restN - 1 - M >= 0) {
                    ans -= dp[restN - 1 - M][restK - 1];
                } else { // 拿不到格子了，情况数依然要减掉！！！
                    ans -= (long) Math.pow(M + 1, restK - 1);
                }
                dp[restN][restK] = ans;
            }
        }
        return (double) dp[N][K] / Math.pow(M + 1, K);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = killP1(N, M, K);
            double ans2 = dp1(N, M, K);
            double ans3 = dp2(N, M, K);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
