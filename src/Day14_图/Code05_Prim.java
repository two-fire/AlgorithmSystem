package Day14_图;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Author : LiuYan
 * @create 2021/4/9 9:06
 * 贪心
 *
 * 最小生成树 (Minimum spanning tree) 算法之Prim
 *  点的数量远远小于边的数量的时候，很快就可以结束。
 *  时间复杂度：O(ElogV)  e：边数  v：点数
 *
 * 1）可以从任意节点出发
 * 2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
 * 3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
 * 4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
 * 5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
 * 6）当所有点都被选取，最小生成树就得到了
 *
 * 思路：由上知，需要一个最小堆，来装边。
 *      需要并查集，考虑是否成环。【不需要并查集。如果toNode在set中，说明成环，不要当前边】
 *      把解锁的点装到set中
 *      选了的边装到一个set中
 *
 */
public class Code05_Prim {
    public static class EdgeComp implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }
    // 最小生成树Prim
    public static Set<Edge> primMST(Gragh gragh) {
        if (gragh == null) {
            return null;
        }
        Set<Edge> ans = new HashSet<>(); // 选中的边
        Set<Node> nodeSet = new HashSet<>(); // 解锁的点
        PriorityQueue<Edge> edgeQ = new PriorityQueue<>();// 解锁的边
        for (Node cur : gragh.nodes.values()) {
            if (!nodeSet.contains(cur)) {
                nodeSet.add(cur);
                for (Edge e : cur.edges) { // 解锁边
                        edgeQ.add(e);
                }
                while (!edgeQ.isEmpty()) {
                    Edge e = edgeQ.poll();
                    if (!nodeSet.contains(e.to)) {
                        ans.add(e);
                        nodeSet.add(e.to);
                        for (Edge nextE : cur.edges) { // 解锁边
                            edgeQ.add(nextE);
                        }
                    }
                }
            }
//            break; // 如果是森林，就不用break，如果是一个连通图，就可以break上。
        }
        return ans;
    }

}
