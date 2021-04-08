package Day14_图;

/**
 * @Author : LiuYan
 * @create 2021/4/8 13:49
 *
 * 图的算法都不算难，只不过coding的代价比较高
 *
 * 1）先用自己最熟练的方式，实现图结构的表达
 * 2）在自己熟悉的结构上，实现所有常用的图算法作为模板
 * 3）把面试题提供的图结构转化为自己熟悉的图结构，再调用模板或改写即可
 */

// 图的适配器
public class GraphGenerator {
    // matrix 所有的边
    // N*3 的矩阵
    // [weight, from节点上面的值，to节点上面的值]
    //
    // [ 5 , 0 , 7]
    // [ 3 , 0,  1]
    public static Gragh createGraph(int[][] matrix) {
        Gragh gragh = new Gragh();
        for (int i = 0; i < matrix.length; i++) {
            int from = matrix[i][0];
            int to = matrix[i][1];
            int weight = matrix[i][2];
            if (!gragh.nodes.containsKey(from)) {
                gragh.nodes.put(matrix[i][0], new Node(from));
            }
            if (!gragh.nodes.containsKey(to)) {
                gragh.nodes.put(matrix[i][1], new Node(to));
            }
            Node fromNode = gragh.nodes.get(from);
            Node toNode = gragh.nodes.get(to);
            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.edges.add(edge);
            gragh.edges.add(edge);
        }
        return gragh;
    }
}
