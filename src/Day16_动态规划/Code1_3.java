package Day16_动态规划;

import java.util.Scanner;

/**
 * @Author : LiuYan
 * @create 2021/4/18 21:14
 */
public class Code1_3 {
    public static String isEqual(String a, String b) {
        char[] chsA = a.toCharArray();
        char[] chsB = b.toCharArray();
        if ((a.length() & 1) == 1) {
            for (int i = 0; i < chsB.length; i++) {
                if (chsA[i] != chsB[i]) {
                    return "NO";
                }
            }
            return "YES";
        } else {
            int N = chsA.length;
            return process(chsA,0,N - 1, chsB, 0,N - 1) ?  "YES" : "NO";
        }
    }

    // la..ra  lb..rb
    private static boolean process(char[] a,int la, int ra, char[] b,int lb,int rb) {
        if (ra - la == 0 || rb - lb == 0) {
            return a[la] == b[lb];
        } else if (ra < la || rb < lb || rb >= b.length || ra >= a.length ) {
            return true;
        }
        int midA = la + ((ra - la) >> 1);
        int midB = lb + ((rb - lb) >> 1);
        boolean p1 = process(a, la, midA, b, lb, midB);
        boolean p2 = process(a, midA + 1, ra, b, lb, midB);
        boolean p3 = process(a, la, midA, b, midB + 1, rb);
        boolean p4 = process(a, midA + 1, ra, b, midB + 1, rb);
        return p1 || p2 || p3 || p4;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T * 2; i++) {
            String a = sc.next();
            String b = sc.next();
            System.out.println(isEqual(a, b));
        }
    }
}
