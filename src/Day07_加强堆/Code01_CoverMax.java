package Day07_加强堆;

import java.util.*;

/**
 * @Author : LiuYan
 * @create 2021/3/28 14:59
 * 最大线段重合问题（用堆的实现）
 * 给定很多线段，每个线段都有两个数[start, end]，
 * 表示线段开始位置和结束位置，左右都是闭区间
 * 规定：
 * 1）线段的开始和结束位置一定都是整数值
 * 2）线段重合区域的长度必须>=1
 * 返回线段最多重合区域中，包含了几条线段
 *
 * 思路：
 * 方法一： O(N^2)
 *      从最左到最右，每个0.5处就测一下有几条线段，返回最多的线段
 * 方法二： O(N*logN)
 *      1. 按照start排序
 *      2. 准备一个最小堆，之后里面放的是end
 *      3. 根据start遍历线条。每一轮先把end入堆，然后弹出<=start 的end，堆中剩下的个数就是这一轮结果
 *      4. 遍历完后，找到最大的结果就是答案
 */
public class Code01_CoverMax {
    public static int maxCover1(int[][] lines) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < lines.length; i++) {
            max = Math.max(max, lines[i][1]);
            min = Math.min(min, lines[i][0]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p++) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                cur += (lines[i][0] < p && lines[i][1] > p) ? 1 : 0;
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    public static class Line {
        int start;
        int end;
        public Line(int s, int e) {
            start = s;
            end = e;
        }
    }
    public static int maxCover2(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        Arrays.sort(lines, new myComparator());
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int cover = 0;
        // 进出堆2次，最多n个数进，每次调整O(logN)，所以下面是O(N*logN)
        for (int i = 0; i < lines.length; i++) {
            queue.add(lines[i].end);
            while (!queue.isEmpty() && queue.peek() <= lines[i].start) {
                queue.poll();
            }
            cover = Math.max(cover, queue.size());
        }
        return cover;
    }
    public static class myComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }
    // for test
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static class StartComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }

    }

    public static void main(String[] args) {
        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test end");
    }
}
