package Day16_动态规划;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : LiuYan
 * @create 2021/4/14 13:41
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 */
public class Code13_CoinsWaySameValueSamePapper {
    public static class Coins {
        private int[] value;
        private int[] count;
        public Coins(int[] v, int[] c) {
            value = v;
            count = c;
        }
    }
    public static Coins init(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (!counts.containsKey(arr[i])) {
                counts.put(arr[i], 1);
            } else {
                counts.put(arr[i], counts.get(arr[i]) + 1);
            }
        }
        int N = counts.size();
        int[] value = new int[N];
        int[] count = new int[N];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            value[i] = entry.getKey();
            count[i++] = entry.getValue();
        }
        return new Coins(value, count);
    }
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Coins coins = init(arr);
        return process(coins.value, coins.count, 0, aim);
    }
    private static int process(int[] value, int[] count, int index, int rest) {
        if (rest == 0) {
            return 1;
        } else if (index == value.length) {
            return 0;
        } else {
            int p = 0;
            for (int zhangs = 0; zhangs <= count[index] && zhangs * value[index] <= rest; zhangs++) {
                p += process(value, count, index + 1, rest - (zhangs * value[index]));
            }
            return p;
        }
    }

    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Coins coins = init(arr);
        int[] value = coins.value;
        int[] count = coins.count;

        int N = value.length;
        int[][] dp = new int[N + 1][aim + 1];
        for (int i = 0; i <= N; i++) {
            dp[i][0] = 1;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 1; rest <= aim; rest++) {
                int p = 0;
                for (int zhangs = 0; zhangs <= count[index] && zhangs * value[index] <= rest; zhangs++) {
                    p += dp[index + 1][rest - (zhangs * value[index])];
                }
                dp[index][rest] = p;
            }
        }
        return dp[0][aim];
    }

    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Coins coins = init(arr);
        int[] value = coins.value;
        int[] count = coins.count;

        int N = value.length;
        int[][] dp = new int[N + 1][aim + 1];
        for (int i = 0; i <= N; i++) {
            dp[i][0] = 1;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 1; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest >= value[index]) {
                    dp[index][rest] += dp[index][rest - value[index]];
                }
                if (rest - value[index] * (count[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - value[index] * (count[index] + 1)];
                }
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
