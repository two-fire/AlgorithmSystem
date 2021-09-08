package Day01_排序和二分法;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author : LiuYan
 * @create 2021/3/20 9:44
 * 冒泡排序
 * 时间复杂度 O(N^2)
 */
public class Code02_BubbleSort {
    public static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j <  i; j++) {
                if (arr[j + 1] < arr[j]) {
                    swap(arr,j + 1, j);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
    private static void printArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = br.readLine()) != null) {
            String[] s = str.trim().split(" ");
            int[] arr = new int[s.length];
            int i = 0;
            for (String value : s) {
                arr[i++] = Integer.parseInt(value);
            }
            bubbleSort(arr);
            printArr(arr);
        }
    }
}
