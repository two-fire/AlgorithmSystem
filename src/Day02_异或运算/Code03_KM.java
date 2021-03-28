package Day02_异或运算;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author : LiuYan
 * @create 2021/3/26 10:44
 * 一个数组中有一种数出现K次，其他数都出现了M次
 * (M > 1,  K < M)
 * 找到出现了K次的数
 * 要求，额外空间复杂度O(1)，时间复杂度O(N)
 * <p>
 * 思路：每一位如果是m的倍数，就说明出现k次的数在这一位上是0；否则是1
 */
public class Code03_KM {
    // 0出现了5次， k = 3，最后应该返回-1
    public static int findKNum(int[] arr, int k, int m) {
        int[] help = new int[32];
        for (int n : arr) { // 找到每个数的每一位
            for (int i = 0; i < 32; i++) {
                    help[i] += (n >> i) & 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            // [0 0 0 0 1 1 1 1 1] k=3 m=5 没有满足的条件，应该返回-1
            // 但0会命中这条分支，最后会返回0
            if (help[i] % m == 0) {
                continue;
            }
            if (help[i] % m == k) { // 出现k次的数在这一位上是1
                ans |= (1 << i);
            } else {
                return -1;
            }
            if (ans == 0) {
                int c = 0;
                for (int n : arr) {
                    if(n == 0) {
                        c++;
                    }
                }
                if (c != k) {
                    return -1;
                }
            }
        }
        return ans;
    }

    // for test
    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int n : arr) {
            if (map.containsKey(n)) {
                map.put(n, map.get(n) + 1);
            } else {
                map.put(n, 1);
            }
        }
        for (int n : map.keySet()) {
            if (map.get(n) == k) {
                return n;
            }
        }
        return -1;
    }

    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // 2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[k + (numKinds - 1) * m];
        int index = 0;
        for (; index < k; index++) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum)); // 每次ran出来新的数
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了，打乱
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    // [-range, +range]
    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    public static void main(String[] args) {
        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = findKNum(arr, k, m);
            if (ans1 != ans2) {
                for (int n : arr) {
                    System.out.print(n + " ");
                }
                System.out.println();
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");

    }
}
