package Day18_单调栈;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/24 10:00
 * 给定一个非负数组arr，代表直方图
 * 返回直方图的最大长方形面积
 *
 * 思路：遍历把当下作为高，向左右两边扩，直到小于它停。得到一个max
 *
 * 测试链接：https://leetcode.com/problems/largest-rectangle-in-histogram
 */
public class Code03_LargestRectangleInHistogram {
    public static int largestRectangleArea1(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int cur = stack.pop();
                int leftLess = stack.isEmpty() ? -1 : stack.peek();
                max = Math.max(max, (i - leftLess - 1) * arr[cur]);
            }
            stack.add(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int leftLess = stack.isEmpty() ? -1 : stack.peek();
            max = Math.max(max, (arr.length - 1 - leftLess) * arr[cur]);
        }
        return max;
    }
    // 用数组代替栈
    public static int largestRectangleArea2(int[] arr) {
        int[] stack = new int[arr.length];
        int size = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            while (size != 0 && arr[stack[size]] >= arr[i]) {
                int cur = stack[size--];
                int leftLess = size == 0 ? -1 : stack[size];
                max = Math.max(max, (i - leftLess - 1) * arr[cur]);
            }
            stack[++size] = i;
        }
        while (size != 0) {
            int cur = stack[size--];
            int leftLess = size == 0 ? -1 : stack[size];
            max = Math.max(max, (arr.length - 1 - leftLess) * arr[cur]);
        }
        return max;
    }
}
