package 笔试;

import java.util.*;

/**
 * 深信服
 */
public class Emp_3 {
    public static String get_substr(String st) {
        char[] chars = st.toCharArray();
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            String key = String.valueOf(chars[i]);
            if (!map.containsKey(key)) {
                map.put(key, 1);
                list.add(key);
            } else if (map.get(key) == 2) {
                map.remove(key);
                int count = 2;
                while (count-- != 0) {
                    list.remove(key);
                }
            } else {
                map.put(key, map.get(key) + 1);
                list.add(key);
        }
        }
        StringBuilder ans = new StringBuilder();
        for (String s : list) {
            ans.append(s);
        }

        return ans.toString();
    }
    public static String get_substr2(String st) {
        char[] chars = st.toCharArray();
        HashMap<String, Integer> map = new HashMap<>();
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            String key = String.valueOf(chars[i]);
            if (!map.containsKey(key)) {
                if (!map.isEmpty() && map.get(list.peekLast()) >= 2){
                    map.remove(key);
                    int count = map.get(list.peekLast());
                    while (count-- != 0) {
                        list.pollLast();
                    }
                }
                map.put(key, 1);
                list.add(key);
            } else {
                map.put(key, map.get(key) + 1);
                list.add(key);
            }
        }
        StringBuilder ans = new StringBuilder();
        for (String s : list) {
            ans.append(s);
        }

        return ans.toString();
    }
    public static void main(String[] args) {
//        HashMap<String, Integer> map = new HashMap<>();
//        map.put("z", 1);
//        map.put("b", 1);
//        map.put("c", 1);
//        map.put("d", 1);
//        map.put("e", 1);
//        map.put("f", 1);
//        StringBuilder ans = new StringBuilder();
//        for (String s : map.keySet()) {
//            int i = map.get(s);
//            while (i-- != 0) {
//                ans.append(s);
//            }
//        }
//        System.out.println(ans.toString());
        System.out.println(get_substr2("aaaab"));
    }
}
