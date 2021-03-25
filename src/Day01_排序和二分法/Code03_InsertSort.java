package Day01_排序和二分法;

import java.util.Scanner;

/**
 * @Author : LiuYan
 * @create 2021/3/23 13:18
 * 插入排序
 * 时间复杂度 O(N^2)
 */
public class Code03_InsertSort {
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] >= arr[j - 1]) {
                    break;
                }
                swap(arr, j, j - 1);
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            String[] chs = str.trim().split(" ");
            int[] arr = new int[chs.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(chs[i]);
            }
            insertSort(arr);
            printArr(arr);
        }
    }
}
