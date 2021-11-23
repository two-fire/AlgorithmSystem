package 笔试;

public class shoppee1 {
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public int getMaxMoney(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return process(node, true);
    }

    private int process(TreeNode node, boolean flag) { // flag能否选node
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return flag ? node.val : 0;
        }
        int p1 = 0;
        int p2 = process(node.left, true) + process(node.right, true);
        if (flag) { // 能选并且选了
            p1 = node.val + process(node.left, false)
                    + process(node.right, false); // 选node
            p1 = Math.max(p1, process(node.left, true) + process(node.right, false));
            p1 = Math.max(p1, process(node.left, false) + process(node.right, true));
            p1 = Math.max(p1, process(node.left, true) + process(node.right, true));
            return Math.max(p1, p2);
        }
        return p2;
    }

    public static void main(String[] args) {
        shoppee1 s = new shoppee1();
        TreeNode t = new TreeNode(1);
        TreeNode t1 = new TreeNode(2);
        TreeNode t2 = new TreeNode(3);
        TreeNode t3 = new TreeNode(4);
        t.left = t1;
        t.right = t2;
        t2.left = t3;
        System.out.println(s.getMaxMoney(t));
    }
}
