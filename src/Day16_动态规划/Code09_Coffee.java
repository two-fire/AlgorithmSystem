package Day16_动态规划;

import javax.crypto.Mac;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author : LiuYan
 * @create 2021/4/13 16:24
 * 给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
 * 假设所有人拿到咖啡之后立刻喝干净，
 * 返回从开始等到所有咖啡机变干净的最短时间
 * 三个参数：int[] arr、int N，int a、int b
 *
 * 其实是两道题：
 *  1）返回一个数组，N个人分别最快多久能喝完？
 *  2）
 *
 * 比如：
 * arr[3 1 7]  0号3min泡一杯，1号1min泡一杯，2号7min泡一杯。这三个咖啡机之间是并行的，各自是串行的
 *
 * 思路：用小根堆，里面放的对象是（咖啡机什么时候可以再用，咖啡泡一杯要多久）。用再用时间+泡时间 总时间来作为比较依据。
 *
 * 当一个参数没办法很明确知道范围的时候，就要人为想限制，来估计参数范围大小。这种就叫做业务限制模型。
 * 因为curTime变化范围不知道，所以不能用明确的状态转换dp。
 * curTime <= 所有杯子
 */
public class Code09_Coffee {

    public static class Machine {
        private int nextTime; // 咖啡机什么时候可以再用
        private int workTime; // 咖啡泡一杯要多久
        public Machine(int nt, int wt) {
            nextTime = nt;
            workTime = wt;
        }

    }
    public static class TimeComp implements Comparator<Machine> {
        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.workTime + o1.nextTime) - (o2.workTime + o2.nextTime);
        }
    }

    public static int minTime1(int[] arr, int N, int a, int b) {
        PriorityQueue<Machine> machines = new PriorityQueue<>(new TimeComp());
        // 返回一个数组，N个人最快多久能喝完？
        int[] drunk = new int[N];
        for (int i = 0; i < arr.length; i++) {
            machines.add(new Machine(0, arr[i]));
        }
        for (int i = 0; i < N; i++) {
            Machine cur = machines.poll();
            cur.nextTime += cur.workTime;
            drunk[i] = cur.nextTime;
            machines.add(cur);
        }
        // 喝完的杯子是挥发还是洗？
        return process(drunk, 0, 0, a, b);
    }

    // 现在处于drunk[index]，当前时间curTime。洗杯子时间a，挥发时间b。返回最短需要时间
    private static int process(int[] drunk, int index, int curTime, int a, int b) {
        if (index == drunk.length) {
            return 0;
        }
        // 决定洗
        int selfClean1 = a + Math.max(drunk[index], curTime);
        int restClean1 = process(drunk, index + 1, selfClean1, a, b);
        int p1 = Math.max(selfClean1, restClean1); // 不能相加。因为是时间点！！！

        // 决定挥发
        int selfClean2 = b + drunk[index];
        int restClean2 = process(drunk, index + 1, curTime, a, b);
        int p2 = Math.max(selfClean2, restClean2);
        return Math.min(p1, p2);
    }

    // 贪心+优良尝试改成动态规划
    public static int minTime2(int[] arr, int N, int a, int b) {
        PriorityQueue<Machine> machines = new PriorityQueue<>(new TimeComp());
        // 返回一个数组，N个人最快多久能喝完？
        int[] drunk = new int[N];
        for (int i = 0; i < arr.length; i++) {
            machines.add(new Machine(0, arr[i]));
        }
        for (int i = 0; i < N; i++) {
            Machine cur = machines.poll();
            cur.nextTime += cur.workTime;
            drunk[i] = cur.nextTime;
            machines.add(cur);
        }
        // 喝完的杯子是挥发还是洗？
        return dp(drunk, a, b);
    }
    // 洗杯子时间a，挥发时间b
    private static int dp(int[] drunk, int a, int b) {
        // 估计curTime的范围
        int maxCurTime = 0;
        for (int i = 0; i < drunk.length; i++) {
            maxCurTime = Math.max(maxCurTime, drunk[i]) + a;
        }
        int N = drunk.length;
        int[][] dp = new int[N + 1][maxCurTime + 1];
        // 从下往上填
        for (int index = N - 1; index >= 0; index--) {
            for (int curTime = 0; curTime <= maxCurTime; curTime++) {
                // 决定洗
                int selfClean1 = a + Math.max(drunk[index], curTime);
                if (selfClean1 > maxCurTime) { // 因为超的部分，必然不会被递归调用到
                    break;
                }
                int restClean1 = dp[index + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // 决定挥发
                int selfClean2 = b + drunk[index];
                int restClean2 = dp[index + 1][curTime];
                int p2 = Math.max(selfClean2, restClean2);
                dp[index][curTime] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }


    // test
    /*
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }
    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }
    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }
    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }
*/
}
