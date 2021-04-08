package Day14_图;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author : LiuYan
 * @create 2021/4/8 14:31
 * 描述
 * 给定一个有向图，图节点的拓扑排序定义如下:
 *  对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.
 *  拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
 *
 * 针对给定的有向图找到任意一种拓扑排序的顺序。
 *
 * 思路：计算入度，然后入读为0入队列
 */
// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderBFS {
    // 邻接表法
    public static class DirectedGraphNode {
        public int label; // node的值
        public ArrayList<DirectedGraphNode> neighbors; // node的直接邻居

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        if (graph == null) {
            return null;
        }
        HashMap<DirectedGraphNode, Integer> inMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {  // 需要初始化！！！
            inMap.put(cur, 0);
        }
        // 计算入度
        for (DirectedGraphNode cur : graph) {
            for (DirectedGraphNode next : cur.neighbors) {
                inMap.put(next, inMap.get(next) + 1);
            }
        }
        Queue<DirectedGraphNode> queue = new LinkedList<>();
        for (DirectedGraphNode cur : inMap.keySet()) {
            if (inMap.get(cur) == 0) {
                queue.add(cur);
            }
        }
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            DirectedGraphNode cur = queue.poll();
            ans.add(cur);
            for (DirectedGraphNode next : cur.neighbors) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    queue.add(next);
                }
            }
        }
        return ans;
    }
}
