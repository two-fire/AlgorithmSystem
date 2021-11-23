package 笔试;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * 老张是一个工厂里的工人。他每天的工作就是坐在工厂流水线旁检查产品的质量。
 * 一天，流水线运来了一批产品，一共有n个，每个产品的质量用一个正整数ai表示。
 * 评价这些产品的好坏有一个“一致程度”的指标，“一致程度”指的是一批产品中质量相同的产品的最大个数。
 * 例如：如果一批产品的质量是“3 2 1 2 2 3”，质量为“1”的产品有1个
 * ，质量为“2”的产品有3个，质量为“3”的产品有2个，那么这批产品的“一致程度”就是3。
 * 现在老张需要从这些产品中选出一段连续的序列，使得该序列的“一致程度”与总的n个产品的“一致程度”相同，
 * 同时保证选出的序列长度最短。你能写一个程序实现这个操作吗？
 */
public class jd1 {
    public static int[] minSequence(int n, int[] quality) {
        HashSet<Integer> set = new HashSet<>();
        int maxNum = quality[0];
        for (int i = 0; i < quality.length; i++) {
            set.add(quality[i]);
            maxNum = Math.max(quality[i],maxNum);
        }
        int[] arr = new int[maxNum+1];
        int max = quality[0];
        int maxCount = 1;
        for (int i = 0; i < arr.length; i++) {
           arr[quality[i]]++;
           if (arr[quality[i]] > maxCount) {
               max = quality[i];
               maxCount = arr[quality[i]];
           }
        }

        int max2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != max && arr[i] == maxCount ) {
                max2 = i;
                break;
            }
        }
        int l = 0;
        int r = quality.length - 1;
        int[] ans = new int[2];
        boolean[] finded = new boolean[2];
        while (l <= r) {
            if (quality[l] > max2) {
                l++;
            } else if (quality[l] == max2) {
                if (!finded[0]) ans[0] = l++;
                maxCount--;
                finded[0] = true;
            } else if (quality[l] < max2) {
                l++;
            }
            if (quality[r] > max2) {
                r--;
            } else if (quality[r] == max2) {
                if (!finded[1]) ans[1] = r--;
                maxCount--;
                finded[1] = true;
            } else if (quality[r] < max2) {
                r--;
            }
            if (maxCount == 0) {
                break;
            }
        }
        return ans;
    }
    public static int[] minSequence2(int n, int[] quality) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = quality[0];
        for (int i = 0; i < quality.length; i++) {
            if (map.isEmpty() || !map.containsKey(quality[i])) {
                map.put(quality[i], 1);
            } else {
                map.put(quality[i], map.get(quality[i]) + 1);
            }
            if (map.get(quality[i]) > map.get(max)) {
                max = quality[i];
            }
        }
        int maxCount = map.get(max);

        int l = 0;
        int r = quality.length - 1;
        int[] ans = new int[2];
        boolean[] finded = new boolean[2];
        while (l <= r) {
            if (quality[l] > max) {
                l++;
            } else if (quality[l] == max) {
                if (!finded[0]) ans[0] = l++;
                maxCount--;
                finded[0] = true;
            } else if (quality[l] < max) {
                l++;
            }
            if (quality[r] > max) {
                r--;
            } else if (quality[r] == max) {
                if (!finded[1]) ans[1] = r--;
                maxCount--;
                finded[1] = true;
            } else if (quality[r] < max) {
                r--;
            }
            if (maxCount == 0) {
                break;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String space = sc.nextLine();
        String str = sc.nextLine();
        String[] s = str.trim().split(" ");
        int[] arr = new int[s.length];
        int i = 0;
        for (String v : s) {
            arr[i++] = Integer.parseInt(v);
        }
        int[] ans = minSequence2(n, arr);
        System.out.println(ans[0] + " " + ans[1]);
    }
}
