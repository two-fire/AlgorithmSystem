package Day19_矩阵快速幂技巧;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/27 18:13
 *
 * 给定一个数组arr，
 * 返回所有子数组最小值的累加和
 *
 * subArrayMinSum1是暴力解
 * sumarrayMins是最优解思路下的单调栈优化 O(N)
 *
 * 思路：
 *  假设没有重复值：当前遍历到的数x为最小值。左右两侧找到小于x的数l和r。(l，x]作为左端，[x,r)作为右端。
 * 每次就有(x-l) * (r-x)个数组，它们的最小值是x。
 *  有重复值：注意去重
 *
 * 测试链接：https://leetcode.com/problems/sum-of-subarray-minimums/
 */
public class Code01_SumOfSubarrayMinimums {
    public static int subArrayMinSum1(int[] arr) {
        int ans = 0;
        int min;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                min = arr[i];
                for (int k = i; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        return ans;
    }

    public static int subArrayMins(int[] arr) {
        long ans = 0;
        // left[i] = x 第i位数左侧小于等于它的数的下标
        int[] left = nearLessEqualLeft(arr);
        // right[i] = y 第i位数右侧小于它的数的下标
        int[] right = nearLessRight(arr);
        for (int i = 0; i < arr.length; i++) {
            ans += nums(left[i], i, right[i]) * arr[i];
        }
        return (int) ans;
    }

    // 有(x-l) * (r-x)个数组，它们的最小值是x
    private static long nums(int left, int x, int right) {
        return (right - x) * (x - left);
    }

    private static int[] nearLessRight(int[] arr) {
        int[] res = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                res[stack.pop()] = i;
            }
            stack.add(i);
        }
        while (!stack.isEmpty()) {
            res[stack.pop()] = arr.length;
        }
        return res;
    }

    private static int[] nearLessEqualLeft(int[] arr) {
        int[] res = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                res[stack.pop()] = i;
            }
            stack.add(i);
        }
        while (!stack.isEmpty()) {
            res[stack.pop()] = -1;
        }
        return res;
    }
    
    

}
