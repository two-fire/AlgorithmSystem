package Day17_滑动窗口;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Author : LiuYan
 * @create 2021/4/21 16:46
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 返回组成aim的最少货币数
 * 注意：
 * 因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
 *
 */
public class Code04_MinCoinsOnePaper {
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }
    private static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = process(arr, index + 1, rest - arr[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            return Math.min(p1, p2);
        }
    }
    // dp1时间复杂度为：O(arr长度 * aim)
    public static int dp1(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                if (rest < 0) {
                    return Integer.MAX_VALUE;
                }

                int p1 = dp[index + 1][rest];
                int p2 = Integer.MAX_VALUE;
                if (rest >= arr[index]) {
                    p2 = dp[index + 1][rest - arr[index]];
                    if (p2 != Integer.MAX_VALUE) {
                        p2++;
                    }
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }

    public static class Info {
        private int[] coins; // 货币
        private int[] nums; // 张数
        public Info(int[] c, int[] n) {
            coins = c;
            nums = n;
        }
    }

    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], 1);
            } else {
                map.put(arr[i], map.get(arr[i]) + 1);
            }
        }
        int[] coins = new int[map.size()];
        int[] nums = new int[map.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                coins[i] = entry.getKey();
                nums[i++] = entry.getValue();
        }
        return new Info(coins, nums);
    }

    public static int minCoins2(int[] arr, int aim) {
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;
        return process2(coins, nums, 0, aim);
    }
    private static int process2(int[] coins, int[] nums, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }
        if (index == coins.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int min =Integer.MAX_VALUE;
        for (int zhangs = 0; zhangs <= nums[index]; zhangs++) {
            int p2 = process2(coins, nums, index + 1, rest - zhangs * coins[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2 += zhangs;
            }
            min = Math.min(min, p2);
        }
        return min;
    }

    // dp2时间复杂度为：O(arr长度) + O(货币种数 * aim * 每种货币的平均张数)
    public static int dp2(int[] arr, int aim) {
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;

        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int min =Integer.MAX_VALUE;
                for (int zhangs = 0; zhangs <= nums[index] && rest - zhangs * coins[index] >= 0; zhangs++) {
                    int p2 = dp[index + 1][rest - zhangs * coins[index]];
                    if (p2 != Integer.MAX_VALUE) {
                        p2 += zhangs;
                    }
                    min = Math.min(min, p2);
                }
                dp[index][rest] = min;
            }
        }
        return dp[0][aim];
    }

    // dp3时间复杂度为：O(arr长度) + O(货币种数 * aim)
    // 优化需要用到窗口内最小值的更新结构
    public static int dp3(int[] arr, int aim) {
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;

        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int mod = 0; mod < Math.min(coins[index], aim + 1); mod++) {  // 处理的组号限制
                // 依次填好mod mod+x mod+2*x……
                LinkedList<Integer> minList = new LinkedList<>();
                minList.add(mod);
                dp[index][mod] = dp[index + 1][mod];
                for (int rest = mod + coins[index]; rest <= aim; rest+= coins[index]) {
                    while (!minList.isEmpty() && (dp[index + 1][minList.peekLast()] == Integer.MAX_VALUE ||
                            dp[index + 1][minList.peekLast()] + compensate(minList.peekLast(), rest, coins[index]) >= dp[index + 1][rest])) {
                        minList.pollLast();
                    }
                    minList.add(rest);
                    dp[index][rest] = dp[index + 1][minList.peekFirst()] + compensate(minList.peekFirst(), rest, coins[index]);
                    int overdue = rest - coins[index] * (nums[index] );
                    if (minList.peekFirst() == overdue) {
                        minList.pollFirst();
                    }
                }
            }
        }
        return dp[0][aim];
    }

    public static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }

    // 为了测试
    public static int[] randomArray(int N, int maxValue) {
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
//        int[] arr = new int[]{20 };
//        System.out.println(dp3(arr, 20));
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
            int ans4 = dp3(arr, aim);
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
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

//        System.out.println("==========");

//        int aim = 0;
//        int[] arr = null;
//        long start;
//        long end;
//        int ans2;
//        int ans3;
//
//        System.out.println("性能测试开始");
//        maxLen = 30000;
//        maxValue = 20;
//        aim = 60000;
//        arr = randomArray(maxLen, maxValue);
//
//        start = System.currentTimeMillis();
//        ans2 = dp2(arr, aim);
//        end = System.currentTimeMillis();
//        System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");
//
//        start = System.currentTimeMillis();
//        ans3 = dp3(arr, aim);
//        end = System.currentTimeMillis();
//        System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");
//        System.out.println("性能测试结束");
//
//        System.out.println("===========");
//
//        System.out.println("货币大量重复出现情况下，");
//        System.out.println("大数据量测试dp3开始");
//        maxLen = 20000000;
//        aim = 10000;
//        maxValue = 10000;
//        arr = randomArray(maxLen, maxValue);
//        start = System.currentTimeMillis();
//        ans3 = dp3(arr, aim);
//        end = System.currentTimeMillis();
//        System.out.println("dp3运行时间 : " + (end - start) + " ms");
//        System.out.println("大数据量测试dp3结束");
//
//        System.out.println("===========");
//
//        System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
//        System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
//        System.out.println("dp3的优化用到了窗口内最小值的更新结构");
    }

}
