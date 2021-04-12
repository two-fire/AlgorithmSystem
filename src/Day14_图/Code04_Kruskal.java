package Day14_图;

import java.util.*;

/**
 * @Author : LiuYan
 * @create 2021/4/8 22:48
 * 贪心
 *
 * 最小生成树算法之Kruskal
 * 时间复杂度：O(ElogE)
 *
 * 1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 2）当前的边要么进入最小生成树的集合，要么丢弃
 * 3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
 * 5）考察完所有边之后，最小生成树的集合也得到了
 *
 * 需要一个小根堆，如果from和to不是一个集合就合并，否则舍弃。所以还需要并查集。返回的是选中的边，放到Set中
 */
public class Code04_Kruskal {
    public static class UnionFind {
        private HashMap<Node, Node> parentMap;
        private HashMap<Node, Integer> sizeMap;
        public UnionFind(Gragh gragh) {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node cur : gragh.nodes.values()) {
                parentMap.put(cur, cur);
                sizeMap.put(cur, 1);
            }
        }
        public Node findHead(Node a) {
            Stack<Node> help = new Stack<>();
            while (parentMap.get(a) != a) {
                help.add(a);
                a = parentMap.get(a);
            }
            while (!help.isEmpty()) {
                parentMap.put(help.pop(), a);
            }
            return a;
        }
        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aHead = findHead(a);
            Node bHead = findHead(b);
            if (aHead != bHead) {
                int aSize = sizeMap.get(a);
                int bSize = sizeMap.get(b);
                Node big = aSize >= bSize ? aHead :bHead;
                Node small = big == aHead ? bHead : aHead;
                parentMap.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }
        public boolean isSameSet(Node a, Node b) {
            return findHead(a) == findHead(b);
        }
        public int sets() {
            return sizeMap.size();
        }
    }
    public static class EdgeComp implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> kruskal(Gragh gragh) {
        if (gragh == null) {
            return null;
        }
        UnionFind uf = new UnionFind(gragh);
        // 小根堆中是边长从小到大
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComp());
        // 将边按权值进行排序：O（elogE）
        for (Edge e : gragh.edges) { // e条边
            queue.add(e); // O(logE)
        }
        Set<Edge> ans = new HashSet<>();
        while (!queue.isEmpty()) {
            Edge cur = queue.poll();  // O(logE)
            if (!uf.isSameSet(cur.from, cur.to)) {
                uf.union(cur.from, cur.to);
                ans.add(cur);
            }
        }
        return ans;
    }
}
