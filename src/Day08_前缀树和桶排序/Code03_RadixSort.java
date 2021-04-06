package Day08_前缀树和桶排序;

import java.util.Arrays;

/**
 * @Author : LiuYan
 * @create 2021/3/29 8:50
 *
 * 不基于比较的排序——基数排序
 *  非负 + 能用十进制理解的数
 *  时间复杂度为O(N)，额外空间负载度O(N) 稳定
 *
 * 思路；从低位开始依次进桶，然后从左到右倒出；再从十位…
 */
public class Code03_RadixSort {
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1);
    }
    public static void radixSort(int[] arr, int l, int r) {
        // 找到最大有多少位
        int bits = maxBits(arr);
        int[] help = new int[r - l + 1];
        int i = 0, j = 0;
        for (int d = 1; d <= bits; d++) { // 进桶次数
            int[] count = new int[10];
            for (i = l; i <= r; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }
            // count[i] <=i的个数
            for (i = 1; i <= 9; i++) {
                count[i] += count[i - 1];
            }
            for (i = r; i >= l; i--) {
                j = getDigit(arr[i], d);
                help[--count[j]] = arr[i];
            }
            for (i = l; i <= r; i++) {
                arr[i] = help[i];
            }
        }
    }

    // 求数x在第d位上的值
    // 123 个位 123/1%10  十位 123/10%10 百位 123/100%10
    private static int getDigit(int x, int d) {
        return (x / (int)Math.pow(10, d - 1) % 10);
    }

    // 求最大的位数
    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        // ÷10需要几回变为0
        int bits = 1;
         while (max / 10 != 0) {
            bits++;
            max /= 10;
        }
        return bits;
    }
    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 5;
        int maxValue = 10;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
