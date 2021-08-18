package Day20_KMP算法;

/**
 * @Author : LiuYan
 * @create 2021/7/19 22:32
 * 字符串s1中是否有某一个子串和 s2 一样
 * 下标为x的字符的“信息”：x前面字符串，前缀和后缀相同的字符个数（不包含前面整个字符串）
 *      e.g a b c d a b c f  对于f，前面子字符串“abcdabc” 前缀abc 后缀abc  信息为3
 */
public class Code01_KMP {
    /**
     * 如果不存在，返回-1；否则返回第一个字符串的位置
     * @param str1 长度N
     * @param str2 长度M
     * @return
     */
    private static int getIndexOf(String str1, String str2) {
        if (str1 == null || str2 == null || str2.length() < 0 || str1.length() < str2.length()) {
            return -1;
        }
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        // O(M) M <= N
        int[] next = getNextArray(s2); // 每个字符的“信息”
        // 比对到的位置
        int x = 0;
        int y = 0;
        // O(N)
        while (x < s1.length && y < s2.length) {
            if (s1[x] == s2[y]) { // 两字符相同，同时向后移动
                x++;
                y++;
            } else if (next[y] == -1) { // y =  0 x现在位置，y跳到0位置都比对不上，下一个
                x++;
            } else { // 两字符不同，y回到y前面子串前缀末尾开始比较 如上例，s2就到 d 处
                y = next[y];
            }
        }
        return y == s2.length ? x - y : -1;
    }

    private static int[] getNextArray(char[] s) {
        if (s.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[s.length];// next[x]是x之前的子串前缀、后缀串相同时的最大串长度
        next[0] = -1;
        next[1] = 0;
        int x = 2; // 当前的下标是x
        int y = 0; // 当前是y位置的值在和i-1位置的字符比较
        while (x < next.length) {
            if (s[x - 1] == s[y]) { // 在之前next[x - 1] 的基础上，再加1
                next[x++] = ++y;
            } else if (y > 0) { // 如果不同，那就把前缀串再细分
                y = next[y];
            } else {
                next[x++] = 0;
            }
        }
        return next;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


}
