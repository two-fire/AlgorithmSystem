package Day16_动态规划;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author : LiuYan
 * @create 2021/4/18 20:02
 */

public class Code1_1 {
    public class ListNode {
        int val;
        ListNode next = null;

    }
    public ListNode solve (ListNode S) {
        ArrayList<ListNode> lists = new ArrayList<>();
        while (S.next != null) {
            lists.add(S);
            S = S.next;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int N = lists.size();
        int min =Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map.put(lists.get(j).val * (N - j), i);
                min = Math.min(lists.get(j).val * (N - j), min);
            }
        }
        ListNode head = lists.get(map.get(min));
        for (int i = 1; i < N - map.get(min); i++) {
            head.next = lists.get((map.get(min) + i) % N);
            head = head.next;
        }
        return head;
    }
}
