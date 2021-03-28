package Day05_快排;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author : LiuYan
 * @create 2021/3/27 19:33
 * 合并有序数组
 *
 * 将两个有序Z整数数组进行合并。例如: A:[1,2,5]、B:[3,4] 合并为 [1,2,3,4,5]
 * 要求: 时间复杂度O(m+n)，空间复杂度O(1)
 */
public class Code02_3 {
    public static int[] merge(int[] a, int[] b) {
        int[] arr = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int index = 0;
        while (i < a.length && j < b.length) {
            arr[index++] = (a[i] <= b[j]) ? a[i++] : b[j++];
        }
        while (i < a.length) {
            arr[index++] = a[i++];
        }
        while (j < b.length) {
            arr[index++] = a[j++];
        }
        return arr;
    }

    public static void print(int[] arr) {
        for (int v : arr) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int[] a = new int[]{ 1,2,5 };
        int[] b = new int[]{ 3,4 };
        print(merge(a, b));
    }
}
