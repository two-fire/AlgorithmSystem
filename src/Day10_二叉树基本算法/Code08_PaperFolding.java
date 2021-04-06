package Day10_二叉树基本算法;

/**
 * @Author : LiuYan
 * @create 2021/4/2 10:40
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。
 * 此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。
 * 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 *
 * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。
 * 请从上到下打印所有折痕的方向。
 * 例如:
 *  N=1时，打印: down
 *  N=2时，打印: down down up
 *  N=3时，打印: down down up down down up up
 *
 *  规律：满二叉树中序遍历
 *      1. 根是down
 *      2. 左树头是down
 *      3. 右树头是up
 */

public class Code08_PaperFolding {
    public static void printAllFolds(int N) {
        if (N <= 0) {
            return;
        }
        process(1, N, false);
    }
    // 在第i层，一共N层 如果为凸就是true
    public static void process(int i, int N, boolean isTu) {
        if (i > N) {
            return;
        }
        process(i + 1, N, false);
        System.out.println(isTu ? "凸 " : "凹 ");
        process(i + 1, N, true);
    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }
}
