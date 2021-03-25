package Day01_排序和二分法;

/**
 * @Author : LiuYan
 * @create 2021/3/24 14:23
 * 局部最小值问题
 * 一个数组相邻数不相等且无序。求出一个相邻均小于x的数
 * 二分法，往下降的区域分
 */
public class Code07_LocalMin {
    public static int findLocalMin(int[] arr) {
        if (arr == null) {
            return -1;
        }
        return findLocalMin(arr,0, arr.length - 1);
    }
    public static int findLocalMin(int[] arr, int l, int r) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1 || arr[l] < arr[l + 1]) {
            return 0;
        }
        if (arr[r] < arr[r - 1]) {
            return arr.length - 1;
        }
        int mid;
        // 下面两句需要加上，否则报错！
        l = l + 1;
        r = r - 1;

        while (l < r) {
            mid = l + ((r - l) >> 1);
            if (arr[mid] > arr[mid + 1]) {
                l = mid + 1;
            } else if (arr[mid] > arr[mid - 1]) {
                r = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
    //for test
    // -max ~max
    public static int randNum(int max) {
        return (int)(Math.random() * max + 1) - (int)(Math.random() * max + 1);
    }
    //for test
    private static void printArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int N = 10; // 数组长度 0~N-1
        int max = 50; //元素范围 -max ~max
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N); // 长度0 ~ N-1
            int[] arr = new int[len];
            int rand;
            for (int j = 0; j < len - 1; j++) {
                arr[j] = randNum(max);
                while (true) {
                    rand = randNum(max);
                    if (rand != arr[j]) {
                        arr[j + 1] = rand;
                        break;
                    }
                }
            }
            int x = findLocalMin(arr);
            if (arr.length > 1) {
                if (x == 0 && arr[x] > arr[x + 1]) throw new RuntimeException("error!");
                else if (x == arr.length - 1 && arr[x] > arr[x - 1]) throw new RuntimeException("error!");
                else if(x > 0 && x < arr.length - 1) {
                    if (arr[x - 1] < arr[x] || arr[x + 1] < arr[x]) {
                        System.out.println(arr[x - 1] + " " + arr[x] + " " + arr[x + 1]);
                        printArr(arr);
                        throw new RuntimeException("error!");
                    }
                }
            }
        }
        System.out.println("success!");
    }
}
