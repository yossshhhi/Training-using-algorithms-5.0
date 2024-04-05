package org.example.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class D3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(line.nextToken());
        int k = Integer.parseInt(line.nextToken());
        Map<Integer, List<Integer>> map = new HashMap<>();

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            int number = Integer.parseInt(tokenizer.nextToken());
            List<Integer> indices = map.getOrDefault(number, new ArrayList<>());
            indices.add(i);
            map.put(number, indices);
        }

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> indices = entry.getValue();

            for (int i = 1; i < indices.size(); i++) {
                if (indices.get(i) - indices.get(i - 1) - 1 < k) {
                    System.out.print("YES");
                    return;
                }
            }
        }

        System.out.print("NO");
    }
}
