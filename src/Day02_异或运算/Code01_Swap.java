package Day02_异或运算;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author : LiuYan
 * @create 2021/3/25 9:07
 * 如何不用额外变量交换两个数
 * 输入：
 * 1 2 3 4
 * 1 2
 * 输出：
 * 1 3 2 4
 */
public class Code01_Swap {
    public static void swap (int[] arr, int a, int b) {
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = br.readLine()) != null) {
            String[] s = str.trim().split(" "); // 一系列数
            int[] value = new int[s.length]; // 要交换的两个下标
            for (int i = 0; i < s.length; i++) {
                value[i] = Integer.parseInt(s[i]);
            }
            String indexs;
            if ((indexs = br.readLine()) != null) {
                String[] s1 = indexs.trim().split(" ");
                int[] index = new int[s1.length]; // 要交换的两个下标
                for (int i = 0; i < s1.length; i++) {
                    index[i] = Integer.parseInt(s1[i]);
                }
                swap(value, index[0], index[1]);
                printValue(value);
            }
        }
    }

    private static void printValue(int[] s) {
        for (int v : s) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
