package Day04_归并排序;

/**
 * @Author : LiuYan
 * @create 2021/3/26 22:49
 * 在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
 * 例子： [1,3,4,2,5]
 * 1左边比1小的数：没有
 * 3左边比3小的数：1
 * 4左边比4小的数：1、3
 * 2左边比2小的数：1
 * 5左边比5小的数：1、3、4、 2
 * 所以数组的小和为1+1+3+1+1+3+4+2=16
 *
 * 思路：二分，求每一段数组的小和。==> 求右边比自己大的数
 *      merge的时候，根据左组查看右组有几个数比自己大。相等拷贝右边
 */
public class Code02_SmallSum {
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) { // base case
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }

    // 返回arr[l..r]上的小和
    public static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = l;
        int j = mid + 1;
        int index = 0;
        int ans = 0;
        while (i <= mid && j <= r) {
            if (arr[i] < arr[j]) {
                // 注意！要记得乘以 arr[i]
                ans += (r - j + 1) * arr[i];
                help[index++] = arr[i++];
            } else {
                help[index++] = arr[j++];
            }
        }
        while (i <= mid) {
            help[index++] = arr[i++];
        }
        while (j <= r) {
            help[index++] = arr[j++];
        }
        for (index--; index >= 0; index--) {
            arr[r--] = help[index];
        }
        return ans;
    }

    // for test
    public static int rightSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                ans += (arr[j] < arr[i]) ? arr[j] : 0;
            }
        }
        return ans;
    }

    // for test
    // 随机生成数组
    public static int[] randArray(int maxNum, int maxLen) {
        int[] arr = new int[(int)Math.random() * maxLen]; // 0 ~ maxlen - 1
        for (int i = 0; i < arr.length; i++) {
            arr[i] = randNum(maxNum);
        }
        return arr;
    }
    // for test
    // 返回-max ~ max随机数
    public static int randNum(int maxNum) {
        return (int)Math.random() * (maxNum + 1) - (int)Math.random() * (maxNum + 1);
    }
    // for test
    public static int[] copyArr(int[] arr) {
        int[] arr1 = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = arr[i];
        }
        return arr1;
    }
    // for test
    public static void print(int[] arr) {
        for (int v : arr) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int maxNum = 100;
        int maxLen = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randArray(maxNum, maxLen);
            int[] arr1 = copyArr(arr);
            if (rightSum(arr1) != smallSum(arr)) {
                succeed = false;
                print(arr);
                print(arr1);
                break;
            }
        }
        System.out.println(succeed ? "success!" : "error!");
    }
}
