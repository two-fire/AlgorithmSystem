package Day15_暴力递归;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : LiuYan
 * @create 2021/4/9 20:19
 *
 * 打印一个字符串的全排列
 * 输入：abc
 * 输出：
 * abc acb bca bac cab cba
 *
 * 注意：恢复现场。每一轮之后，记得把删除的加回来
 * 思路：
 * 一、传字符串进去，然后删除一个s，剩下的进行排序。然后再返回，添到s后面。然后把s加回来，再删除第二个…
 * 二、利用交换实现。深度优先遍历，比如abc：0和0位置交换，1和1交换，2和2交换。往前，1和2交换。往前，0和1交换…
 *
 * 打印一个字符串的全部排列，要求不要出现重复的排列
 * 思路：
 *  和上面二方法类似，只是如果字符是之前试过的，就直接跳过
 *  acc：0和1交换√ 0h2交换重复，需要跳过。
 *  可以用set，也可以用数组标记：
 *      boolean[] visited = new boolean[256];
 * 			for (int i = index; i < str.length; i++) {
 * 				if (!visited[str[i]]) {
 * 					visited[str[i]] = true;
 * 					swap(str, index, i);
 * 					g2(str, index + 1, ans);
 * 					swap(str, index, i);
 *              }
 *          }
 */
public class Code03_PrintAllPermutations {
    public static List<String> permutation1(String s) {
        ArrayList<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] chs = s.toCharArray();
        ArrayList<Character> str = new ArrayList<>();
        for (char c : chs) {
            str.add(c);
        }
        String path = "";
        process1(str, ans, path);
        return ans;
    }
    private static void process1(ArrayList<Character> str, ArrayList<String> ans, String path) {
        if (str.isEmpty()) {// base case
            ans.add(path);
            return;
        }
        for (int i = 0; i < str.size(); i++) {
            char c = str.get(i);
            str.remove(i);
            process1(str, ans, path + c);
//            str.add(c); // 错误！要加到原来位置才算恢复现场！！！
            str.add(i, c); // 恢复现场
        }
    }

    public static List<String> permutation2(String s) {
        ArrayList<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] chars = s.toCharArray();
        String path = "";
        process2(chars, 0, ans);
        return ans;
    }

    private static void process2(char[] chars, int index, ArrayList<String> ans) {
        if (index == chars.length) {
            ans.add(String.valueOf(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            process2(chars, index + 1, ans);
            swap(chars, index, i); // 恢复现场
        }
    }

    // 打印一个字符串的全部排列，要求不要出现重复的排列
    public static List<String> permutation3(String s) {
        ArrayList<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] chars = s.toCharArray();
        Set<String> sets = new HashSet<>();
        process3(chars, 0, sets);
        for (String str : sets) {
            ans.add(str);
        }
        return ans;
    }

    // abc
    // a和a交换 b和b交换 c和c交换 abc
    // b和c交换 acb  还原
    // a和b交换 bac 往下a和c交换 -> bca
    // a和c交换 cba 往下b和啊交换 -> cab
    private static void process3(char[] chars, int index, Set<String> set) {
        if (index == chars.length) {
            set.add(String.valueOf(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            process3(chars, index + 1, set);
            swap(chars, index, i);
        }
    }


    // 交换i和j处字符
    private static void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }
    public static void main(String[] args) {
        String s = "abc";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }
}

