package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/14 15:16
 * 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 */
public class Code14_BobDie {
    public static double liveP1(int N, int M, int row, int col, int k) {
        return (double) process(N, M, row, col, k) / Math.pow(4, k);
    }

    // 醉汉在(row, col)
    private static long process(int N, int M, int row, int col, int rest) {
        if (row < 0 || col < 0 ||row == N || col == M) {
            return 0;
        }
        if (rest == 0) {
            return 1;
        }
        // 还在棋盘中，还有步数要走
        long p1 = process(N, M, row - 1, col, rest - 1);
        long p2 = process(N, M, row, col - 1, rest - 1);
        long p3 = process(N, M, row + 1, col, rest - 1);
        long p4 = process(N, M, row, col + 1, rest - 1);
        return p1 + p2 + p3 + p4;
    }

    public static double liveP2(int N, int M, int row, int col, int k) {
        long[][][] dp = new long[N + 1][M + 1][k + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int res = 1; res <= k; res++) {
            for (int c = 0; c < M; c++) {
                for (int r = 0; r <  N; r++) {
                    // 还在棋盘中，还有步数要走
                    dp[r][c][res] = pick(dp, N, M, r - 1, c, res - 1);
                    dp[r][c][res] += pick(dp, N, M, r, c - 1, res - 1);
                    dp[r][c][res] += pick(dp, N, M, r + 1, c, res - 1);
                    dp[r][c][res] += pick(dp, N, M, r, c + 1, res - 1);
                }
            }
        }
        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    private static long pick(long[][][] dp, int N, int M, int row, int col, int rest) {
        if (row < 0 || col < 0 ||row == N || col == M) {
            return 0;
        }
        return dp[row][col][rest];
    }

    public static void main(String[] args) {
        System.out.println(liveP1(50, 50, 6, 6, 10));
        System.out.println(liveP2(50, 50, 6, 6, 10));
    }
}
