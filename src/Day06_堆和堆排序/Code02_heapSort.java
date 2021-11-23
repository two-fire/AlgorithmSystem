package Day06_堆和堆排序;

import java.util.Arrays;

/**
 * @Author : LiuYan
 * @create 2021/3/28 11:4
 * 堆排序
 * 1. 先让整个数组都变成大根堆结构，建堆：
 *      从上到下 O(n*logn)
 *      从下到上 O(n)   把一个完全二叉树调整为大根堆，每个节点都往下调整
 * 2. 0位置和n-1的数交换，size-- n个数进行后，O(n*logn)
 *
 */
public class Code02_heapSort {
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // O(n*logn) 从上到下
//        for (int i = 0; i < arr.length; i++) {
//            heapInseart(arr, i);
//        }
        // O(n) 从下到上
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        // O(N*logN)
        while (heapSize > 1) { // O(n)
            swap(arr, 0, heapSize - 1); // O(1)
            heapify(arr, 0, --heapSize); // O(logn)
        }

    }

    // 向下调整
    private static void heapify(int[] arr, int index, int heapSize) {
        int i = index * 2 + 1; // 左孩子
        while (i < heapSize) {
            int largest = (i + 1 < heapSize && arr[i + 1] > arr[i]) ? i + 1 : i;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            i = index * 2 + 1;
        }
    }
    // 向上调整
    private static void heapInseart(int[] arr, int index) {
        int p = (index - 1) / 2;
        while (p >=0 && arr[p] < arr[index]) {
            swap(arr, index, p);
            index = p;
            p = (index - 1) / 2;
        }
    }
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    // for test
/*
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
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
*/
    public static void main(String[] args) {
        heapify(new int[]{7,6,2,3,8},0,5);
        heapSort(new int[]{7,6,2,3,8});
        System.out.println();
    }
}
