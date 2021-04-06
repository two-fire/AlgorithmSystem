package Day09_链表;

import java.util.HashSet;

/**
 * @Author : LiuYan
 * @create 2021/3/31 9:57
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。
 * 请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
 * 【要求】
 * 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 *
 * 思路：
 *      两个无环链表相交
 *      两个有环链表相交：交点在环外；
 *                       交点在入环处；
 *                       交点在环上
 */
public class Code05_FindFirstIntersectNode {
    public static class Node {
        int value;
        Node next;
        public Node(int v) {
            value = v;
        }
    }
    public static Node getIntersectNode(Node head1, Node head2) {
        Node inter1 = getLoopNode2(head1);
        Node inter2 = getLoopNode2(head2);
        if (inter1 == null && inter2 != null || inter1 != null && inter2 == null) {
            return null;
        }
        if (inter1 == null && inter2 == null) { // 都无环
            return noLoop(head1, head2, null);
        } else {
            // 交点在环外或入环处
            Node node = noLoop(head1, head2, inter1.next);
            if (node != null) {
                return node;
            }
            // 交点在环上
            node = inter1.next;
            while (node != inter2 && node != inter1) {
                node = node.next;
            }
            if (node == inter2) {
                return node;
            } else {
                return null;
            }
        }

    }
    // 是否有环
    public static Node getLoopNode1(Node head) {
        if (head == null) {
            return null;
        }
        HashSet set = new HashSet();
        Node cur = head;
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            }
            set.add(cur);
            cur = cur.next;
        }
        return null;
    }
    public static Node getLoopNode2 (Node head) {
        if (head == null) {
            return null;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        if (fast == slow) {
            fast = head;
            while (fast != slow) {
                fast = fast.next;
                slow = slow.next;
            }
            return fast;
        }
        return null;
    }

    // 两段无环单链，返回相交节点或者null
    public static Node noLoop(Node head1, Node head2, Node end) {
        Node cur1 = head1;
        Node cur2 = head2;
        int len1 = 0;
        int len2 = 0;
        while (cur1 != end) {
            cur1 = cur1.next;
            len1++;
        }
        while (cur2 != end) {
            cur2 = cur2.next;
            len2++;
        }
        if (cur1 != cur2) {
            return null;
        }
        Node max = (len1 > len2) ? head1 : head2;
        Node min = (max == head1) ? head2 : head1;
        int diff = Math.abs(len1 - len2);
        while (diff-- > 0) {
            max = max.next;
        }
        while (max != min) {
            max = max.next;
            min = min.next;
        }
        return max;
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        //null
        head1 = null;
        head2 = null;
        System.out.println(getIntersectNode(head1, head2));
    }

}
