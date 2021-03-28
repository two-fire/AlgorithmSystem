package Day03_基本数据结构;

/**
 * @Author : LiuYan
 * @create 2021/3/26 22:38
 * 递归求arr中的最大值
 */
public class Code08_GetMax {
    public static int getMax(int[] arr) {
        if (arr == null) {
            return -1;
        }
        return process(arr, 0, arr.length);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) {
            return arr[l];
        }
        int mid = l + ((r - l) >> 1);
        int leftmax = process(arr, l, mid - 1);
        int rightmax = process(arr, mid, r);
        return Math.max(leftmax, rightmax);
    }
}
