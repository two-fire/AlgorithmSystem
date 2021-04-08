package Day14_图;

/**
 * @Author : LiuYan
 * @create 2021/4/8 13:40
 *
 */
public class Edge {
    public int weight;
    public Node from;
    public Node to;
    public Edge(int w, Node f, Node t) {
        weight = w;
        from = f;
        to = t;
    }
}
