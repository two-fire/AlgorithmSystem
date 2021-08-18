package Day21_Manacher算法;

/**
 * @Author : LiuYan
 * @create 2021/8/17 7:29
 * 例题一：
 *      在字符串末尾添加任意个数字符，在包含最后一个字符的情况下，最长回文子串是多长？
 *      abc12321 -> abc12321cba  找到前面不是的部分逆序过来就可以了
 *      所以一旦某个x位置他的R刚好把最后一个字符包含进来，那他就是后面那个回文串的中心。
 *
 * 例题二：求一个字符串中最长回文串的结果
 *
 */
public class Code02_Manacher_print {
    // 例题一
    public static int manacher_len(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        char[] str = manacherString2(s);
        int[] pArr = new int[str.length];
        int R = -1;
        int C = -1;
        int end = -1;
        int len = 0;
        for (int i = 0; i < pArr.length; i++) {
            pArr[i] = R > i ? Math.min(R - i, pArr[C * 2 - i]) : 1;
            while (i - pArr[i] >= 0 && i + pArr[i] < pArr.length) {
                if (str[i - pArr[i]] == str[i + pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                end = R - 1;
                C = i;
//                max = pArr[i]; // 不能保证R更新了，max就一定更新
            }
            max = Math.max(max, pArr[i]);
            // 必须包含最后一个字符情况下最长的回文串的长度
            if (end == pArr.length - 1) {
                len = pArr[i] - 1;
                break;
            }
        }
        return 2 * s.length() - len;
    }

    // 例题二
    public static String manacher_print(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        char[] str = manacherString2(s);
        int[] pArr = new int[str.length];
        int R = -1;
        int C = -1;
        int end = -1; // 最长串R的前一个数
        for (int i = 0; i < pArr.length; i++) {
            pArr[i] = R > i ? Math.min(R - i, pArr[C * 2 - i]) : 1;
            while (i - pArr[i] >= 0 && i + pArr[i] < pArr.length) {
                if (str[i - pArr[i]] == str[i + pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
//                end = R - 1;
                C = i;
//                max = pArr[i]; // 不能保证R更新了，max就一定更新
            }
            if (max < pArr[i]) {
                max = pArr[i];
                end = R - 1;
            }
//            max = Math.max(max, pArr[i]); // 同理，R更新不一定end要更新
            }
        // 处理串,在初始字符串中的对应下标
        // aa12321
        // 0123456
        // #a#a#1#2#3#2#1#  (14-1)/2 = 6 = '1'; (4-1)/2 = 1 = 'a'
        // 012345678901234
        int index = (end - 1) / 2;
        // max - 1 最长回文串长度
        int start = index - (max - 1) + 1;
        String ans = s.substring(start, start + max - 1);
        System.out.println(ans);
        return ans;
    }

    private static char[] manacherString2(String s) {
        char[] str = s.toCharArray();
        char[] ans = new char[2 * s.length() + 1];
        int index = 0;
        for (int i = 0; i != ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : str[index++];
        }
        return ans;
    }

    // for test
    private static String getRandomStr(int posibility, int strSize) {
        char[] ans = new char[(int)(Math.random() * strSize) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * posibility) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int strSize = 20;
        int posibility = 5;
        String str = getRandomStr(posibility,strSize);
        System.out.println(str + "  len：" + manacher_len(str));
        manacher_print(str);
    }

}
