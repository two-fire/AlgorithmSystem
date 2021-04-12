package Day14_图;

import com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author : LiuYan
 * @create 2021/4/9 10:16
 * 贪心
 *
 * 最小距离 —— Dijkstra算法
 *   要求：有向无负边，可以有环。【准确来说，是不能有负环。因为找最小收益时，会不停转下去】
 *   结果：给出一个源点，到其他点的最短距离
 *
 * 1）Dijkstra算法必须指定一个源点
 * 2）生成一个源点到各个点的最小距离表，一开始只有一条记录，即源点到自己：0，源点到其他所有点：正无穷大
 * 3）从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新源点到各个点的最小距离表，不断重复这一步
 * 4）源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
 *
 * 思路：
 *  一、由上，需要一个map记录最小距离。一个set来记录打过√的点。每次遍历所有记录（要屏蔽掉✔的点），选一个最小的点
 *      最终返回最小距离表
 *  二、优化。优化遍历  加强堆
 *      堆中放的是nodeRecord，里面是Node和距离。
 */

public class Code06_Dijkstra {
    // 经典方法
    public static HashMap<Node, Integer> dijkstra1(Node from) {
        HashMap<Node, Integer> minDistance = new HashMap<>();
        minDistance.put(from, 0);
        HashSet<Node> selected = new HashSet<>();
        while (selected.size() < minDistance.size()) {
            Node cur = getMinNode(minDistance, selected);
            selected.add(cur);
            for (Edge next : cur.edges) {
                if (!minDistance.containsKey(next.to)) {
                    minDistance.put(next.to, minDistance.get(cur) + next.weight);
                } else {
                    // 源点 -> cur 最短距离 + cur -> cur.edge 权重， 源点 -> cur.edge 最短距离
                    minDistance.put(next.to, Math.min(minDistance.get(cur) + next.weight, minDistance.get(next.to)));
                }
            }
        }
        return minDistance;
    }

    // 返回节点，未✔节点中 源点到它的距离最小
    private static Node getMinNode(HashMap<Node, Integer> minDistance, HashSet<Node> selected) {
        Node minNode = null;
        int minDist = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> cur : minDistance.entrySet()) {
            if (!selected.contains(cur.getKey()) && cur.getValue() < minDist) {
                minNode = cur.getKey();
                minDist = cur.getValue();
            }
        }
        return minNode;
    }

    /* 优化 加强堆来完成遍历选最小节点操作*/
    public static HashMap<Node, Integer> dijkstra2(Node from, int size) {
        MyHeap heap = new MyHeap(size);
        heap.addOrUpdateOrIgnore(from, 0);
        HashMap<Node, Integer> ans = new HashMap<>();
        while (!heap.isEmpty()) {
            NodeRecord cur = heap.pop();
            int distance = cur.distance;
            Node curNode = cur.node;
            // 更新它指向的其他节点到源点的最短距离
            for (Edge edge : curNode.edges) {
              heap.addOrUpdateOrIgnore(edge.to, distance + edge.weight);
            }
            ans.put(curNode, distance);
        }
        return ans;
    }
    public static class NodeRecord {
        private Node node;
        private int distance;
        public NodeRecord(Node n, int d) {
            node = n;
            distance = d;
        }
    }
    public static class MyHeap {
        private Node[] nodes; // 堆
        private HashMap<Node, Integer> indexMap; // 反向索引表
        private HashMap<Node, Integer> distanceMap; // 最小距离表
        private int size; // 堆上节点数
        public MyHeap(int s) {
            size = 0;
            nodes = new Node[s];
            indexMap = new HashMap<>();
            distanceMap = new HashMap<>();
        }
        // 更新 选中的节点 的邻居 和源点的最短距离
        public void addOrUpdateOrIgnore(Node node, int distance) {
            // add
            if (!isEntered(node)) {
                distanceMap.put(node, distance);
                nodes[size] = node;
                indexMap.put(node, size);
                heapInsert(node, size++);
            }
            // update
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distance, distanceMap.get(node)));
                heapInsert(node, indexMap.get(node));
            }
        }

        public NodeRecord pop() {
           NodeRecord record = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
           swap(0, size - 1);
           indexMap.put(nodes[size - 1], -1);
           distanceMap.remove(nodes[size - 1]);
           nodes[size - 1] = null;
           heapify(0, --size);

           return record;
        }

        // 在堆中插入，向上调整
        public void heapInsert(Node node, int index) {
            // 父大 交换
            while ((index - 1) / 2 >= 0 && distanceMap.get(nodes[(index - 1) / 2]) > distanceMap.get(nodes[index])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }
        // 向下调整
        public void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallIndex = (left + 1) < size && distanceMap.get(left) > distanceMap.get(left + 1) ? left + 1 : left;
                smallIndex = distanceMap.get(smallIndex) < distanceMap.get(index) ? smallIndex : index;
                if (smallIndex == index) {
                    break;
                }
                swap(smallIndex, index);
                index = smallIndex;
                left = index * 2 + 1;
            }
        }
        private boolean isEmpty() {
            return size == 0;
        }
        // node是否进入过堆
        private boolean isEntered(Node node) {
            return indexMap.containsKey(node);
        }
        // node是否在堆中
        private boolean inHeap(Node node) {
            return isEntered(node) && indexMap.get(node) != -1;
        }
        // 交换i和j处的node
        private void swap(int i, int j) {
            Node tmp = nodes[i];
            nodes[i] = nodes[j];
            nodes[j] = tmp;
        }
    }
}
