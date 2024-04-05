package org.example.contest3;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String first = scanner.nextLine();
        String second = scanner.nextLine();

        Map<Character, Integer> firstAnagram = new HashMap<>();
        Map<Character, Integer> secondAnagram = new HashMap<>();
        if (first.length() != second.length()) {
            System.out.print("NO");
            return;
        }

        for (int i = 0; i < first.length(); i++) {
            firstAnagram.put(first.charAt(i), firstAnagram.getOrDefault(first.charAt(i), 0) + 1);
            secondAnagram.put(second.charAt(i), secondAnagram.getOrDefault(second.charAt(i), 0) + 1);
        }

        if (firstAnagram.equals(secondAnagram))
            System.out.print("YES");
        else
            System.out.print("NO");
    }
}
