package org.example.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class C3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        Map<Integer, Integer> map = new TreeMap<>();

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            int number = Integer.parseInt(tokenizer.nextToken());
            map.put(number, map.getOrDefault(number, 0) + 1);
        }

        int previous = -10;
        int values = map.values().stream().mapToInt(Integer::intValue).sum();

        if (values == 1) {
            System.out.println(0);
            return;
        }

        int minResult = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (Math.abs(entry.getKey() - previous) < 2) {
                minResult = Math.min(minResult, values - entry.getValue() - map.get(previous));
            }
            minResult = Math.min(minResult, values - entry.getValue());
            previous = entry.getKey();
        }

        System.out.println(minResult);
    }
}
