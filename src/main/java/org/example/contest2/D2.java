package org.example.contest2;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class D2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Set<String> cells = new HashSet<>();

        for (int i = 0; i < n; i++) {
            cells.add(scanner.nextLine());
        }

        int perimeter = 0;

        for (String cell : cells) {
            String[] parts = cell.split(" ");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if ((i == -1 && j == 0) || (i == 0 && j == -1) || (i == 0 && j == 1) || (i == 1 && j == 0)) {
                        int newRow = row + i;
                        int newCol = col + j;
                        if (!cells.contains(newRow + " " + newCol)) {
                            perimeter++;
                        }
                    }
                }
            }
        }
        System.out.println(perimeter);
    }
}
