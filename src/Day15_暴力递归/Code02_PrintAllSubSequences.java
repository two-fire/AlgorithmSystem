package Day15_暴力递归;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : LiuYan
 * @create 2021/4/9 18:55
 * 打印一个字符串的全部子序列
 *
 * 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
 * (在上面基础上加一个set去重)
 *
 */

public class Code02_PrintAllSubSequences {
    // 打印一个字符串的全部子序列
    public static List<String> subs(String str) {
        char[] chs = str.toCharArray();
        ArrayList<String> ans = new ArrayList<>();
        String path = "";
        process1(chs, 0, ans, path);
        return ans;
    }

    // chs[index]是当前正在考察的字符
    // ans是结果集
    // path是当前正在考察的字符串的前面部分
    private static void process1(char[] chs, int index, ArrayList<String> ans, String path) {
        if (index == chs.length) {
            ans.add(path);
            return;
        }
        // chs[index]不选
        process1(chs, index + 1, ans, path);
        // chs[index]选
        process1(chs, index + 1, ans, path + chs[index]);
    }

    // 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
    public static Set<String> subNoRepeat(String str) {
        char[] chs = str.toCharArray();
        HashSet<String> ans = new HashSet<>();
        String path = "";
        process2(chs, 0, ans, path);
        return ans;
    }

    private static void process2(char[] chs, int index, Set<String> ans, String path) {
        if (index == chs.length) {
            ans.add(path);
            return;
        }
        process2(chs, index + 1, ans, path);
        process2(chs, index + 1, ans, path + chs[index]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while ((str = bf.readLine()) != null) {
            List<String> ans1 = subs(str);
            Set<String> ans2 = subNoRepeat(str);
            for (String s : ans1) {
                System.out.println(s);
            }
            System.out.println("==========");
            for (String s : ans2) {
                System.out.println(s);
            }
        }
    }


}

