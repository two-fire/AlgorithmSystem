package Day04_归并排序;

/**
 * @Author : LiuYan
 * @create 2021/3/27 12:59
 * 归并排序补充题目（难）
 * https://leetcode.com/problems/count-of-range-sum/
 * 题目描述：
 * 给定一个数组arr，两个整数lower和upper，
 * 返回arr中有多少个子数组的累加和在[lower,upper]范围上
 * 思路：
 *   sum[i]：arr[0..i]的累加和
 *   题目转换为：
 *   1）sum[i] 是否在[lower,upper]范围上
 *   2）如果sum[i]= u 求sum[0]..sum[i-1]有多少个在[u-upper, u - lower]
 *        arr[x..i] = sum[i] - sum[x] 在[lower, upper]上
 *      即：merge函数中，先看左组有多少个数满足条件，然后再正规merge
 *
 *  遇到问题：
 *   (1) for (int i = 1; i < arr.length; i++) {
 *             sum[i] = sum[i - 1] + arr[i];
 *         }
 *         在求前缀和数组中，错写成 sum[i] += sum[i - 1];
 *   (2) long min = sum[j] - upper;
 *         在merge()中，错写成: long min = sum[i] - upper;
 *   (3) j++;
 *         在merge()中，while循环最后忘记加条件
 *   (4) 测试数据超过了int范围，sum[] 还有 help[] 等要改为long类型
 */
public class Code05_CountRangeSum {
    public static int countRangeSum(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 得到前缀和数组sum
        long[] sum = new long[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        return process(sum, lower, upper, 0, sum.length - 1);
    }

    private static int process(long[] sum, int lower, int upper, int l, int r) {
        if (l == r) {
            return sum[l] >= lower && sum[l] <= upper ? 1 : 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(sum, lower, upper, l, mid)
                + process(sum, lower, upper, mid + 1, r)
                + merge(sum, lower, upper, l, mid, r);
    }

    private static int merge(long[] sum, int lower, int upper, int l, int mid, int r) {
        int res = 0;
        int i = l; // 左组的start指针
        int ir = l; // 左组的end指针
        int j = mid + 1;
        while (j <= r) {
            // 符合条件 sum[i]~sum[ir-1]
            long min = sum[j] - upper;
            long max = sum[j] - lower;
            // 找到start位置: i在符合条件的第一个处
            while (i <= mid && sum[i] < min) {
                i++;
            }
            // 找到end位置：符合条件的下一个处
            ir = i;
            while (ir <= mid && sum[ir] <= max) {
                ir++;
            }
            res += ir - i;
            j++;
        }

        // 正规merge
        long[] help = new long[r - l + 1];
        i = l;
        j = mid + 1;
        int index = 0;
        while (i <= mid && j <= r) {
            help[index++] = (sum[i] <= sum[j]) ? sum[i++] : sum[j++];
        }
        while (i <= mid) {
            help[index++] = sum[i++];
        }
        while (j <= r) {
            help[index++] = sum[j++];
        }
        for (i = 0; i < help.length; i++) {
            sum[l + i] = help[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = { 0,-2147483647,-2147483647,2147483647 };

        int lower = -564;
        int upper = 3864;
        System.out.println(countRangeSum(arr, lower, upper));
    }
}
