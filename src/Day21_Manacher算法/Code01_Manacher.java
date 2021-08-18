package Day21_Manacher算法;

/**
 * @Author : LiuYan
 * @create 2021/8/16 7:13
 *
 * Manacher算法：找到最长回文子串 o(n)
 * 常规算法：（暴力解）
 *      方法一：f以每个位置作为中心，看左右能不能扩出去。其中，每两个位置之间要假设有个虚轴向两端扩。否则不能找到偶数回文。
 *      方法二：把原始串处理。把每个字符中加一个字符。比如12212 => #1#2#2#1#2#
 *         不会出现虚字符不会跟实字符比较，所以虚字符可以是原字符串中字符。
 *      时间复杂度：aaaa #a#a#a#a# 0 1 2 3 4 3 2 1 O(n^2)
 * 概念：
 *  abc12321def  对于12321
 *  回文直径 5 半径 3
 *  回文半径数组parr  处理串左右两边扩，从左往右计算每个位置扩的长度，放到数组里
 *  最右回文边界  int R 一开始为-1  只要我扩出来的右边界比R大，就更新
 *  C 取得最右回文边界时候的中心点是哪  和R伴生
 *
 *  Manacher算法流程：
 *      处于 i 位置
 *      1）i 没有被R罩住 暴力扩，不优化。就往左右扩
 *      2）i 被R罩住 此时C一定在i左边，一定存在关于C对称的i',同时i'左边就是左边界L。【特殊情况，i和R一起，不影响】
 *          ① i' 扩出来的区域，在LR内 => i扩的区域和i'一样大 o(1)
 *              因为i的回文区域是i'的逆序，则至少是i'的大小。而i的区域左右边界不等，所以肯定等于i'大小
 *          ② i'回文区域已经跑到LR外了 => i到r就是回文半径 o(1)
 *          ③ i'的回文区域左边界和L压线 => 至少是i'的区域大小
 *      时间复杂度：对于1），只要成功R就增大，不会退，所以O(N)
 *
 *
 */
public class Code01_Manacher {
    // 找到最长回文子串
    public static int manacher(String str) {
        if (str == null || str.length() ==0) {
            return 0;
        }
        // 变为处理串
        char[] chars = manacherString1(str);
        // 回文半径数组
        int[] pArr = new int[chars.length];
        int C = -1;
        // 代码中，R是扩成功后的下一个位置
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < chars.length; i++) {
            // i 扩出来的区域至少是多大
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            // 暴力扩
            while (i + pArr[i] < chars.length && i - pArr[i] > -1) {
                if (chars[i + pArr[i]] == chars[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    private static char[] manacherString2(String str) {
        char[] charArray = str.toCharArray();
        char[] ans = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != ans.length; i++) {
            ans[i] = (1 & i) == 0 ? '#' : charArray[index++];
        }
        return ans;
    }

    private static char[] manacherString1(String str) {
        char[] charArray = str.toCharArray();
        char[] ans = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < ans.length; i++) {
            ans[i] = '#';
        }
        for (int i = 0; i < charArray.length; i++) {
            ans[index * 2 + 1] = charArray[i];
            index++;
        }
        return ans;
    }
    // for test
    public static int right(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int max = 0;
        // 1 #1#
        // 11 #1#1#
        char[] chars = manacherString2(str); // 变为处理串
        for (int i = 0; i < chars.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < chars.length && chars[L] == chars[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }


    private static String getRandomStr(int posibility, int strSize) {
        char[] ans = new char[(int)(Math.random() * strSize) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * posibility) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
//        int testTime = 500000;
//        int strSize = 20;
//        int posibility = 5;
//        System.out.println("test begin");
//        for (int i = 0; i < testTime; i++) {
//            String str = getRandomStr(posibility,strSize);
//            if (manacher(str) != right(str)) {
//                System.out.println("oops!");
//            }
//        }
//        System.out.println("test finish");
        System.out.println(manacher("deaecadae"));
    }


}
