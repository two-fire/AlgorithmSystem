package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/13 12:50
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步  【马走“日”】
 * 最后落在(x,y)上的方法数有多少种?
 * 10 * 9
 */
public class Code08_HorseJump {
    /* 暴力 */
    public static int jump(int x, int y, int k) {
        if (x < 0 || y < 0 || x > 8 || y > 9) {
            return 0;
        }
        return process(0, 0, x, y, k);
    }
    //  curx,cury 到 x，y 还有rest步
    private static int process(int curx, int cury, int x, int y, int rest) {
        if (curx < 0 || cury < 0 || curx > 8 || cury > 9) {
            return 0;
        }
        if (rest == 0) {
            return curx == x && cury == y ? 1 : 0;
        }
        int p1 = process(curx + 1, cury + 2, x, y, rest - 1);
        p1 += process(curx - 1, cury + 2, x, y, rest - 1);
        p1 += process(curx + 2, cury + 1, x, y, rest - 1);
        p1 += process(curx - 2, cury + 1, x, y, rest - 1);
        p1 += process(curx + 2, cury - 1, x, y, rest - 1);
        p1 += process(curx - 2, cury - 1, x, y, rest - 1);
        p1 += process(curx + 1, cury - 2, x, y, rest - 1);
        p1 += process(curx - 1, cury - 2, x, y, rest - 1);
        return p1;
    }

    /* dp */
    public static int jump2(int x, int y, int k) {
        if (x < 0 || y < 0 || x > 8 || y > 9) {
            return 0;
        }
        int[][][] dp = new int[9][10][k + 1]; // 三维
        dp[x][y][0] = 1;
        // 从下往上层填。本层的填写顺序不重要，因为是依赖底下一层的
        for (int rest = 1; rest <= k; rest++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 10; j++) {
                    int p1 = pick(i + 1, j + 2, rest - 1, dp);
                    p1 += pick(i - 1, j + 2, rest - 1, dp);
                    p1 += pick(i + 2, j + 1, rest - 1, dp);
                    p1 += pick(i - 2, j + 1, rest - 1, dp);
                    p1 += pick(i + 2, j - 1, rest - 1, dp);
                    p1 += pick(i - 2, j - 1, rest - 1, dp);
                    p1 += pick(i + 1, j - 2, rest - 1, dp);
                    p1 += pick(i - 1, j - 2, rest - 1, dp);
                    dp[i][j][rest] = p1;
                }
            }
        }
        return dp[0][0][k];
    }
    private static int pick(int x, int y, int rest, int[][][] dp) {
        if (x < 0 || y < 0 || x > 8 || y > 9) {
            return 0;
        }
        return dp[x][y][rest];
    }


    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(jump(x, y, step));
        System.out.println(jump2(x, y, step));
    }

}
