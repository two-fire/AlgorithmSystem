package Day17_滑动窗口;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author : LiuYan
 * @create 2021/4/20 22:49
 * 加油站的良好出发点问题
 * 链接：https://www.nowcoder.com/questionTerminal/4d76096de4754712aa6ebecd9737fcf5?answerType=1&f=discussion

 * N个加油站组成一个环形，给定两个长度都是N的非负数组gas和cost(N>1)，
 * gas[i]代表第i个加油站存的油可以跑多少千米，
 * cost[i]代表第i个加油站到环中下一个加油站相隔多少千米。
 * 假设你有一辆油箱足够大的车，初始时车里没有油。
 * 如果车从第i个加油站出发，最终可以回到这个加油站，那么第i个加油站就算良好出发点，否则就不算。
 * 请返回长度为N的boolean型数组res，res[i]代表第i个加油站是不是良好出发点。
 * 规定只能按照顺时针走，也就是i只能走到i+1，N只能走到1
 *
 * [要求]
 * 时间复杂度为O(n)，空间复杂度为O(1)
 *
 * 输入：
 * 8
 * 4 5 3 1 5 1 1 9
 * 1 9 1 2 6 0 2 0
 *
 * 输出：
 * 0 0 1 0 0 1 0 1
 *
 * 如果车从A点出发，到B点且加上B的油，还剩8的油，发现到不了C；
 * 如果从B点出发，发现车到不了C；
 * 如果从C点出发，发现可以转一圈，所以C点是良好出发点。
 *
 * 思路：一共n个点
 *  暴力方法：先把gas和cost相加得到一个sum数组，从一个点出发，顺时针转一圈，路上对sum累加，如果出现小于0，则失败。
 *  改进方法：
 *      1、同样获得一个sum数组。在它基础上，得到一个前缀和arr数组，arr[i,j,j+1..j+n..]
 *      那么arr[j]-arr[i]..arr[j+n]-arr[i]就是从j点出发的sum累加数组。
 *      2、一个从小到大的双端队列。每一轮弹出最小值，如果小于0，则失败
 */
public class Code03_GasStation {
    // 时间复杂度O(N^2) 空间O(N)
    public static int[] canCompleteCircle1(int[] gas, int[] cost) {
        int N = gas.length;
        int[] arr = new int[N];
        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
           arr[i] = gas[i] - cost[i];
        }
        for (int i = 0; i < N; i++) {
            if (arr[i] < 0) {
                continue;
            }
            res[i] = arr[i];
            for (int mod = 1; mod < N && res[i] >= 0; mod++) {
                res[i] += arr[(i + mod) % N];
            }
            res[i] = res[i] < 0 ? 0 : 1;
        }
        return res;
    }

    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int[] canCompleteCircle2(int[] gas, int[] cost) {
        int N = gas.length;
        int[] res = new int[N];
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < N; i++) {
            res[i] = good[i] ? 1 : 0;
        }
        return res;
    }

    public static boolean[] goodArray(int[] gas, int[] cost) {
        int N = gas.length;
        int M = N * 2 - 1;
        int[] sum = new int[N];
        boolean[] res = new boolean[N];
        int i = 0;
        for (; i < N; i++) {
            sum[i] = gas[i] - cost[i];
        }
        // 利用前缀和数组
        // 4  5 3  1 5  1 1 9
        // 1  9 1  2 6  0 2 0
        // 3 -4 2 -1 1 -1-1 9
        // 3 -1-2 -3-2- 3 6 9 5 7 6 5 6
        int[] arr = new int[M];
        arr[0] = sum[0];
        for (i = 1; i < M; i++) {
            arr[i] = sum[i % N] + arr[i - 1];
        }
        int index = 0;
        int L = 0;
        LinkedList<Integer> minList = new LinkedList<>();
        for (int R = N - 1; R < M; R++) { // 比较N轮，N个为1组，找到其中的最小值，看看是否小于0
            while (L <= R) {
               while (!minList.isEmpty() && arr[L] < minList.peekLast()) {
                   minList.pollLast();
               }
               minList.add(arr[L]);
               L++;
            }
            int rest = index == 0 ? 0 : arr[index - 1];
            if (minList.peekFirst() - rest >= 0) {
                res[index] = true;
            }
            if (arr[index] == minList.peekFirst()) {
                minList.pollFirst();
            }
            index++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1) + 1);
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1) + 1);
        }
        return arr;
    }
    public static int[] generateRandomArray2(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1) + 1);
        }
        return arr;
    }
    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }
    private static boolean isEqual(int[] ans1, int[] ans2) {
        for (int i = 0; i < ans1.length; i++) {
            if (ans1[i] != ans2[i]) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
//        int[] gas = new int[]{147, 9 ,27 };
//        int[] cost = new int[]{82, 149, 12  };
//        for (int n : canCompleteCircle2(gas, cost)) {
//            System.out.print(n + " ");
//        }
        int maxLen = 5;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] gas = generateRandomArray(maxLen, maxValue);
            int[] cost = generateRandomArray2(gas.length, maxValue);
            int[] ans1 = canCompleteCircle1(gas, cost);
            int[] ans2 = canCompleteCircle2(gas, cost);
            if (!isEqual(ans1,ans2)) {
                System.out.println("Oops!");
                printArray(gas);
                printArray(cost);
                printArray(ans1);
                printArray(ans2);
                break;
            }
        }
        System.out.println("测试结束");

//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNextInt()) {
//            int N = sc.nextInt();
//            int[] gas = new int[N];
//            int[] cost = new int[N];
//            for (int i = 0; i < N; i++) {
//                gas[i] = sc.nextInt();
//            }
//            for (int i = 0; i < N; i++) {
//                cost[i] = sc.nextInt();
//            }
//            for (int n : canCompleteCircle1(gas, cost)) {
//                System.out.print(n + " ");
//            }
//        }
    }


}
