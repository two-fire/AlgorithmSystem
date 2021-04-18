package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/16 19:16
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的最少货币数
 */
public class Code16_MinCoinsNoLimit {
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim, 0);
    }
    // 位置arr[index]，还剩rest值，此时货币数为sum
    private static int process(int[] arr, int index, int rest, int sum) {
        if (rest == 0) {
            return sum;
        } else if (index == arr.length) {
            return Integer.MAX_VALUE;
        } else {
            int p1 = Integer.MAX_VALUE;
            for (int zhangs = 0; zhangs * arr[index] <= rest; zhangs++) {
                // 注意！此处的sum为sum + zhangs
                p1 = Math.min(p1, process(arr, index + 1, rest - zhangs * arr[index], sum + zhangs));
            }
            return p1;
        }
    }
    public static int minCoins2(int[] arr, int aim) {
        return process(arr, 0, aim);
    }
    // 位置arr[index]，还剩rest值
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int p1 = Integer.MAX_VALUE;
            for (int zhangs = 0; zhangs * arr[index] <= rest; zhangs++) {
                int next = process(arr, index + 1, rest - zhangs * arr[index]);
                if (next != Integer.MAX_VALUE) {
                    p1 = Math.min(p1, zhangs + next);
                }
            }
            return p1;
        }
    }

    public static int dp1(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int p1 = Integer.MAX_VALUE;
                for (int zhangs = 0; zhangs * arr[index] <= rest; zhangs++) {
                    int next = dp[index + 1][rest - zhangs * arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        p1 = Math.min(p1, zhangs + next);
                    }
                }
                dp[index][rest] = p1;
            }
        }
        return dp[0][aim];
    }

    public static int dp2(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int next = dp[index + 1][rest];
                // 必须同时满足下面两个条件！！！
                // rest - arr[index]假设叫x。
                // 首先，x要在表中，不能越界；其次，当初决策x时，要保证x是有效的。否则+1就超最大值了
                if (rest - arr[index] >= 0
                        && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    next = Math.min(dp[index][rest - arr[index]] + 1, next);
                }
                dp[index][rest] = next;
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
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
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            int ans4 = minCoins2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3 || ans4 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("功能测试结束");
    }

}
