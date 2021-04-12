package Day16_动态规划;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author : LiuYan
 * @create 2021/4/10 21:57
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
 * 返回需要至少多少张贴纸可以完成这个任务。
 * 例子：str= "babac"，arr = {"ba","c","abcd"}
 * ba + ba + c  3  abcd + abcd 2  abcd+ba 2
 * 所以返回2
 *
 * 每种贴纸无穷张，爱怎么剪怎么剪，只要拼出str即可。
 *  思路：每次用一张贴纸去减字符串，直到减不动截枝向上返回。
 *  比如，
 *  babac -> 被ba可以减到bac， -> 被c可以减到baba， -> 被abcd可以减到ba
 *  然后对于bac，再用那三张去减……
 *
 * 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
 *
 * 因为process中，变化的参数是字符串cur， 状态转换不好使用，因为摸不清变化范围，空间消耗太多。
 * 所以只需要建立词频表，进行傻缓存。不需要严格表结构的dp
 *
 */
public class Code05_StickersToSpellWord {
    public static int minStickers(String aim, String[] stickers) {
        if (aim == null || stickers == null || stickers.length == 0) {
            return 0;
        }
        int ans = process1(aim, stickers);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    // 当前字符串为cur，贴纸stickers
    public static int process1(String cur, String[] stickers) {
        if (cur.length() == 0) {
            return 0;
        }
        int p = Integer.MAX_VALUE;
        for (String sticker : stickers) {
            String next = minus(cur, sticker);
            if (next.length() < cur.length()) {
                p = Math.min(p, process1(next, stickers));
            }
        }
        return p + (p == Integer.MAX_VALUE ? 0 : 1);
    }
    private static String minus(String cur, String sticker) {
        char[] chs = cur.toCharArray();
        char[] stickers = sticker.toCharArray();
        int[] arr = new int[26]; // 0~25 代表a~z
        for (char c : chs) {
            arr[c - 'a']++;
        }
        for (char s : stickers) {
            arr[s - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (arr[i] > 0) {
                for (int j = 0; j < arr[i]; j++) {
                    builder.append((char)(i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    // 词频表
    public static int minStickers2(String aim, String[] stickers) {
        if (aim == null || stickers == null || stickers.length == 0) {
            return 0;
        }
        int N = stickers.length;
        int[][] count = new int[N][26]; // 贴纸的词频表  一行代表一个贴纸
        for (int i = 0; i < N; i++) {
            char[] chars = stickers[i].toCharArray();
            for (char c : chars){
                count[i][c - 'a']++;
            }
        }

        int ans = process2(aim, count);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process2(String aim, int[][] count) {
        if (aim.length() == 0) {
            return 0;
        }
        int p = Integer.MAX_VALUE;
        char[] arr = aim.toCharArray();
        int[] acount = new int[26];
        for (char c : arr) {
            acount[c - 'a']++; // 目标字符串转字符
        }
        for (int i = 0; i < count.length; i++) {
            // 最关键的优化(重要的剪枝!这一步也是贪心!)
            if (count[i][arr[0] - 'a'] > 0) { // 从一开始就能减掉aim第一字符的贴纸往下走。
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (acount[j] > 0) {
                        int num = acount[j] - count[i][j];
                        for (int k = 0; k < num; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                if (builder.length() < arr.length) {
                    p = Math.min(p, process2(builder.toString(), count));
                }
            }
        }
        return p + (p == Integer.MAX_VALUE ? 0 : 1);
    }

    // 傻缓存
    public static int minStickers3(String aim, String[] stickers) {
        if (stickers == null || stickers.length == 0 || aim == null) {
            return 0;
        }
        int N = stickers.length;
        int[][] count = new int[N][26]; // 词频表
        for (int i = 0; i < N; i++) {
           for (char c : stickers[i].toCharArray()) {
               count[i][c - 'a']++;
           }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        int ans = process3(aim, count, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    private static int process3(String cur, int[][] stickers, HashMap<String, Integer> dp) {
        if (dp.containsKey(cur)) {
            return dp.get(cur);
        }
        if (cur.length() == 0) {
            return 0;
        }
        char[] chars = cur.toCharArray();
        int[] count = new int[26];
        for (char c : chars) {
            count[c - 'a']++;
        }
        int p = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            if (sticker[chars[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (count[j] > 0) {
                        int num = count[j] - sticker[j];
                        if (num > 0) {
                            for (int k = 0; k < num; k++) {
                                builder.append((char) (j +'a'));
                            }
                        }
                    }
                }
                if (builder.length() < cur.length()) {
                    p = Math.min(p, process3(builder.toString(), stickers, dp));
                }
            }
        }
        int ans = p + (p == Integer.MAX_VALUE ? 0 : 1);
        dp.put(cur, ans);
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(minStickers("babac",new String[]{"ba","c","abcd"}));
        System.out.println(minStickers2("babac",new String[]{"ba","c","abcd"}));
        System.out.println(minStickers3("babac",new String[]{"ba","c","abcd"}));
    }
}
