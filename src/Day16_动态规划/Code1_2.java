package Day16_动态规划;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author : LiuYan
 * @create 2021/4/18 20:30
 */
public class Code1_2 {
    public static class Info {
        int times;
        int score;
        public Info(int t, int s) {
            times = t;
            score = s;
        }
    }
    public static class myComp implements Comparator<Info> {
        @Override
        public int compare(Info o1, Info o2) {
            return o1.times != o2.times ? o1.times - o2.times : o2.score - o1.score;
        }
    }

    public static int minNum(int N, int[] arr, int[] score) {
        PriorityQueue<Info> pQ = new PriorityQueue<>(new myComp());
        for (int i = 0; i < N; i++) {
            Info info = new Info(arr[i], score[i]);
            pQ.add(info);
        }
        int ans = 0;
        while (!pQ.isEmpty()) {
            Info cur = pQ.poll();
            while (!pQ.isEmpty() && pQ.peek().times <= cur.times + 1) {
                ans += pQ.poll().score;
            }
        }
        return ans;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int n = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int[] arr = new int[n];
            int[] score = new int[n];
            for (int j = 0; j < n; j++) {
                arr[j] = sc.nextInt();
            }
            for (int j = 0; j < n; j++) {
                score[j] = sc.nextInt();
            }
            System.out.println(minNum(n, arr, score));
        }
    }
}
