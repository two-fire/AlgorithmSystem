package Day14_图;

import java.util.HashSet;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/8 14:20
 * 图的深度优先遍历
 *  一条路走到死，然后回退，看看有没有其他的路。
 *  需要一个栈，一个set，防止出现回路。
 */
public class Code02_DFS {
    public static void dfs(Node start) {
        if (start == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(start);
        set.add(start);
        System.out.println(start.value);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    System.out.println(next.value);
                    stack.push(cur);
                    stack.push(next);
                    break;
                }
            }
    }

}
}
