package 笔试;

import java.util.*;

public class huawei1 {
    List<String> ans = new ArrayList<>();
    HashMap<String, Integer> set = new HashMap();
//    TreeSet<String> treeSet = new TreeSet<>();

    public List<String> findStr(String str) {
        if (str == null || str.length() <= 2) {
            ans.add("NULL");
            return ans;
        }
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0, j = 0; i <= j && j <= str.length() && i < str.length(); ) {
            if (j < str.length() && isWord(str.charAt(j))) {
                j++;
            } else {
                if (j < str.length() && !isWord(str.charAt(i))) {
                    while (j < str.length() && !isWord(str.charAt(j))) {
                        j++;
                    }
                    if (j - i == 1 && str.charAt(i) == ' ') {
                        list.add(" ");
                    } else {
                        list.add(".");
                    }
                } else {
                    String tmp = str.substring(i, j);
                    if (!set.containsKey(tmp)) set.put(tmp, 1);
                    else set.put(tmp, set.get(tmp) + 1);
                    list.add(tmp);
                }
                i = j;
            }
        }

        if (set.size() == 0) {
            ans.add("NULL");
        } else {
            ArrayList<String> max = new ArrayList<>();

            int maxLen = 0;
            for (String t : set.keySet()) {
                int tmpLen = set.get(t);
                if (tmpLen > maxLen) {
                    maxLen = tmpLen;
                    max.clear();
                    max.add(t);
                } else if (tmpLen == maxLen) {
                    max.add(t);
                }
            }
            for (String s : max) {
                findAns(list, s);
            }
        }

        Collections.sort(ans);
        if (ans.size() == 0) {
            ans.add("NULL");
        }
        return ans;
    }


    private void findAns(List<String> list, String max) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(max)) {

                if (i > 1 && list.get(i - 1).equals(" ") && isStr(list.get(i - 2))) {
                    String s = list.get(i - 2) + " " + list.get(i);
                    if (ans.contains(s)) continue;
                    ans.add(s);
                }
                if (i <= list.size() - 3 && list.get(i + 1).equals(" ") && isStr(list.get(i + 2))) {
                    String s2 = list.get(i) + " " + list.get(i + 2);
                    ans.add(s2);
                    if (ans.contains(s2)) continue;
                    ans.add(s2);
                }
            }
        }
    }

    private boolean isStr(String s) {
        return !s.equals(".") && !s.equals(" ");
    }

    public boolean isWord(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static void main(String[] args) {
        huawei1 h = new huawei1();
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        List<String> ans = h.findStr(str);
        for (String s : ans) {
            System.out.println(s);
        }
    }
}
