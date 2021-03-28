package Day04_归并排序;

/**
 * @Author : LiuYan
 * @create 2021/3/26 22:48
 * 归并排序 时间O(N*LogN) 额外空间复杂度O(N)
 * 递归：二分法，一个辅助数组用来合并。左边大或者相同拷贝左边
 * 非递归：步长 1，2，4... 然后左右组进行比较。一个辅助数组进行merge。右边可以不够步长，但没有了就停
 */
public class Code01_MergeSort {
    // 递归方法实现
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        process(arr, l, mid);
        process(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = l;
        int j = mid + 1;
        int index = 0;
        // 把小的数放到help中
        while (i <= mid && j <= r) {
//            help[index++] = Math.max(arr[i++], arr[j++]); // 错误
            help[index++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];
        }
        // 剩下的全部加到help数组后面
        while (i <= mid) {
            help[index++] = arr[i++];
        }
        while (j <= r) {
            help[index++] = arr[j++];
        }
        // 拷贝回去
        for (index--; index >= 0; index--) {
            arr[r--] = help[index];
        }
//        for (index = 0; index < help.length; index++) {
//            arr[l + index] = help[index];
//        }
    }

    // 非递归方法实现
    public static void mergeSort2(int[] arr) {
        if (arr == null ||arr.length < 2) {
            return;
        }
        int step = 1;
        while (step < arr.length) { // step >= 数组长度，结束
            int l = 0; // 左组开始位置
            int r = step; // 右组开始位置
            // 每一轮
            while (r < arr.length) { // 右组没了停
                // 右组不够,右组的右边界就是arr.length - 1
                int rr = Math.min(r + step - 1, arr.length - 1);
                // 左组的右边界
                int lr = r - 1;
                merge(arr, l, lr, rr);
                // 更新位置，l和r都跳step
                l = rr + 1;
                r = l + step;
            }
            // 防止溢出
            if (step > arr.length / 2) {
                break;
            }
            step <<= 1; // 步长扩大两倍
        }
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
        int testTime = 50000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
