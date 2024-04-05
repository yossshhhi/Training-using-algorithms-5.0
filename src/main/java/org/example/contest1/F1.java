package org.example.contest1;

import java.util.Scanner;

public class F1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        int oddCount = 0;

        for (int i = 0; i < n; i++) {
            if (Math.abs(numbers[i]) % 2 == 1) {
                oddCount++;
            }
            numbers[i] = numbers[i] % 10;
        }

        StringBuilder result = new StringBuilder();
        if (oddCount > 0) {
            if (oddCount % 2 == 1) {
                System.out.println("+".repeat(n - 1));
                return;
            } else {
                for (int i = 1; i < n; i++) {
                    int i1 = numbers[i - 1];
                    int i2 = numbers[i];
                    if (i1 % 2 == 0 && i2 % 2 == 0) {
                        result.append(("+"));
                    } else {
                        result.append("x").append("+".repeat(n - i - 1));
                        System.out.println(result);
                        return;
                    }
                }
            }
        }

        System.out.println(result);
        scanner.close();
    }
}
