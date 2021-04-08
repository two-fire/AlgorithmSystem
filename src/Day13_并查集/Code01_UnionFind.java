package Day13_并查集;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/7 10:04
 * 并查集
 * 1）有若干个样本a、b、c、d…类型假设是V
 * 2）在并查集中一开始认为每个样本都在单独的集合里
 * 3）用户可以在任何时候调用如下两个方法：
 *  boolean isSameSet(V x, V y) : 查询样本x和样本y是否属于一个集合
 *  void union(V x, V y) : 把x和y各自所在集合的所有样本合并成一个集合
 * 4） isSameSet和union方法的代价越低越好
 *
 * 1）每个节点都有一条往上指的指针
 * 2）节点a往上找到的头节点，叫做a所在集合的代表节点
 * 3）查询x和y是否属于同一个集合，就是看看找到的代表节点是不是一个
 * 4）把x和y各自所在集合的所有点合并成一个集合，只需要小集合的代表点挂在大集合的代表点的下方即可
 *
 * 并查集的优化
 * 1）节点往上找代表点的过程，把沿途的链变成扁平的
 * 2）小集合挂在大集合的下面
 * 3）如果方法调用很频繁，那么单次调用的代价为O(1)，两个方法都如此
 *
 * 并查集的应用
 *   解决两大块区域的合并问题
 *   常用在图等领域中
 */
public class Code01_UnionFind {
    public static class Node<T> {
        T value;
        public Node(T v) {
            value = v;
        }
    }
    public static class UnionFind<T> {
        public HashMap<T, Node<T>> nodes; // 包装
        public HashMap<Node<T>, Node<T>> parentMap; // 节点 父亲
        public HashMap<Node<T>, Integer> sizeMap; // 集合头 集合大小
        public UnionFind(List<T> lists) {
           nodes = new HashMap<>();
           parentMap = new HashMap<>();
           sizeMap = new HashMap<>();
           for (T v : lists) {
               Node<T> node = new Node<>(v);
               nodes.put(v, node);
               parentMap.put(node, node);
               sizeMap.put(node, 1);
           }
        }
        // 找到cur所在集合的头
        public Node<T> findHead(Node<T> cur) {
            Stack<Node<T>> stack = new Stack<>();
            while (parentMap.get(cur) != cur) {
                stack.push(cur);
                cur = parentMap.get(cur);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), cur);
            }
            return cur;
        }
        // 是否是一个集合
        public boolean isSameSet(T a, T b) {
           return findHead(nodes.get(a)) == findHead(nodes.get(b));
        }
        // 把x和y合并
        public void union(T a, T b) {
            Node<T> aHead = findHead(nodes.get(a));
            Node<T> bHead = findHead(nodes.get(b));
            if (aHead != bHead) {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                Node<T> big = aSize >= bSize ? aHead : bHead;
                Node<T> small = big == aHead ? bHead : aHead;
                parentMap.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }
        // 返回集合的总个数
        public int sets() {
            return sizeMap.size();
        }
    }
}
