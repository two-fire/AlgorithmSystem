package Day09_链表;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @Author : LiuYan
 * @create 2021/3/29 20:32
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * 1）把链表放入数组里，在数组上做partition（笔试）
 * 2）分成小、中、大三部分，再把各个部分之间串起来（面试）
 *
 * 问题：
 *      打印结果循环（1 2 5 5 7 9 8 1……）。因为8的next没有设为null
 */
public class Code03_SmallerEqualBigger {
    public static class Node {
        private int value;
        private Node next;
        public Node(int v) {
            value = v;
        }
    }
    // 把链表放入数组里，在数组上做partition
    public static Node listPartition1(Node head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        int len = 0;
        Node node = head;
        while (node != null) {
            node = node.next;
            len++;
        }
        node = head;
        Node[] arr = new Node[len];
        for (int i = 0; i < len; i++) {
            arr[i] = node;
            node = node.next;
        }
        int less = -1;
        int more = len;
        for (int i = 0; i < more;) {
            if (arr[i].value < x) {
                swap(arr, i++, ++less);
            } else if (arr[i].value > x) {
                swap(arr, i, --more);
            } else {
                i++;
            }
        }

        node = arr[0];
        for (int i = 1; i < arr.length; i++) {
            node.next = arr[i];
            node = arr[i];
        }
        node.next = null;
        return arr[0];
    }

    private static void swap(Node[] arr, int i, int j) {
        Node tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static Node listPartition2(Node head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        // 小区
        Node smallHead = null;
        Node smallTail = null;
        // 中区
        Node midHead = null;
        Node midTail = null;
        // 大区
        Node largeHead = null;
        Node largeTail = null;
        Node node = null;
        while (head != null) {
            node = head.next;
            head.next = null; // 让最后一个节点指向null
            if (head.value < x) {
                if (smallTail == null) {
                    smallHead = head;
                    smallTail = head;
                } else {
                    smallTail.next = head;
                    smallTail = head;
                }
            } else if (head.value == x) {
                if (midTail == null) {
                    midHead = head;
                    midTail = head;
                } else {
                    midTail.next = head;
                    midTail = head;
                }
            } else {
                if (largeTail == null) {
                    largeHead = head;
                    largeTail = head;
                } else {
                    largeTail.next = head;
                    largeTail = head;
                }
            }
            head = node;
        }

        // 把3个区连起来
        // 有小于区
        if (smallTail != null) {
            smallTail.next = midHead;
            midTail = midTail == null ? smallTail : midTail; // 谁大谁变成midTail
        }
        // 有中区
        if (midTail != null) {
            midTail.next = largeHead;
        }
        return smallHead != null ? smallHead : (midHead != null ? midHead : largeHead);
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
//        head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }
}
