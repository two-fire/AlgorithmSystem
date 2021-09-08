package Day22_bfprt算法和蓄水池算法;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 在无序数组中求第k小的数
 * 1） 改写快排，< = > 区域，求k-1位置处的数
 * 2） bfprt: 代替了随机快排 随机的过程，制定一系列规则选出划分值
 * 3） 大根堆：k长度从大到小排列，之后来个新的数就和最大值比较，如果小，就交换重排序。最后得到的就是最小的k个数
 */
public class Code01_FindMinKth {
    // 利用大根堆，时间复杂度O(N*logK)
    public static int minKth1(int[] array, int k) {
        // 逆序
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < k; i++) {
            maxHeap.add(array[i]);
        }
        for (int i = k; i < array.length; i++) {
            if (array[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(array[i]);
            }
        }
        return maxHeap.peek();
    }
    // 利用bfprt算法，时间复杂度O(N)
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }
    // arr[L..R] 如果排序，位于index的数是什么，返回 默认index不越界
    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        // 五个一组；组内排序；组中位数拿出来组成新数组；返回新数组中位数
        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, L, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, R, index);
        }
    }

    private static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L +1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        // mArr中找到中位数
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    private static int getMedian(int[] arr, int L, int R) {
        insertSort(arr, L, R); // 插入排序常数时间最短
        return arr[(L + R) / 2];
    }

    // 改写快排，时间复杂度O(N)
    // k >= 1
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return process(arr, 0, arr.length - 1, k - 1);
    }

    private static int process(int[] arr, int L, int R, int x) {
        if (L == R) {
            return arr[L];
        }
        int pivot = arr[L + (int)(Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (x >= range[0] && x <= range[1]) {
            return arr[x];
        } else if (x < range[0]) {
            return process(arr, L, range[0] - 1, x);
        } else {
            return process(arr, range[1] + 1, R, x);
        }
    }

    // [L,R] 插入排序
    private static void insertSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        for (int i = L; i < more;) {
            if (arr[i] < pivot) {
                swap(arr, i++, ++less);
            } else if (arr[i] > pivot) {
                swap(arr, i, --more);
            } else {
                i++;
            }
        }
        return new int[]{less + 1, more - 1};
    }
    private static int[] copyArray(int[] arr) {
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
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }
    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
