package Day08_前缀树和桶排序;

/**
 * @Author : LiuYan
 * @create 2021/3/28 20:35
 *
 * 前缀树
 * 1）单个字符串中，字符从前到后的加到一棵多叉树上
 * 2）字符放在路上，节点上有pass和end
 * 3）所有样本都这样添加，如果没有路就新建，如有路就复用
 * 4）沿途节点的pass值增加1，每个字符串结束时来到的节点end值增加1
 *
 * 可以完成前缀相关的查询
 */

import java.util.HashMap;
import java.util.HashSet;

/**
 * 设计一种结构。用户可以：
 * 1）void insert(String str)            添加某个字符串，可以重复添加，每次算1个
 * 2）int search(String str)             查询某个字符串在结构中还有几个
 * 3) void delete(String str)           删掉某个字符串，可以重复删除，每次算1个
 * 4）int prefixNumber(String str)  查询有多少个字符串，是以str做前缀的
 */
public class Code01_TrieTree {
    public static class Node1 {
        private int pass; // 经过就加1
        private int end; // 字符串结尾就加1
        private Node1[] nexts; // 字符

        public Node1() {
            pass = 0;
            end = 0;
            nexts = new Node1[26];
        }
    }
    public static class Trie1 {
        private Node1 root;
        public Trie1() {
            root = new Node1();
        }
        // 添加某个字符串，可以重复添加
        public void insert(String str) {
            if (str == null) {
                return;
            }
            char[] chs = str.toCharArray();
            Node1 node = root;
            int path = 0;
            node.pass++;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i] - 'a';
                if (node.nexts[path] == null) {
                    node.nexts[path] = new Node1();
                }
                node.pass++;
                node = node.nexts[path];
            }
            node.end++;
        }
        // 查询某个字符串在结构中还有几个
        public int search(String str) {
            if (str == null) {
                return 0;
            }
            char[] chs = str.toCharArray();
            Node1 node = root;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i] - 'a';
                if (node.pass == 0 || node.nexts[path] == null) {
                    return 0;
                }
                node = node.nexts[path];
            }
            return node.end;
        }
        // 删掉某个字符串，可以重复删除
        public void delete(String str) {
            // 如果有这个字符串再删
            if (search(str) != 0) {
                char[] chs = str.toCharArray();
                Node1 node = root;
                int path = 0;
                node.pass--;
                for (int i = 0; i < chs.length; i++) {
                    path = chs[i] - 'a';
                    node = node.nexts[path];
                    // 如果pass已经变为0了，说明后面的可以直接删完，不用再往下遍历了
                    if (--node.pass == 0) {
                        node = null;
                        return;
                    }
                }
                node.end--;
            }
        }
        // 查询有多少个字符串，是以str做前缀的
        public int prefixNumber(String str) {
            if (str == null) {
                return 0;
            }
            char[] chs = str.toCharArray();
            Node1 node = root;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i] - 'a';
                node = node.nexts[path];
                // 如果没有这个节点说明没有str为前缀的字符串
                if (node == null) {
                    return 0;
                }
            }
            return node.pass;
        }
    }

    public static class Node2 {
        private int pass;
        private int end;
        private HashMap<Integer, Node2> nexts;
        public Node2() {
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
          private Node2 root;
          public Trie2() {
              root = new Node2();
          }

        public void insert(String str) {
            if (str == null) {
                return;
            }
            char[] chs = str.toCharArray();
            Node2 node = root;
            int path = 0;
            node.pass++;
            for (int i = 0; i < chs.length; i++) {
                path = (int)chs[i];
                if (!node.nexts.containsKey(path)) {
                    node.nexts.put(path, new Node2());
                }
                node = node.nexts.get(path);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String str) {
            if (search(str) != 0) {

                char[] chs = str.toCharArray();
                Node2 node = root;
                int path = 0;
                node.pass--;
                for (int i = 0; i < chs.length; i++) {
                    path = (int) chs[i];
                    if (--node.nexts.get(path).pass == 0) {
                        node.nexts.remove(path);
                        return;
                    }
                    node = node.nexts.get(path);
                }
                node.end--;
            }
        }

        public int search(String str) {
            if (str == null) {
                return 0;
            }
            char[] chs = str.toCharArray();
            Node2 node = root;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = (int)chs[i];
                if (!node.nexts.containsKey(path)){
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.end;
        }

        public int prefixNumber(String str) {
            if (str == null) {
                return 0;
            }
            char[] chs = str.toCharArray();
            Node2 node = root;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = (int)chs[i];
                // 没有这个前缀
                if (!node.nexts.containsKey(path)) {
                    return 0;
                }
            }
            return node.pass;
        }
    }

    public static class Right {
        private HashMap<String, Integer> set;
        public Right() {
            set = new HashMap<>();
        }

        public void insert(String str) {
            if (set.containsKey(str)) {
                set.put(str, set.get(str) + 1);
            } else {
                set.put(str, 1);
            }
        }

        public int search(String str) {
            if (set.containsKey(str)) {
                return set.get(str);
            } else {
                return 0;
            }
        }

        public void delete(String str) {
            if (set.containsKey(str)) {
                if (set.get(str) == 1) {
                    set.remove(str);
                } else {
                    set.put(str, set.get(str) - 1);
                }
            }
        }

        public int prefixNumber(String str) {
            int count = 0;
            for (String s : set.keySet()) {
                if (s.startsWith(str)) {
                    count++;
                }
            }
            return count;
        }
    }

    // test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int)Math.random() * strLen + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int)(Math.random() * 26);
            ans[i] = (char)(97 + value);
        }
        return String.valueOf(ans);
    }
    // test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int)Math.random() * arrLen + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }
    public static void main(String[] args) {
        int testTime = 100000;
        int arrLen = 100;
        int strLen = 20;
        for (int i = 0; i < testTime; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if(decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1= trie1.search(arr[j]);
                    int ans2= trie2.search(arr[j]);
                    int ans3= right.search(arr[j]);
                    if (ans1 != ans3 || ans2 != ans3) {
                        System.out.println("oop!");
                    }
                } else {
                    int ans1= trie1.prefixNumber(arr[j]);
                    int ans2= trie2.prefixNumber(arr[j]);
                    int ans3= right.prefixNumber(arr[j]);
                    if (ans1 != ans3 || ans2 != ans3) {
                        System.out.println("oop!");
                    }
                }
            }
        }
        System.out.println("finish! ");
    }
}
