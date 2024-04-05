package org.example.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class F3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Set<String> vocabulary = new HashSet<>(Arrays.asList(br.readLine().split(" ")));
        String[] words = br.readLine().split(" ");

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 1; j < word.length(); j++) {
                String substring = word.substring(0, j);
                if (vocabulary.contains(substring)) {
                    sb.append(substring).append(" ");
                    count++;
                    break;
                }
            }
            if (i == count) {
                sb.append(word).append(" ");
                count++;
            }
        }

        System.out.print(sb.toString().trim());
    }
}
