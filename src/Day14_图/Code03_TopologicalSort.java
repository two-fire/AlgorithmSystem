package Day14_图;

import java.util.*;

/**
 * @Author : LiuYan
 * @create 2021/4/8 15:03
 * 图的拓扑排序算法
 *
 * 1）在图中找到所有入度为0的点输出
 * 2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
 * 3）图的所有点都被删除后，依次输出的顺序就是拓扑排序
 *
 * 要求：有向图且其中没有环
 * 应用：事件安排、编译顺序
 *
 * 利用一个队列，一个map。如果入度为0，进入队列。map里面用来存节点和对应的入度
 */

public class Code03_TopologicalSort {
    public static List<Node> sortedTopology(Gragh gragh) {
        if (gragh == null) {
            return null;
        }
        HashMap<Node, Integer> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        for (Node cur : gragh.nodes.values()) {
            map.put(cur, cur.in);
            if (cur.in == 0) {
                queue.add(cur);
            }
        }
        List<Node> lists = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            lists.add(cur);
            for (Node next : cur.nexts) {
                map.put(next, map.get(next) - 1);
                if (map.get(next) == 0) {
                    lists.add(next);
                }
            }
        }
        return lists;
    }
}
