package Day14_图;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @Author : LiuYan
 * @create 2021/4/8 20:00
 * 思路：
 * 二、从当前节点出发，走到的最大深度，谁大谁在前
 * 最大深度：我所有邻居中最大深度的最大值，然后+1就是我的最大深度
 *
 */
public class Code03_TopologicalOrderDFS1 {
    public static class DirectedGraphNode {
        public int label; // node的值
        public ArrayList<DirectedGraphNode> neighbors; // node的直接邻居

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }
    // 缓存
    public class Record {
        private DirectedGraphNode node;
        private int depth;
        public Record(DirectedGraphNode n, int d) {
            node = n;
            depth = d;
        }
    }
    // 返回节点和该节点的最大深度
    public Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> record) {
        if (record.containsKey(cur)) {
            return record.get(cur);
        }
        int depth = 0;
        for (DirectedGraphNode node : cur.neighbors) {
            depth = Math.max(f(node, record).depth, depth);
        }
        Record ans = new Record(cur, depth + 1);
        record.put(cur, ans);
        return ans;
    }
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        if (graph == null) {
            return null;
        }
        ArrayList<Record> lists = new ArrayList<>();
        HashMap<DirectedGraphNode, Record> record = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            lists.add(f(cur, record));
        }
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        lists.sort(new MyComp());
        for (Record cur : lists) {
            ans.add(cur.node);
        }
        return ans;
    }
    public static class MyComp implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o2.depth - o1.depth;
        }
    }
}
