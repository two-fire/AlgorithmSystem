package 笔试;

/*
小美要给n个朋友送礼物，第i个朋友需要送ai个礼物。商店里有m种礼物，第j种礼物的价格为bj。不同的人对的礼物有不同的要求。有的人希望礼物更加贵重，而有的人认为心意到了就行。因此第i位朋友的每种礼物价格均不能低于ci 。

请问给第i位朋友的礼物的最低价格总和为多少？（同一种礼物不能送给同一个人两份，但不同的朋友可以收到相同的礼物）。
 */
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class meituna3 {

    public static int[] minMoney(int n, int m, int[] b, int[] a, int[] c) {
        int[] ans = new int[n];
        Arrays.sort(b);
        for (int i = 1; i <= n; i++) { // n位朋友
            int idx = findX(b, c[i]);
            if (m - idx +1 < a[i]) {
                ans[i-1] = -1;
            } else {
                ans[i-1] = sum(b, idx, a[i]);
            }
        }
        return ans;
    }

    private static int sum(int[] b, int idx, int len) {
        int ans = 0;
        int i = idx;
        int x = len;
        while (x-- > 0) {
            ans += b[i++];
        }
        return ans;
    }

    private static int findX(int[] b, int x) {
        int ans = b.length;
        for (int i = b.length - 1; i > 0; i--) {
            if (b[i] < x) {
                break;
            }
            if (b[i] >= x) {
                ans = Math.min(ans, i);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] str = s.trim().split(" ");
        int n = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);

        int[] a = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            a[i] = sc.nextInt();
        }

        int[] b = new int[m + 1];
        for (int i = 1; i < m + 1; i++) {
            b[i] = sc.nextInt();
        }

        int[] c = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            c[i] = sc.nextInt();
        }
        int[] ans = minMoney(n, m, b, a, c);
        for (int v : ans) {
            System.out.print(v + " ");
        }
    }

//    private static int findX(int[] b, int l, int r, int x) {
//        if (l >= r) {
//            return l;
//        }
//        int mid = l + ((r - l) >> 1);
//        if (b[mid] > x) {
//            findX(b, l, mid - 1, x);
//        } else if (b[mid] < x) {
//            if (b[])
//            findX(b, mid + 1, r, x);
//        }
//    }


}
