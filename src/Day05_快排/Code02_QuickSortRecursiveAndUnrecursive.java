package Day05_快排;

/**
 * @Author : LiuYan
 * @create 2021/3/27 15:43
 * 快速排序递归和非递归
 *
 * 快排1.0和2.0，时间复杂度都是O(N^2)
 * 快速排序3.0 时间复杂度O(N*logN)  额外空间复杂度O(logN)
 *
 * 问题：
 * 1）process调用的时候，par是否+1或者-1，要注意
 * 2）process递归，base case应该是l >= r，而不是==
 * 3）partition函数中， for (int i = l; i < r; i++) ，而不是for (int i = l; i < r - 1; i++)
 */
public class Code02_QuickSortRecursiveAndUnrecursive {
    // 快排1.0
    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }
    // 快排1.0 在arr[l..r]上划分成： <区    arr[r]    >区
    private static void process1(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int par = partition(arr, l, r);
        process1(arr, l, par - 1);
        process1(arr, par + 1, r);
    }

    // 快排1.0
    // [0..less-1] less [less+1..]
    private static int partition(int[] arr, int l, int r) {
        int less = l - 1;
        // 除了最后一个，前面排成： <=arr[r]  >arr[r]
        for (int i = l; i < r; i++) { //
            if (arr[i] <= arr[r]) {
                swap(arr, i, ++less);
            }
        }
        swap(arr, ++less, r); // 把arr[r]放到中间来
        return less;
    }

    // 快排2.0
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length - 1);
    }
    // 快排2.0 以arr[r]为划分值，在arr[l..r]上划分成： <区    =区    >区
    private static void process2(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int[] par =  netherlandFlag(arr, l, r);
        process2(arr, l, par[0] - 1);
        process2(arr, par[1] + 1, r);
    }

    // 以arr[r]为划分值，在arr[l..r]上划分成： <区    =区    >区
    // 返回等于区的两端
    private static int[] netherlandFlag(int[] arr, int l, int r) {
        int x = arr[r];
        int less = l - 1;
        int more = r; // 和code01中有区别，因为这里把最后一个囊括了
        for (int i = l; i < more;) {
            if (arr[i] < x) {
                swap(arr, i++, ++less);
            } else if (arr[i] == x) {
                i++;
            } else {
                swap(arr, i, --more);
            }
        }
        swap(arr, r, more);
        more++;
        return new int[]{ less + 1, more - 1};
    }

    // 快排3.0
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process3(arr, 0, arr.length - 1);
    }

    // 快排3.0 随机数和arr[r]交换，在arr[l..r]上划分成： <区    =区    >区
    private static void process3(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int rand = l + (int)Math.random() * (r - l + 1);
        swap(arr, rand, r);
        int[] par = netherlandFlag(arr, l, r);
        process3(arr, l, par[0] - 1);
        process3(arr, par[1] + 1, r);
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
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
            int[] arr3 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                printArray(arr3);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");

    }
}
