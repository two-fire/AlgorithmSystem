package Day17_滑动窗口;

import java.util.LinkedList;

/**
 * @Author : LiuYan
 * @create 2021/4/20 19:44
 * 滑动内最大值和最小值的更新结构
 *  窗口不管L还是R滑动之后，都会让窗口呈现新状况，
 *  如何能够更快的得到窗口当前状况下的最大值和最小值？
 *  最好平均下来复杂度能做到O(1)
 *  利用单调双端队列！
 *
 * 假设一个固定大小为W的窗口，依次划过arr，
 * 返回每一次滑出状况的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7], W = 3
 * 返回：[5,5,5,4,6,7]
 *
 * 思路：双端队列中从大到小排列。遍历数组，如果cur比队列最后一个大，就把队列中小的都取出来，然后把cur放进去。
 * 然后每3个取最左边，就是max。如果刚好是三个中的最左边，就弹出，否则只取不弹。
 */
public class Code01_SlidingWindowMaxArray {
    public static int[] getMaxWindow1(int[] arr, int W) {
        if (arr == null || arr.length < W || W < 1) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - W + 1];
        for (int i = 0; i <= N - W; i++) {
            res[i] = arr[i];
            for (int j = i + 1; j < i + W; j++) {
                if (arr[j] > res[i]) {
                    res[i] = arr[j];
                }
            }
        }
        return res;
    }

    public static int[] getMaxWindow2(int[] arr, int W) {
        if (arr == null || W < 1 || arr.length < W) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - W + 1];
        // 从大到小排列，如果放进去的数更大，就把小的取出，放入大的
        LinkedList<Integer> linkedList = new LinkedList<>();
        int R = W - 1;
        int index = 0;
        for (int i = 0; R < N;) {
            while (i <= R) {
                while (!linkedList.isEmpty() && arr[i] > linkedList.peekLast()) {
                    linkedList.pollLast();
                }
                linkedList.add(arr[i]);
                i++;
            }
            if (arr[index] == linkedList.peekFirst()) { // 最大值是即将移除的数
                res[index++] = linkedList.pollFirst();
            } else {
                res[index++] = linkedList.peekFirst();
            }
            R++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
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

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow1(arr, w);
            int[] ans2 = getMaxWindow2(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
//        int[] arr = new int[]{4,3,5,4,3,3,6,7};
//        getMaxWindow2(arr,3);
    }

}
