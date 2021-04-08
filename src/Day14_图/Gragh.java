package Day14_图;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author : LiuYan
 * @create 2021/4/8 13:40
 *
 */
public class Gragh {
    public HashMap<Integer, Node> nodes; // 点集
    public HashSet<Edge> edges; // 边集
    public Gragh() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

}
