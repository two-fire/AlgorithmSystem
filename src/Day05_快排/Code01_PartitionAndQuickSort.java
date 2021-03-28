package Day05_快排;

/**
 * @Author : LiuYan
 * @create 2021/3/27 15:43
 *
 * 1、给定一个数组arr，和一个整数num。
 * 请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 *
 * 思路：
 *  如果cur <= x,cur和<=x区的后一位交换，<=x区向右扩一位；如果cur > x,下一个
 *
 * 2、给定一个数组arr，和一个整数num。
 * 请把小于num的数放在数组的左边，等于num的放中间，大于num的数放在数组的右边。
 *
 * 思路：
 * 和1类似，只是有一个大于区，一个小于区。
 * 如果cur < x,cur和<区的后一位交换，<区向右扩一位；如果cur = x,下一个；
 *      如果cur > x,cur和>区的前一位交换，>区向左扩一位
 *
 * 遇到问题：
 * 2问输出错误。原因是如果cur > x, 这时候下标不能往后移动！
 */
public class Code01_PartitionAndQuickSort {

    // 小于等于num的数放在数组的左边，大于num的数放在数组的右边
    // 返回小于等于区域的最后一个数
    // [0..less] [less+1..]
    public static int partition(int[] arr, int num) {
        int less = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= num) {
                swap(arr, i, less + 1);
                less++;
            }
        }
        return less;
    }
    // 小于num的数放在数组的左边，等于num的放中间，大于num的数放在数组的右边
    // 返回小于区的最后一个数 和 大于区的第一个数
    public static int[] netherlandsFlag(int[] arr, int num) {
        int less = -1;
        int more = arr.length;
        for (int i = 0; i < more;) {
            if (arr[i] < num) {
                swap(arr, i, less + 1);
                less++;
                i++;
            } else if(arr[i] > num) {
                swap(arr, i, more - 1);
                more--;
            } else {
                i++;
            }
        }
        return new int[]{less, more};
    }
    public static void swap(int[] arr, int i ,int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{ 9, 3, 2, 1, 5, 3, 8, 0 };
        System.out.println(partition(arr, 3));
        printArr(arr);
        arr = new int[]{ 9, 3, 2, 1, 5, 3, 8, 0 };
        int[] p = netherlandsFlag(arr, 3);
        System.out.println(p[0] + " " + p[1]);
        printArr(arr);
    }

    private static void printArr(int[] arr) {
        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
