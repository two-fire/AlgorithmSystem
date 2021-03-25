package Day01_排序和二分法;

import java.util.Arrays;

/**
 * @Author : LiuYan
 * @create 2021/3/24 11:45
 * 认识二分法
 *  在一个有序数组中，找某个数是否存在。存在返回下标，否则-1
 */
public class Code04_searchX {
    public static int searchX(int[] arr, int x) {
        if (arr == null) {
            return -1;
        }
//        return searchX(arr, 0, arr.length - 1, x);
        return searchX2(arr, 0 , arr.length - 1, x);
    }

    // 递归版
    private static int searchX(int[] arr, int l, int r, int x) {
        if (l > r) {
            return -1;
        }
        int mid = l + ((r - l) >> 1);
        if (x == arr[mid]) {
            return mid;
        } else if (x < arr[mid]) {
            return searchX(arr, l, mid - 1, x);
        } else {
            return searchX(arr, mid + 1, r, x);
        }
    }

    // 迭代版
    private static int searchX2(int[] arr, int l, int r, int x){
        int mid;
        while (l <= r) {
            mid = l + ((r - l) >> 1);
            if (x == arr[mid]) {
                return mid;
            } else if (x < arr[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    //for test
    // -max ~max
    public static int randNum(int max) {
        return (int)(Math.random() * max + 1) - (int)(Math.random() * max + 1);
    }

    // for test
    private static int right_search(int[] arr,int x) {
        for (int i = 0; i < arr.length; i++) {
            if (x == arr[i]) {
                return i;
            }
        }
        return -1;
    }

    //for test
    private static void printArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    //for test
    public static boolean compare(int[] arr, int rx, int tx) {
       if (rx == -1) {
           return (tx == -1) ? true : false;
       } else {
           return (arr[rx] == arr[tx]) ? true : false;
       }
    }
    public static void main(String[] args) {
        int testTime = 100000; // 测试次数
        int N = 10;
        int max = 50; // 元素取值范围-max ~ max
        int x = randNum(max * 2);
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N); // 长度0 ~ N-1
            int[] arr = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = randNum(max);
            }
            Arrays.sort(arr);
            int rx = right_search(arr, x);
            int tx = searchX(arr, x);
            if (!compare(arr, rx, tx)) {
                System.out.println(x+ " " + rx+" "+ tx);
                printArr(arr);
                throw new RuntimeException("error!");
            }
        }
        System.out.println("success!");

    }
}
