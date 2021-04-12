package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/10 21:39
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 *
 *
 */
public class Code04_ConvertToLetterString {
    // 暴力
    public static int convertNum1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chs = s.toCharArray();
        return process1(chs, 0);
    }
    private static int process1(char[] arr, int i) {
        if (i == arr.length) {
            return 1;
        } else if (arr[i] == '0') { // 单独是0终止失败
            return 0;
        }
        // AAA
        int p1 = process1(arr, i + 1);
        int p2 = 0;
        // KA AK
        if (i < arr.length - 1) {
            int s = Integer.parseInt(String.valueOf(arr[i]) + String.valueOf(arr[i + 1]));
            if (s > 9 && s < 27) {
                 p2 = process1(arr, i + 2);
            }
        }
        return p1 + p2;
    }

    // 状态转移
    public static int dp(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int N = chs.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) { // 要从后往前！！！
            if (chs[i] != '0') {
                int p1 = dp[i + 1];
                int p2 = 0;
                // KA AK
                if (i < N - 1) {
                    int s = Integer.parseInt(String.valueOf(chs[i]) + String.valueOf(chs[i + 1]));
                    if (s > 9 && s < 27) {
                        p2 = dp[i + 2];
                    }
                }
                dp[i] = p1 + p2;
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(convertNum1("7210231231232031203123"));
        System.out.println(dp("7210231231232031203123"));
    }
}
