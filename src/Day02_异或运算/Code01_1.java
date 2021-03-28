package Day02_异或运算;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @Author : LiuYan
 * @create 2021/3/25 20:45
 * 题目
 * * 给定一个无序的整数类型数组，求最长的连续元素序列的长度。
 * * 例如：
 * * 给出的数组为[100, 4, 200, 1, 3, 2],
 * * 最长的连续元素序列为[1, 2, 3, 4]. 返回这个序列的长度：4
 * * 你需要给出时间复杂度在O（n）之内的算法。语言不限。
 */
public class Code01_1 {
    public static int fun(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return 1;
        }
        int max = Integer.MIN_VALUE;
        for (int n : arr) {
            if (n > max) {
                max = n;
            }
        }
        int[] help = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            help[arr[i]] = 1;
        }
        int count = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < help.length - 1; i++) {

            if (help[i] == 1) {
                count++;
                if (help[i+1] != 1) {
                    queue.add(count);
                    count = 0;
                }
            }
        }
        return queue.poll();
    }
    /*
    //for test
    public static int[] copy(int[] arr) {
        int[] arr2 = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }
        return arr2;
    }

    //for test
    public static int rightTest(int[] arr) {
        Arrays.sort(arr);
        int c = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] + 1 == arr[i + 1]) {
                c++;
            } else {
                c++;
                queue.add(c);
                c=0;
            }
        }
        return queue.poll();
    }
    */
    public static void main(String[] args) {
        /*int testTime = 10000;
        int max = 20;
        int len = 30;
        for (int i = 0; i < testTime; i++) {
            int L = (int)Math.random() * len;
            int[] arr = new int[L]; // 0 len-1
            for (int j = 0; j < L; j++) {
                arr[j] = (int)Math.random() * max;
            }
            int[] arr2 = copy(arr);
            if (fun(arr) != rightTest(arr2)) {
                System.out.println("error");
            }

        }
        System.out.println("success");*/
        int[] arr = new int[]{ 100, 4, 200, 1, 3, 2 };
        System.out.println(fun(arr));
    }
}
