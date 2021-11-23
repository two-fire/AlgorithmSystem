package 笔试;

import java.util.*;

/**
 * 2->1  3,4->2,1   5,6,7->3   8,9->2,5,7   10->4   【4做完了才能做10】
 */
public class hua {

    // arr 依赖关系； K 任务数量
    public static int[] findWay(int[][] relate, int K) {
        Map<Integer, Integer> inDegrees = new HashMap<>(); // 入度
        Map<Integer, List<Integer>> adj = new HashMap<>(); // 邻接表
        for (int i = 0; i < K; i++) {
            inDegrees.put(i, 0);
        }
        for (int[] cp : relate) {
            int cur = cp[1];
            int next = cp[0];
            inDegrees.put(next, inDegrees.get(next)  + 1);
            if (!adj.containsKey(cur)) {
                adj.put(cur, new ArrayList<>());
            }
            adj.get(cur).add(next);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int key : inDegrees.keySet()) {
            queue.add(key);
        }
        int[] ans = new int[K];
        int idx = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            ans[idx++] = cur;
            if (adj.get(cur) == null) {
                continue;
            }
            List<Integer> list = adj.get(cur);
            for (int v : list) {
                inDegrees.put(v, inDegrees.get(v) - 1);
                if (inDegrees.get(v) == 0) {
                    queue.add(v);
                }
            }
        }
        if (idx != K) {
            return new int[]{};
        }
        return ans;
    }

    public static void main(String[] args) {
        //2->1 3,4 ->2,1 5,6,7->3 8,9->2,5,7 10->4
    }
}
