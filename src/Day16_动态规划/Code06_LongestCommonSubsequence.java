package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/11 15:04
 *
 * 给定两个字符串str1和str2，
 * 返回这两个字符串的最长公共子序列长度。如果不存在公共子序列，返回 0 。
 *
 * 比如 ： str1 = “a12b3c456d”,str2 = “1ef23ghi4j56k”
 * 最长公共子序列是“123456”，所以返回长度6
 *
 *
 * 链接：https://leetcode.com/problems/longest-common-subsequence/
 */
public class Code06_LongestCommonSubsequence {
    // 纯暴力
    public static int longestCommonSubsequence1(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        return process1(chs1, 0, chs2, 0);
    }
    // 返回str1[0..i] 和str2[0..j]的最长公共子序列
    private static int process1(char[] str1, int i, char[] str2, int j) {
        int N = str1.length;
        int M = str2.length;
        if (i == N || j == M) {
            return 0;
        } else if (i == N - 1) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, i, str2, j + 1);
            }
        } else if(j == M - 1) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, i + 1, str2, j);
            }
        } else { // 都不是最后一个字符
            // str1[i] 不是公共字符，str2[j]可能是
            int p1 = process1(str1, i, str2, j + 1);
            // str1[i] 可能是公共字符，str2[j]不是
            int p2 = process1(str1, i + 1, str2, j);
            // str1[i]，str2[j]都是
            int p3 = str1[i] == str2[j] ? (1 + process1(str1, i + 1, str2, j + 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    // 状态方程
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N + 1][M + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = M - 1; j >= 0; j--) {
                if (i == N - 1) {
                    if (str1[i] == str2[j]) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i][j + 1];
                    }
                } else if(j == M - 1) {
                    if (str1[i] == str2[j]) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i + 1][j];
                    }
                } else { // 都不是最后一个字符
                    // str1[i] 不是公共字符，str2[j]可能是
                    int p1 = dp[i][j + 1];
                    // str1[i] 可能是公共字符，str2[j]不是
                    int p2 = dp[i + 1][j];
                    // str1[i]，str2[j]都是
                    int p3 = str1[i] == str2[j] ? (1 + dp[i + 1][j + 1]) : 0;
                    dp[i][j] = Math.max(p1, Math.max(p2, p3));
                }
            }
        }
        return dp[0][0];
    }


    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence1("a12b3c456d","1ef23ghi4j56k"));
        System.out.println(longestCommonSubsequence2("a12b3c456d","1ef23ghi4j56k"));
    }
}
