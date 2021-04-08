package Day13_并查集;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : LiuYan
 * @create 2021/4/7 16:13
 * 岛问题（扩展）
 * 每次空降一个1，每次求岛数量。
 * Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]]
 * We return the result as an array: [1, 1, 2, 3]
 *
 * 3 3
 * 0 0 0 1 1 2 2 1
 * 1 1 2 3
 * 4 4
 * 0 0 0 1
 * 1 1
 * ……
 *
 * 思路：和Code03岛问题中，并查集实现2（numIslands3）类似，只是size[small]每次合并完后不置0
 *  如果size[i] != 0，表示i位置被初始化过。
 *
 * https://leetcode.com/problems/number-of-islands-ii/
 */
public class Code04_NumberOfIslandsII {
    public static List<Integer> numIsland21(int m, int n, int[][] positions) {
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> list = new ArrayList<>();
        for (int[] position : positions) {
            list.add(uf.connect(position[0], position[1]));
        }
        return list;
    }
    public static class UnionFind1 {
        public int[] parent;
        public int[] size;
        public int[] help;
        public int col;
        public int row;
        public int sets;
        public UnionFind1(int m, int n) {
            row = m;
            col = n;
            int len = col * row;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }
        public int findHead(int a) {
            int hi = 0;
            while (parent[a] != a) {
                help[hi++] = a;
                a = parent[a];
            }
            while (--hi >= 0) {
                parent[help[hi]] = a;
            }
            return a;
        }
        public int connect(int r, int c) {
            int index = getIndex(r, c);
            if (size[index] == 0) { // 这个地方没有空降过
                size[index] = 1;
                parent[index] = index;
                sets++;
                union(r, c, r - 1, c);
                union(r, c, r, c - 1);
                union(r, c, r + 1, c);
                union(r, c, r , c + 1);
            }
            return sets;
        }

        // 合并size[a]和size[b]
        private void union(int a1, int b1, int a2, int b2) {
            if (a1 < 0 || a1 == row || b1 < 0 || b1 ==col || a2 < 0 || a2 == row || b2 < 0 || b2 ==col) {
                return;
            }
            int a = getIndex(a1, b1);
            int b = getIndex(a2, b2);
            if (size[a] == 0 || size[b] == 0) {
                return;
            }
            int aHead = findHead(a);
            int bHead = findHead(b);
            if (aHead != bHead) {
                int aSize = size[aHead];
                int bSize = size[bHead];
                int big = aSize > bSize ? aHead : bHead;
                int small = big == aHead ? bHead : aHead;
                size[big] += size[small];
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
        public int getIndex(int r, int c) {
            return r * col + c;
        }
    }

    // 课上讲的如果m*n比较大，会经历很重的初始化，而k比较小，怎么优化的方法
    public static List<Integer>  numIsland22(int m, int n, int[][] positions) {
        UnionFind2 uf = new UnionFind2();
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }
    public static class UnionFind2 {
        private HashMap<String, String> parentMap;
        private HashMap<String, Integer> sizeMap;
        private ArrayList<String> help;
        private int sets;

        public UnionFind2() {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        private String findHead(String a) {
            while (parentMap.get(a) != a) {
                help.add(a);
                a = parentMap.get(a);
            }
            for (String cur : help) {
                parentMap.put(cur, a);
            }
            return a;
        }
        private int connect(int r, int c) {
            String a = String.valueOf(r) + "_" + String.valueOf(c);
            if (!sizeMap.containsKey(a)) {
                sizeMap.put(a, 1);
                parentMap.put(a, a);
                sets++;
                String up = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String down = String.valueOf(r) + "_" + String.valueOf(c + 1);
                String left = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String right = String.valueOf(r + 1) + "_" + String.valueOf(c);
                union(a, up);
                union(a, down);
                union(a, left);
                union(a, right);
            }
            return sets;
        }
        private void union(String a, String b) {
            if (parentMap.containsKey(a) && parentMap.containsKey(b)) {
                String aHead = findHead(a);
                String bHead = findHead(b);
                if (!aHead.equals(bHead)) {
                    int aSize = sizeMap.get(aHead);
                    int bSize = sizeMap.get(bHead);
                    String big = aSize > bSize ? aHead : bHead;
                    String small = big == aHead ? bHead : aHead;
                    sizeMap.put(big, aSize + bSize);
                    sets--;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            sc.nextLine();
            String[] str = sc.nextLine().split(" ");
            int[] s = new int[str.length];
            for (int i = 0; i < str.length; i++) {
                s[i] = Integer.parseInt(str[i]);
            }
            int[][] arr = new int[str.length / 2][2];
            int si = 0;
            for (int i = 0; i < arr.length; i++) {
                arr[i][0] = s[si++];
                arr[i][1] = s[si++];
            }
            List<Integer> lists = numIsland22(m, n, arr);
            for (Integer list : lists) {
                System.out.print(list + " ");
            }
        }
    }
}
