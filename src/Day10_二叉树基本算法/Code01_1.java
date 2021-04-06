package Day10_二叉树基本算法;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/1 8:49
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制 。
 * 实现 LRUCache 类：
 *
 * LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。
 * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 */
public class Code01_1 {
    public static class Node {
        private int val;
        private Node next;
        public Node(int v) {
            val = v;
        }
    }
    public static class LRUCache {
        private int capacity;
        private HashMap<Integer, Integer> map;
        private int size;
        private Node node;
        private Node head;
        private Node tail;
        public LRUCache (int c) {
            capacity = c;
            map = new HashMap<>(capacity);
            size = 0;
            node = new Node(-1);
            head = node;
            tail = node;
        }
        public int get(int key) {
            if (map.containsKey(key)) {
                tail.next = new Node(key);
                tail = tail.next;
                return map.get(key);
            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (!map.containsKey(key)) {
                map.put(key, value);
                tail.next = new Node(key);
                tail = tail.next;
            }
        }

    }
}
