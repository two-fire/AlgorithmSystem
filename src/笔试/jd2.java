package 笔试;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 小明手中有n块积木，并且小明知道每块积木的重量。现在小明希望将这些积木堆起来，要求是任意一块积木如果想堆在另一块积木上面，那么上面积木的重量减去下面积木的重量不能超过x（每堆中最下面的积木没有重量要求）。现在小明有一个机会，除了这n块积木，还可以获得k块任意重量的积木。小明希望将积木堆在一起，同时希望积木堆的数量越少越好，你能帮他找到最好的方案么？
 */
public class jd2 {
    // n 积木； 还有k块 ； 上面比下面重最多x
    public static int minCount(int n, long k, int x, int[] arr) {
        int ans = 1;
        Arrays.sort(arr);
        ArrayList<Integer> list = new ArrayList<>();
        int dis;
        for (int i = 1; i < arr.length; i++) {
            dis = arr[i] - arr[i - 1];
            if (dis > x) {
                list.add(dis);
                ans++; // ans+1个堆
            }
        }
        int[] disArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            disArr[i] = list.get(i);
        }
        Arrays.sort(disArr);
        int count = 0;
        for (int i = 0; i < disArr.length && disArr[i] <= x * 2; i++) {
            count++;
        }

        if (k >= count) {
            ans -= count;
        }else if (k > 0) {
            ans = (int) (ans - k);
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long k = sc.nextInt();
        int x = sc.nextInt();
        String space = sc.nextLine();
        String str = sc.nextLine();
        String[] s = str.trim().split(" ");
        int[] arr = new int[s.length];
        int i = 0;
        for (String v : s) {
            arr[i++] = Integer.parseInt(v);
        }
        System.out.println(minCount(n, k, x, arr));
    }
}
