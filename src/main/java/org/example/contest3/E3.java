package org.example.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class E3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            int n = Integer.parseInt(reader.readLine());
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < n; j++) {
                int number = Integer.parseInt(tokenizer.nextToken());
                set.add(number);
            }

            for (Integer integer : set) {
                map.put(integer, map.getOrDefault(integer, 0) + 1);
            }
        }

        int[] result = map.entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .mapToInt(Map.Entry::getKey)
                .sorted()
                .toArray();
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}
