package Day14_图;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author : LiuYan
 * @create 2021/4/8 13:33
 * 图的宽度优先遍历
 * 思路：利用队列，和set来进行遍历
 */
public class Code01_BFS {
    public static void bfs(Node start) {
        if (start == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet set = new HashSet();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }
}
