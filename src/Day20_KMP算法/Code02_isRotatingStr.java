package Day20_KMP算法;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author : LiuYan
 * @create 2021/8/16 20:57
 *
 * 求是不是旋转串
 * 比如 123456 旋转串可以是 234561  345612... 612345 规定只能旋转一次
 *    思路：
 *      s1长度不等于s2，不是。如果长度相等：把两个s1拼接到一起，看s2是否是其中的子串
 */
public class Code02_isRotatingStr {
    /**
     * s2能通过s1旋转得来返回true，否则false
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isRotatingStr(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        return getIndexOf(s1 + s1, s2) != -1;
    }
    // ababad
    // abad
    private static int getIndexOf(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] next = getNextArray(str2);
        int x = 0;
        int y = 0;
        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }
    // abcabcd
    // 0123456
    private static int[] getNextArray(char[] str2) {
        if (str2.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int x = 2;
        int y = 0; // 和x-1处比较的位置
        while (x < next.length) {
            if (str2[x - 1] == str2[y]) {
                next[x++] = ++y;
            } else if (y > 0) {
                y = next[y];
            } else {
                next[x++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            String[]s = str.trim().split(" ");
            System.out.println(isRotatingStr(s[0], s[1]));
        }
    }
}
