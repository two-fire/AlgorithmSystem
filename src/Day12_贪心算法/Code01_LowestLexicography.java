package Day12_贪心算法;

import java.util.*;

/**
 * @Author : LiuYan
 * @create 2021/4/6 16:15
 * 给定一个由字符串组成的数组strs，
 * 必须把所有的字符串拼接起来，
 * 返回所有可能的拼接结果中，字典序最小的结果
 *
 * 思路：如果a拼接上b后的字典序，小于b拼接上a后的字典序，那么a放在前；否则b放前。
 */
public class Code01_LowestLexicography {
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, new MyComp());
        String ans = "";
        for (int i = 0; i < strs.length; i++) {
            ans += strs[i];
        }
        return ans;
    }
    public static class MyComp implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    // 暴力解
    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> treeSet = process(strs);
        return treeSet.first();
    }

    // TreeSet有序表 自然会按照字典序最小的排在前面
    private static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add(""); // 必须要有这句，否则空的时候，会认为没法拼接
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndexString(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur : next) {
                ans.add(first + cur);
            }
        }
        return ans;
    }
    // 把数组中下标是index的字符串移除
    private static String[] removeIndexString(String[] strs, int index) {
        String[] arr = new String[strs.length - 1];
        int j = 0;
        for (int i = 0; i < strs.length; i++) {
            if (i != index) {
                arr[j++] = strs[i];
            }
        }
        return arr;
    }

    // for test
    public static void main(String[] args) {
        int testTime = 1000;
        int strMax = 5;
        int strLen = 5;
        int arrLen = 4;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String[] strs1 = generateRandStringArray(arrLen, strLen, strMax);
            String[] strs2 = copyStringArray(strs1);
            if (!lowestString1(strs1).equals(lowestString2(strs2))) {
                for (String str : strs1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                throw new RuntimeException("oops!");
            }
        }
        System.out.println("finish!");
    }
    // for test
    private static String[] copyStringArray(String[] strs1) {
        String[] ans = new String[strs1.length];
        for (int i = 0; i < strs1.length; i++) {
            ans[i] = strs1[i];
        }
        return ans;
    }
    // for test
    private static String[] generateRandStringArray(int arrLen, int strLen, int strMax) {
        String[] ans = new String[(int)(Math.random() * arrLen + 1)];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandString(strLen, strMax);
        }
        return ans;
    }
    // for test
    private static String generateRandString(int strLen, int strMax) {
        char[] ans = new char[(int)(Math.random() * strLen + 1)];
        for (int i = 0; i < ans.length; i++) {
            int value = (int)(Math.random() * strMax);
            ans[i] = (Math.random() <= 0.5) ? (char)(65 + value) : (char)(97 + value);
        }
        return String.valueOf(ans);
    }
}
