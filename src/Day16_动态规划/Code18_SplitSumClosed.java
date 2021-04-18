package Day16_动态规划;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author : LiuYan
 * @create 2021/4/18 14:42
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 */
public class Code18_SplitSumClosed {
    public static int sum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        return process(arr, 0, sum / 2);
    }

    // 在arr[index], 要最接近rest，返回较小累加和
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = 0;
            if (rest >= arr[index]) {
                p2 = arr[index] + process(arr, index + 1, rest - arr[index]);
            }
            return Math.max(p1, p2);
        }
    }
    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][sum / 2 + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum / 2; rest++) {
                int p1 =dp[index + 1][rest];
                int p2 = 0;
                if (rest >= arr[index]) {
                    p2 = arr[index] + dp[index + 1][rest - arr[index]];
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum / 2];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = bf.readLine()) != null) {
            String[] s = str.trim().split(" ");
            int[] arr = new int[s.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(s[i]);
            }
            System.out.println(dp(arr));
        }
    }
}
