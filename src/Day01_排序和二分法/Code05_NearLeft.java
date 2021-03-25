package Day01_排序和二分法;

import java.util.Arrays;

/**
 * @Author : LiuYan
 * @create 2021/3/24 14:21
 * 在一个有序数组中，找>=某个数最左侧的位置
 * 二分法，每次遇到一个>=x的数，就用tmp记一下。
 */
public class Code05_NearLeft {
    public static int search(int[] sortedArr, int x) {
        if (sortedArr == null) {
            return -1;
        }
       return search(sortedArr, 0 ,sortedArr.length - 1, x);
    }
    public static int search(int[] sortedArr, int l, int r, int x) {
        int mid;
        int tmp = -1;
        while (l <= r) {
            mid = l + ((r - l) >> 1);
            if (x <= sortedArr[mid]) {
                tmp = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return tmp;
    }
    //for test
    // -max ~max
    public static int randNum(int max) {
        return (int)(Math.random() * max + 1) - (int)(Math.random() * max + 1);
    }
    //for test
    private static void printArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    // for test
    // >=x的最左侧数
    private static int right_search(int[] arr,int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= x) {
                return i;
            }
        }
        return -1;
    }
    //for test
    public static boolean compare(int[] arr, int rx, int tx) {
        return rx == tx;
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
            int tx = search(arr, x);
            if (!compare(arr, rx, tx)) {
                System.out.println(x+ " " + rx+" "+ tx);
                printArr(arr);
                throw new RuntimeException("error!");
            }
        }
        System.out.println("success!");
    }
}
