package org.example.contest2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class A2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> listX = new ArrayList<>();
        List<Integer> listY = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            listX.add(scanner.nextInt());
            listY.add(scanner.nextInt());
        }
        Collections.sort(listX);
        Collections.sort(listY);
        System.out.print(listX.getFirst() + " " + listY.getFirst() + " " + listX.getLast() + " " + listY.getLast());
    }
}
