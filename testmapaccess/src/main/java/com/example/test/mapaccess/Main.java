package com.example.test.mapaccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map["10"] = "ten";
        map.put("2", "two");
        System.out.println(map["2"]);
        System.out.println(map["10"]);

        Map<String, List<Integer>> map2 = new LinkedHashMap<>();
        map2["list 1..4"] = Arrays.asList(1, 2, 3, 4);
        map2["list 5..9"] = Arrays.asList(5, 6, 7, 8, 9);
        map2["empty"] = Collections.emptyList();
        System.out.println(map2["list 1..4"]);
        System.out.println(map2["list 5..9"]);
        System.out.println(map2["empty"]);

        Test test = new Test();
        int x = test["5"];
//        int x = test.get("5");
        System.out.println(x);
    }

    static class Test {

        public int get(String s) {
            System.out.println("get " + s);
            return 10;
        }
    }
}
