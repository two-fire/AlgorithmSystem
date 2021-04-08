package Day12_贪心算法;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author : LiuYan
 * @create 2021/4/6 20:03
 * 输入: 正数数组costs、正数数组profits、正数K、正数M
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * K表示你只能串行的最多做k个项目
 * M表示你初始的资金
 * 说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
 * 输出：你最后获得的最大钱数。
 *
 * 思路：
 * 时间复杂度O(N*LogN)
 *  1.先建一个小根堆，所有项目按照花费来组织。再建一个大根堆，按利润组织。开始为空。
 *  2.每一轮按照w，把能解锁的项目弹出放到大根堆。
 *  3.大根堆中拿出顶部，做它。周而复始
 */
public class Code05_IPO {
    public static int getMaxProfit(Program[] programs, int K, int M) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComp());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComp());
        for (int i = 0; i < programs.length; i++) {
            minCostQ.add(programs[i]);
        }
        int ans = M;
        for (int i = 0; i < K; i++) {
             while (minCostQ.peek().c <= M) {
                maxProfitQ.add(minCostQ.poll());
            }
             if (maxProfitQ.isEmpty()) {
                 break;
             }
             ans += maxProfitQ.poll().p;
        }
        return ans;

    }

    public static class Program {
        int c; // 花费
        int p; // 利润
        public Program(int c, int p) {
            this.c = c;
            this.p = p;
        }
    }

    public static class MinCostComp implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.c - o2.c;
        }
    }
    public static class MaxProfitComp implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.p - o1.p;
        }
    }
}
