package Day01_排序和二分法;

import java.util.Arrays;

/**
 * @Author : LiuYan
 * @create 2021/3/20 9:11
 * 选择排序
 * 时间复杂度 O(N^2)
 */
public class Code01_SelectSort {
    private static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, i, min);
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
    //for test
    // -max ~max
    public static int randNum(int max) {
        return (int)(Math.random() * max + 1) - (int)(Math.random() * max + 1);
    }
    //for test
    public static void rightSort(int[] arr) {
        Arrays.sort(arr);
    }
    //for test
    public static int[] copy(int[] arr) {
        int[] arr1 = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = arr[i];
        }
        return arr1;
    }
    //for test
    public static boolean compare(int[] arr, int[] arr1) {
        for (int i = 0; i < arr.length; i++) {
            if (arr1[i] != arr[i]) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        int testTime = 100000; // 测试次数
        int N = 10;
        int max = 50; // 元素取值范围-max ~ max
        for (int i = 0; i < testTime; i++) {
            int len = (int)(Math.random() * N); // 长度0 ~ N-1
            int[] arr = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = randNum(max);
            }
            int[] arr1= copy(arr);
            int[] arr2= copy(arr);
            selectSort(arr2);
            rightSort(arr1);
            if (!compare(arr1,arr2)) {
                printArr(arr);
                printArr(arr2);
                printArr(arr1);
                throw new RuntimeException("error!");
            }
        }
        System.out.println("success!");

        // 出错例子一次结果调试
//        int[] arr = new int[]{ -26, -23, 1, 5, 31, 35 };
//        printArr(arr);
//        selectSort(arr);
//        printArr(arr);
    }

}
