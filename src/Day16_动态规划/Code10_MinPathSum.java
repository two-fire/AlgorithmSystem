package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/13 21:09
 * 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
 * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 * 返回最小距离累加和
 */
public class Code10_MinPathSum {
    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        return process(m, 0, 0);
    }

    private static int process(int[][] m, int x, int y) {
        int row = m.length;
        int col = m[0].length;
        if (x == row - 1 && y == col - 1) {
            return m[x][y];
        } else if (x == row - 1) {
            return m[x][y] + process(m, x, y + 1);
        } else if (y == col - 1) {
            return m[x][y] + process(m, x + 1, y);
        } else {
            int p1 = process(m, x + 1, y);
            int p2 = process(m, x, y + 1);
            return m[x][y] + Math.min(p1, p2);
        }
    }
    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[row - 1][col - 1] = m[row - 1][col - 1];

        for (int x = row - 2; x >= 0; x--) {
            dp[x][col - 1] = m[x][col - 1] + dp[x + 1][col - 1];
        }
        for (int y = col - 2; y >= 0 ; y--) {
            dp[row - 1][y] = m[row - 1][y] + dp[row - 1][y + 1];
        }
        for (int x = row - 2; x >= 0; x--) {
            for (int y = col - 2; y >= 0; y--) {
                int p1 = dp[x + 1][y];
                int p2 = dp[x][y + 1];
                dp[x][y] = m[x][y] + Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    // for test
    /*
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    // for test
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));

    }
    */
}
