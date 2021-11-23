package 笔试;

/*
* 深信服
 */
public class Emp_2 {
    public static String dec(String str, int up_chr_offset, int low_chr_offset) {
        if (low_chr_offset > 25 || low_chr_offset < -25 || up_chr_offset > 25 || up_chr_offset < -25) {
            return null;
        }
        char[] ans = new char[str.length()];
        char[] chars = str.toCharArray();
        char[] ups = new char[26];
        char[] lows = new char[26];
        for (int i = 0; i < 26; i++) {
            ups[i] = (char) ('A' + i);
            lows[i] = (char) ('a' + i);
        }
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            if (isUp(chars[i])) {
                index = (26 + (chars[i] + up_chr_offset - 'A')) % 26;
                ans[i] = ups[index];
            } else if (isLow(chars[i])) {
                index = (26 + (chars[i] + low_chr_offset - 'a')) % 26;
                ans[i] = lows[index];
            } else {
                ans[i] = ' ';
            }
        }
        return String.valueOf(ans);
    }

    private static boolean isUp(char c) {
        return (int) c >= (int) 'A' && (int) c <= (int) 'Z';
    }

    private static boolean isLow(char c) {
        return (int) c >= (int) 'a' && (int) c <= (int) 'z';
    }

    public static void main(String[] args) {
        System.out.println((int) 'a' + " " + (('z' + 1 - 'a') % 26));
        System.out.println('B' >= (int) 'A' && (int) 'B' <= (int) 'Z');
        System.out.println(dec("Z", 1, -1));
    }
}
