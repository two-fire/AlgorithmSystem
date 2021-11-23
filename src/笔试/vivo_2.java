package 笔试;

import java.util.ArrayList;

public class vivo_2 {
    public static int workSchedule(int n) {
        if (n >= 28 || n < 1) {
            return 0;
        }
        int[] arr = new int[]{1, 2, 4};
        return process(arr, 0, n);
    }

    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        } else {
            int count = 0;
            for (int i = 0; i * arr[index] <= rest; i++) {
                count += process(arr, index + 1, rest - i * arr[index]);
            }
            return count;
        }
    }


    public static void main(String[] args) {
        System.out.println(workSchedule(3));
        int n = 4;
        int N = n + (n >> 1) + (n >> 2);
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                arr[i] = 1;
            } else if (i == n) {
                arr[i] = 2;
            } else if (i == (n + (n >> 1))) {
                arr[i] = 4;
            }
        }
        for (int i = 1; i < arr.length; i++) {
            if (i < n) {
                arr[i] = 1 + arr[i - 1];
            } else if (i > n && i < (n + (n >> 1))) {
                arr[i] = 2 + arr[i - 1];
            } else if (i > (n + (n >> 1)) && i < N) {
                arr[i] = 4 + arr[i - 1];
            }
        }
//        for (int i : arr) {
//            System.out.println(i);
//        }
    }
}
