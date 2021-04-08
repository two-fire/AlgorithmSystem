package Day14_图;

import Day12_贪心算法.Code01_LowestLexicography;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @Author : LiuYan
 * @create 2021/4/8 20:00
 *
 * 给定一个有向图，图节点的拓扑排序定义如下:
 *  对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.
 *  拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
 * 针对给定的有向图找到任意一种拓扑排序的顺序.
 *
 * OJ链接：https://www.lintcode.com/problem/topological-sorting
 * 思路：
 * 一、如果从x开始，往后走过的所有节点数，大于从Y开始… 这就是说，x的拓扑序小于y的拓扑序。
 *      建立缓存表，然后排序。谁点次高谁在前。
 * 点次：比如：a—>b和c，b->c 那么a的点次是：b串了2，c串了1，a自己1.一共4次
 * 返回一个节点和这个节点后面有多少个点次【记录在一个缓存record类中】
 * 如果缓存中有，直接拿。没有，说明当前节点的点次没算过，算好后放入缓存，再返回。
 */
public class Code03_TopologicalOrderDFS2 {
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
         public DirectedGraphNode node;
         public long num; // 点次
         public Record(DirectedGraphNode node, long n) {
            this.node = node;
            num = n;
         }
    }

    // 返回一个节点和这个节点后面有多少个点次
    public Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> record) {
        if (record.containsKey(cur)) { // 缓存中存在
            return record.get(cur);
        }
        long num = 1;
        for (DirectedGraphNode next : cur.neighbors) {
            num += f(next, record).num;
        }
        record.put(cur, new Record(cur,num));
        return record.get(cur);
    }
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        if (graph == null) {
            return null;
        }
        HashMap<DirectedGraphNode, Record> record = new HashMap<>();
        ArrayList<Record> lists = new ArrayList<>();
        for (DirectedGraphNode cur : graph) { // 求缓存
            lists.add(f(cur, record));
        }
        lists.sort(new MyComp());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record cur : lists) {
            ans.add(cur.node);
        }
        return ans;
    }
    // 按照点次num排序
    public static class MyComp implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
//            return (int) (o2.num - o1.num); // 有错
//            return (o1.num > o2.num ? -1 : 1); // 正确
            return o1.num == o2.num ? 0 : (o1.num > o2.num ? -1 : 1);
        }
    }
}
