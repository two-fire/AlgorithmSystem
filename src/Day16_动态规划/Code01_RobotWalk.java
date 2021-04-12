package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/10 10:26
 * 机器人走路
 * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数。
 *
 * 尝试：机器人当前来到cur位置，还剩rest要走，目标是aim，位置有1~N
 *      返回机器人从cur出发，走过rest之后，最终停在aim的方法数是多少？
 *
 * 出现重复解的暴力递归才可以动态规划进行优化。
 * 用一个二维数组当缓存表 (傻缓存，从顶向下)
 * 尝试之后，再来状态转移
 */
public class Code01_RobotWalk {

    // N个位置，记为1~N
    // 开始时机器人在M位置上
    // 规定机器人必须走 K 步
    // 最终能来到P位置
    public static int ways1(int N, int M, int K, int P) {
        if (N < 2 || M < 1 || M > N || P < 1 || P > N) {
            return -1;
        }
        return process1(M, K, P, N);
    }
    // 机器人当前来到cur位置，还剩rest要走，目标是aim，位置有1~N
    // 返回机器人从cur出发，走过rest之后，最终停在aim的方法数
    private static int process1(int cur, int rest, int aim, int N) {
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return process1(cur + 1, rest - 1, aim, N);
        }
        if (cur == N) {
            return process1(cur - 1, rest - 1, aim, N);
        }
        return process1(cur - 1, rest - 1, aim, N) + process1(cur + 1, rest - 1, aim, N);
    }

    // 因为有重复解 傻缓存
    public static int ways2(int N, int M, int K, int P) {
        if (N < 2 || M < 1 || M > N || P < 1 || P > N) {
            return -1;
        }
        // dp[i][j] : 在i位置，还有j步要走，此时已经有多少个解
        int[][] dp = new int[N + 1][K + 1];
        return process2(M, K, P, N, dp);
    }
    private static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        if (dp[cur][rest] != 0) {
            return dp[cur][rest];
        }
        int ans = 0;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(cur + 1, rest - 1, aim, N, dp);
        } else if (cur == N) {
            ans = process2(cur - 1, rest - 1, aim, N, dp);

        } else {
            ans = process2(cur + 1, rest - 1, aim, N, dp) + process2(cur - 1, rest - 1, aim, N, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    // 状态转移 从上到下，从左往右
    // M : 开始位置  K：步数  P：结束位置
    public static int ways3(int N, int M, int K, int P) {
        if (N < 2 || M < 1 || M > N || P < 1 || P > N) {
            return -1;
        }
        //  dp[i][j] : 在i位置，还有j步要走，此时已经有多少个解
        int[][] dp = new int[N + 1][K + 1];
        // 第一列rest = 0，只有当cur为aim时才是1
        dp[P][0] = 1;
        for (int i = 1; i <= K; i++) {
            // 第一行依赖第二行（cur=1，还有i步要走，只看cur+1还有i-1步要走）
            dp[1][i] = dp[2][i - 1];
            for (int j = 2; j < N; j++) {
                dp[j][i] = dp[j + 1][i - 1] + dp[j - 1][i - 1];
            }
            // 第N行依赖第N-1行
            dp[N][i] = dp[N - 1][i - 1];
        }

        return dp[M][K];
    }

    public static void main(String[] args) {
        // 1~5, 开始在2，走6步到4
        System.out.println(ways1(5, 2, 6, 4));
        System.out.println(ways2(5, 2, 6, 4));
        System.out.println(ways3(5, 2, 6, 4));
    }
}
