package Day16_动态规划;

import java.util.Scanner;

/**
 * @Author : LiuYan
 * @create 2021/4/18 15:29
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 */
public class Code19_SplitSumClosedSizeHalf {
    public static int sum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        if ((arr.length & 1) == 1) { // 奇数
            return Math.max(process(arr, 0, sum / 2, (arr.length + 1) / 2),
                    process(arr, 0, sum / 2, arr.length / 2));
        } else {
            return process(arr, 0, sum / 2, arr.length / 2);
        }

    }

    private static int process(int[] arr, int index, int rest, int count) {
        if (index == arr.length) {
            return count == 0 ? 0 : -1;
        } else if (count < 0) {
            return -1;
        } else {
            int p1 = process(arr, index + 1, rest, count);
            int p2 = -1;
            int next = -1;
            if (rest >= arr[index]) {
                next = process(arr, index + 1, rest - arr[index], count - 1);
            }
            if (next != -1) {
                p2 = arr[index] + next;
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
        int[][][] dp = new int[N + 1][sum / 2 + 1][(N + 1) / 2 + 1];
        for (int rest = 0; rest <= sum / 2; rest++) {
            for (int count = 1; count <= (N + 1) / 2; count++) {
                dp[N][rest][count] = -1;
            }
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum / 2; rest++) {
                for (int count = 0; count <= (N + 1) / 2; count++) {
                    int p1 = dp[index + 1][rest][count];
                    int p2 = -1;
                    int next = -1;
                    if (rest >= arr[index] && count >= 1) {
                        next = dp[index + 1][rest - arr[index]][count - 1];
                    }
                    if (next != -1) {
                        p2 = arr[index] + next;
                    }
                    dp[index][rest][count] = Math.max(p1, p2);
                }
            }
        }

        if ((arr.length & 1) == 1) { // 奇数
            return Math.max(dp[0][sum / 2][(arr.length + 1) / 2], dp[0][sum / 2][arr.length / 2]);
        } else {
            return dp[0][sum / 2][arr.length / 2];
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            String[] s = str.trim().split(" ");
            int[] arr = new int[s.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(s[i]);
            }
            System.out.println(dp(arr));
        }
    }
}
