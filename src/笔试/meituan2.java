package 笔试;

/*
在一场持续300分钟的算法竞赛中，小美在75分钟后便没有了提交。而在不久之后的另一场比赛中，小美225分钟后便没有了提交。于是被小团调侃用一场比赛的时间打了两场比赛。

       小团打了n场比赛，每场比赛持续时间为m分钟，第i场比赛中，小团ai分钟后便没有了提交。请统计，有多少无序对(i,j)满足ai+aj≤m，以方便小美来调侃小团。
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class meituan2 {
    int ans = 0;
    int m;

    // n场 m分钟
    public int pairNum(int m, int[] arr) {
        this.m = m;
        ArrayList<Integer> list = new ArrayList<>();
        Arrays.sort(arr);
        dfs(arr, 1, list);
        return ans;
    }

    private void dfs(int[] arr, int idx, ArrayList<Integer> list) {
        if (list.size() == 2) {
            ans++;
            return;
        }
        for (int i = idx; i < arr.length; i++) {
            if (!list.isEmpty()) {
                if (list.get(0) + arr[i] > m) {
                    break;
                }
            }
            list.add(arr[i]);
            dfs(arr, i + 1, list);
            list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] str = s.trim().split(" ");
        int n = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);
        int[] arr = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            arr[i] = sc.nextInt();
        }
        meituan2 a = new meituan2();
        System.out.println(a.pairNum(m, arr));
    }
}
