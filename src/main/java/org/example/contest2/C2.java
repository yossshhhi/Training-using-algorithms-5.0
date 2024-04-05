package org.example.contest2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class C2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> ropes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ropes.add(scanner.nextInt());
        }

        Collections.sort(ropes);
        Integer biggestRope = ropes.getLast();
        int sum = biggestRope;
        for (int i = 0; i < ropes.size() - 1; i++) {
            int integer = ropes.get(i);
            biggestRope -= integer;
            sum += integer;
        }

        if (biggestRope > 0) {
            System.out.print(biggestRope);
        } else {
            System.out.print(sum);
        }
    }
}
