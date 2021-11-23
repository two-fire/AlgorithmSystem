package Day13_并查集;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/7 13:26
 * 岛屿数量
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 *  输入：grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * 输出：1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 思路：
 *  遍历，如果是1就变为2，然后遍历它的上下左右。
 *
 *  并查集：遍历，如果是1，遍历左和上，看能否合并。
 */
public class Code03_NumberOfIslands {

    // 感染
    public int numIslands1(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        // N * M  N行M列
        int N = grid.length;
        int M = grid[0].length;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    infect(grid, i, j);
                }
            }
        }
        return ans;
    }
    // 从i,j位置出发，感染1
    private void infect(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i == grid.length || j == grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '2';
        infect(grid, i + 1, j);
        infect(grid, i - 1, j);
        infect(grid, i, j - 1);
        infect(grid, i, j + 1);
    }

    /* 并查集 实现1*/
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int N = grid.length;
        int M = grid[0].length;
        Dot[][] dots = new Dot[N][M];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind uf = new UnionFind(dotList);
        // 第一行 只看左
        for (int i = 1; i < M; i++) {
            if (grid[0][i] == '1' && grid[0][i - 1] == '1') {
                uf.union(dots[0][i], dots[0][i - 1]);
            }
        }
        // 第一列 只看上
        for (int j = 1; j < N; j++) {
            if (grid[j][0] == '1' && grid[j - 1][0] == '1') {
                uf.union(dots[j][0], dots[j - 1][0]);
            }
        }
        // 其他 看左看上
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i][j - 1] == '1') {
                        uf.union(dots[i][j], dots[i][j - 1]);
                    }
                    if (grid[i - 1][j] == '1') {
                        uf.union(dots[i][j], dots[i - 1][j]);
                    }
                }
            }
        }
        return uf.sets();
    }
    public static class UnionFind<T> {
        public HashMap<T, Node<T>> nodes;
        public HashMap<Node<T>, Node<T>> parentMap;
        public HashMap<Node<T>, Integer> sizeMap;
        public UnionFind(List<T> list) {
            nodes = new HashMap<>(list.size());
            parentMap = new HashMap<>(list.size());
            sizeMap = new HashMap<>(list.size());
            for (T v : list) {
                Node<T> node = new Node<>(v);
                nodes.put(v, node);
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }
        public Node<T> findHead(Node<T> a) {
            Stack<Node<T>> stack = new Stack<>();
            while (parentMap.get(a) != a) {
                stack.add(a);
                a = parentMap.get(a);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), a);
            }
            return parentMap.get(a);
        }
        public void union(T a, T b) {
            Node<T> aHead = findHead(nodes.get(a));
            Node<T> bHead = findHead(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<T> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<T> small = big == aHead ? bHead : aHead;
                parentMap.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }
        public int sets() {
            return sizeMap.size();
        }
    }
    public static class Dot {
    }
    public static class Node<T> {
        public T value;
        public Node(T v) {
            value = v;
        }
    }

    /* 并查集 实现2*/
    public int numIslands3(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int row = grid.length;
        int col = grid[0].length;
        UnionFind2 uf = new UnionFind2(grid);
        for (int i = 1; i < col; i++) {
            if (grid[0][i] == '1' && grid[0][i - 1] == '1') {
                uf.union(0, i, 0, i - 1);
            }
        }
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                uf.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i - 1][j] == '1' && grid[i][j] == '1') {
                    uf.union(i - 1, j, i, j);
                }
                if (grid[i][j] == '1' && grid[i][j - 1] == '1') {
                    uf.union(i, j, i, j - 1);
                }
            }
        }
        return uf.sets();
    }

    public static class UnionFind2 {
        public int[] parents;
        public int[] size;
        public int[] help;
        public int col;
        public int sets;

        public UnionFind2(char[][] grid) {
            col = grid[0].length;
            sets = 0;
            int row = grid.length;
            int len = col * row;
            parents = new int[len];
            size = new int[len];
            help = new int[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1') {
                        int index = index(i, j);
                        parents[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        // 求grid[i][j]的index
        private int index(int i, int j) {
            return i * col + j;
        }

        public int findHead(int i, int j) {
            int indexa = index(i, j);
            int hi = 0;
            while (parents[indexa] != indexa) {
                help[i++] = indexa;
                indexa = parents[indexa];
            }
            while (--hi >= 0) {
                parents[help[hi]] = indexa;
            }
            return indexa;
        }

        // 把grid[i1][j1]和grid[i2][j2]合并
        public void union(int i1, int j1, int i2, int j2) {
            int aHead = findHead(i1, j1);
            int bHead = findHead(i2, j2);
            if (aHead != bHead) {
                int aSize = size[aHead];
                int bSize = size[bHead];
                int big = aSize >= bSize ? aHead : bHead;
                int small = aHead == big ? bHead : aHead;
                parents[small] = big;
                size[small] = 0;
                size[big] = aSize + bSize;
                sets--;
            }
        }
        public int sets() {
            return sets;
        }
    }
}
