package Day13_并查集;

import java.util.List;

/**
 * @Author : LiuYan
 * @create 2021/4/7 10:50
 *
 * 朋友圈
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。
 * 如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 *
 * 给你一个 n x n 的矩阵 isConnected ，
 * 其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，
 * 而 isConnected[i][j] = 0 表示二者不直接相连。
 *
 * 返回矩阵中 省份 的数量。
 *  输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
 *  输出：2
 *  （0和1城市相连  0-1 2）
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-provinces
 *
 * 思路：上三角形矩阵遍历，是1 就把i、j合并
 */
public class Code02_FriendCircles {
    public static int findCircleNum(int[][] M) {
        int n = M.length; // n 个城市
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] == 1) { //  i和j连接
                    uf.union(i, j);
                }
            }
        }
        return uf.sets;
    }
    public static class UnionFind {
        int[] parents; // parents[i] = j; i的父亲是j
        int[] size; // i的集合大小是j
        int[] help; // 用来代替栈
        int sets; // 集合个数
        public UnionFind(int n) {
            parents = new int[n];
            size = new int[n];
            help = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
                size[i] = 1;
                sets = n;
            }
        }
        public int findHead(int a) {
            int i = 0;
            while (parents[a] != a) {
                help[i++] = a;
                a = parents[a];
            }
            while (--i >= 0) {
                parents[help[i]] = a;
            }
            return a;
        }
        public void union(int a, int b) {
            int aHead = findHead(a);
            int bHead = findHead(b);
            if (aHead != bHead) {
                int aSize = size[a];
                int bSize = size[b];
                int big = aSize >= bSize ? aHead : bHead;
                int small = aHead == big ? bHead : aHead;
                parents[small] = big;
                size[big] = aSize + bSize;
                size[small] = 0;
                sets--;
            }
        }
    }
}
