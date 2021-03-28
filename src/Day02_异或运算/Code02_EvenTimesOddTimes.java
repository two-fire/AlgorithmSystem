package Day02_异或运算;


/**
 * @Author : LiuYan
 * @create 2021/3/26 10:06
 * 1. arr中，只有一种数，出现奇数次，其他数出现了偶数次，怎么找到这个奇数次的数
 * 2. arr中，有两种数，出现奇数次，其他数出现了偶数次，怎么找到这个奇数次的数
 */
public class Code02_EvenTimesOddTimes {
    // arr中，只有一种数，出现奇数次
    public static int findOddTimeNum(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        return eor;
    }
    // arr中，有两种数，出现奇数次
    public static int[] findOddTimesNum(int[] arr) {
        int[] res = new int[2];
        // 异或全部的数 eor = a^b
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        // 找到最右部的1
        int rightOne = eor & (-eor);
        int eor2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((rightOne & arr[i]) == 1) { // 这一位上是1的数
                eor2 ^= arr[i];
            }
        }
        res[0] = eor2;
        res[1] = eor2 ^ eor;
        return res;
    }

    public static void main(String[] args) {
        // 两个数交换
        int a = 5;
        int b = 7;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);

        System.out.println("arr中，有一种数，出现奇数次");
        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        System.out.println(findOddTimeNum(arr1));
        System.out.println("arr中，有两种数，出现奇数次");
        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        for (int n : findOddTimesNum(arr2)) {
            System.out.println(n);
        }

    }


}
