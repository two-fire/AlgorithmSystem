package Day18_单调栈;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/22 14:55
 * 单调栈
 * 一种特别设计的栈结构，为了解决如下的问题：
 *
 * 给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
 * 1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * 2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * 如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快。
 *
 * 单调栈的实现
 * 获得一个值左侧和右侧最近且小于它的数的下标
 *  arr = [ 3, 1, 2, 3]
 *          0  1  2  3
 *       [
 *          0 : [-1,  1]
 *          1 : [-1, -1]
 *          2 : [ 1, -1]
 *          3 : [ 2, -1]
 *       ]
 */
public class Code01_MonotonousStack {
    // 无重复值
    // 准备一个栈，从下到上由小到大。如果遇到小于栈顶的数，就弹出栈顶进行清算
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int N = arr.length;
        int[][] res = new int[N][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            // x小于栈顶，清算栈顶元素
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                int cur = stack.pop();
                int leftLess = stack.isEmpty() ? -1 : stack.peek();
                res[cur][0] = leftLess;
                res[cur][1] = i;
            }
            stack.add(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int leftLess = stack.isEmpty() ? -1 : stack.peek();
            res[cur][0] = leftLess;
            res[cur][1] = -1;
        }
        return res;
    }
    // 有重复值 栈中存放形式{0，1} -> 3  0,1位置是3
    public static int[][] getNearLess(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<ArrayList<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            // x小于栈顶，清算栈顶元素
            while (!stack.isEmpty() && arr[i] < arr[stack.peek().get(0)]) {
                ArrayList<Integer> key = stack.pop();
                int leftLess = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (int k : key) {
                    res[k][0] = leftLess;
                    res[k][1] = i;
                }
            }
            // 相等加入key
            if (!stack.isEmpty() && arr[i] == arr[stack.peek().get(0)]) {
                stack.peek().add(i);
            }
            // 大于直接放上面
            if (stack.isEmpty() || arr[i] > arr[stack.peek().get(0)]) {
                ArrayList<Integer> key = new ArrayList<>();
                key.add(i);
                stack.push(key);
            }
        }
        while (!stack.isEmpty()) {
            ArrayList<Integer> key = stack.pop();
            // 注意下面一定要选arraylist中最后一个
            int leftLess = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (int k : key) {
                res[k][0] = leftLess;
                res[k][1] = -1;
            }
        }
        return res;
    }

    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops1!");
                printArray(arr1);
                for (int[] cur : getNearLessNoRepeat(arr1)) {
                    printArray(cur);
                }
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops2!");
                printArray(arr2);
                for (int[] cur : getNearLess(arr2)) {
                    printArray(cur);
                }
                break;
            }
        }
        System.out.println("测试结束");
    }
}
