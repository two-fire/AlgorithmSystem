package 笔试;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class huawei2 {
    // arr 需要监控的坐标点 m 安装设备坐标
    public int getMinDis(int N, int[] arr, int[] m, int M) {

        Arrays.sort(arr);
        Arrays.sort(m);
        int[][] desc = new int[N][2];
        for (int[] d : desc) {
            Arrays.fill(d, -1);
        }
        for (int i = 0; i < N; i++) {
            int cur = arr[i];
            for (int j = 0; j < M; ) {
                if (cur <= m[j]) {
                    desc[cur][1] = m[j];
                    break;
                } else {
                    desc[cur][0] = m[j];
                    j++;
                }
            }
        }

        int ans = Math.abs(arr[0] - m[0]);
        ans = Math.max(ans, Math.abs(arr[N - 1] - m[M - 1]));

        return ans;
    }

    public static void main(String[] args) {
        huawei2 h = new huawei2();
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        String str = sc.nextLine();
        String[] s = str.trim().split(" ");
        int[] arr = new int[s.length];
        int i = 0;
        for (String t : s) {
            arr[i++] = Integer.parseInt(t);
        }
        int M = Integer.parseInt(sc.nextLine());
        String str2 = sc.nextLine();
        String[] s2 = str2.trim().split(" ");
        int[] m = new int[s.length];
        i = 0;
        for (String t : s2) {
            m[i++] = Integer.parseInt(t);
        }
        System.out.println(h.getMinDis(n, arr, m, M));
    }
}
