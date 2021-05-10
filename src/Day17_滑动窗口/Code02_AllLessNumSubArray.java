package Day17_滑动窗口;

import java.util.LinkedList;

/**
 * @Author : LiuYan
 * @create 2021/4/20 21:18
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：
 * sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 *
 * 思路：如果[i..j]满足，那么其中的任何子数组都满足。如果[i..j]不满足，那么包含[i..j]的子数组都不满足。
 * 两个双端队列。一个下标从小到大，一个下标从大到小。
 * 从L开始，如果满足R就向右扩，到不满足时R停止。那么对于L开头的子数组，就有R-L个满足。然后L+1，继续。
 */
public class Code02_AllLessNumSubArray {
    public static int num1(int[] arr, int n) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int max, min;
        int res = 0;
        for (int i = 0; i < N; i++) {
            max = arr[i];
            min = arr[i];
            for (int j = i; j < N; j++) {
                for (int k = i + 1; k <= j; k++) {
                    max = Math.max(arr[k], max);
                    min = Math.min(arr[k], min);
                }
                if (max - min <= n) {
                    res++;
                }
            }
        }
        return res;
    }

    public static int num2(int[] arr, int n) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        LinkedList<Integer> maxList = new LinkedList<>();
        LinkedList<Integer> minList = new LinkedList<>();
        int R = 0;
        for (int L = 0; L < N; L++) {
            while (R < N) {
                // 注意是 >=  , 不是 >
                while (!maxList.isEmpty() && arr[R] >= arr[maxList.peekLast()]) {
                    maxList.pollLast();
                }
                maxList.add(R);
                while (!minList.isEmpty() && arr[R] <= arr[minList.peekLast()]) {
                    minList.pollLast();
                }
                minList.add(R);
                if (arr[maxList.peekFirst()] - arr[minList.peekFirst()] > n) {
                    break;
                } else {
                    R++;
                }
            }
            count += (R - L);
            if (L == maxList.peekFirst()) {
                maxList.pollFirst();
            }
            if (L == minList.peekFirst()) {
                minList.pollFirst();
            }
        }
        return count;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
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

    public static void main(String[] args) {
        int maxLen = 5;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = num1(arr, sum);
            int ans2 = num2(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
//        int[] arr = new int[]{70, 30};
//        System.out.println(num2(arr, 118));
    }
}
