package Day16_动态规划;

/**
 * @Author : LiuYan
 * @create 2021/4/11 16:23
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 比如 ： str = “a12b3c43def2ghi1kpm”
 * 最长回文子序列是“1234321”或者“123c321”，返回长度7
 *
 * 思路：第一种，逆序，然后和顺序的找最长公共子序列。
 * 第二种，范围尝试模型。【注意讨论开头如何，结尾如何】
 * 开头是，结尾不是
 *
 * 测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/
 */
public class Code07_PalindromeSubsequence {
    /* 逆序，然后和顺序的找最长公共子序列 */
    public static int lps1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (str.length() == 1) {
            return 1;
        }
        char[] str1 = str.toCharArray();
        char[] str2 = reverse(str1);
        return process(str1, 0, str2, 0);
    }
    // 求最长公共子序列
    private static int process(char[] str1, int i, char[] str2, int j) {
        int N = str1.length;
        if (i == N && j == N) {
            return 0;
        } else if (i == N - 1) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process(str1, i, str2, j + 1);
            }
        }  else if (j == N - 1) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process(str1, i + 1, str2, j);
            }
        } else {
            int p1 = str1[i] == str2[j] ? (1 + process(str1, i + 1, str2, j + 1)) : 0;
            int p2 = process(str1, i + 1, str2, j); // 1不是2可能是
            int p3 = process(str1, i, str2, j + 1); // 2不是1可能是
            return Math.max(p1, Math.max(p2, p3));
        }
    }
    private static char[] reverse(char[] str) {
        char[] ans = new char[str.length];
        int j = 0;
        for (int i = str.length - 1; i >= 0; i--) {
            ans[j++] = str[i];
        }
        return ans;
    }

    /* lps1改进：状态转移 */
    public static int lps2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (str.length() == 1) {
            return 1;
        }
        char[] str1 = str.toCharArray();
        char[] str2 = reverse(str1);
        int N = str1.length;
        int[][] dp = new int[N + 1][N + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = N - 1; j >= 0; j--) {
              if (i == N - 1) {
                    if (str1[i] == str2[j]) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i][j + 1];
                    }
                }  else if (j == N - 1) {
                    if (str1[i] == str2[j]) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i + 1][j];
                    }
                } else {
                    int p1 = str1[i] == str2[j] ? (1 +dp[i + 1][j + 1]) : 0;
                    int p2 = dp[i + 1][j]; // 1不是2可能是
                    int p3 = dp[i][j + 1]; // 2不是1可能是
                    dp[i][j] = Math.max(p1, Math.max(p2, p3));
                }
            }
        }
        return dp[0][0];
    }
    /* 范围尝试模型 */
    public static int lps3(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] s = str.toCharArray();
        return process3(s, 0, s.length - 1);
    }
    // 返回str[L...R]最长回文子序列
    private static int process3(char[] str, int L, int R) {
        if (L == R) {
            return 1;
        } else if (L == R - 1) { // 因为最中间可以是单独的字符
            return str[L] == str[R] ? 2 : 1;
        } else {
            int p1 = process3(str, L + 1, R); // str[R]是最终结局中的内容 str[L]不是
            int p2 = process3(str, L, R - 1); // str[R]不是 str[L]是
            int p3 = process3(str, L + 1, R - 1); // 都不是
            int p4 = str[L] != str[R] ? 0 : (2 + process3(str, L + 1, R - 1)); // 都是

            return Math.max(p1, Math.max(p2, Math.max(p3, p4)));
        }
    }
    /* 范围尝试模型优化 */
    public static int lps4(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] s = str.toCharArray();
        int N = s.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int L = 0; L < N - 1; L++) {
            dp[L][L] = 1;
            dp[L][L + 1] = s[L] == s[L + 1] ? 2 : 1;
        }
        for (int i = 2; i < N; i++) {
            int L = 0;
            int R = i;
            while (R < N) {
                int p1 = dp[L + 1][R]; // str[R]是最终结局中的内容 str[L]不是
                int p2 = dp[L][R - 1]; // str[R]不是 str[L]是
                int p3 = dp[L + 1][R - 1]; // 都是
                int p4 = s[L] != s[R] ? 0 : (2 + dp[L + 1][R - 1]); // 都不是
                dp[L++][R++] = Math.max(p1, Math.max(p2, Math.max(p3, p4)));
            }
        }
        return dp[0][N - 1];
    }

    public static void main(String[] args) {
        System.out.println(lps1("bbbab"));
        System.out.println(lps2("bbbab"));
        System.out.println(lps3("bbbab"));
        System.out.println(lps4("bbbab"));
    }
}
