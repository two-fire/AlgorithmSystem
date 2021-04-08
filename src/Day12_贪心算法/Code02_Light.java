package Day12_贪心算法;

import java.util.HashSet;

/**
 * @Author : LiuYan
 * @create 2021/4/6 18:35
 *
 * 给定一个字符串str，只由‘X’和‘.’两种字符构成。
 * ‘X’表示墙，不能放灯，也不需要点亮
 * ‘.’表示居民点，可以放灯，需要点亮
 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 *
 * 思路：
 *  如果i位置是·： i+1是x ，灯+1， 跳到i+2
 *                     · ，灯+1， 跳到i+3
 *  如果i位置是x： 跳到i+1
 *
 */
public class Code02_Light {
    // 暴力
    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) {
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') { // 当前是.
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }return lights.size();
        } else { // str还没结束
            // i X .
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }

    // 贪心
    public static int minLight2(String road) {
        char[] str = road.toCharArray();
        int ans = 0;
        for (int i = 0; i < str.length;) {
            if (str[i] == '.') {
                ans++;
                if (i + 1 == str.length) {
                    break;
                } else {
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
            } else {
                i++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int len = 20;
        for (int i = 0; i < testTime; i++) {
            String string = randomString(len);
            int ans1 = minLight1(string);
            int ans2 = minLight2(string);
            if (ans1 != ans2) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }

    private static String randomString(int len) {
        char[] chs = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < chs.length; i++) {
            chs[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(chs);
    }
}
