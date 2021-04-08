package Day14_图;

import java.util.ArrayList;

/**
 * @Author : LiuYan
 * @create 2021/4/8 13:40
 */
public class Node {
    public int value;
    public int in; // 入度
    public int out; // 出度
    public ArrayList<Node> nexts; // 从它出发找到的直接邻居
    public ArrayList<Edge> edges; // 从它出发有多少直接的边
    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
