package org.example.contest2;

import java.util.Scanner;

public class F2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] clockwise = new int[n];
        int[] anticlockwise = new int[n];
        int max = 0;
        for (int i = 0, j = n; i < n; i++, j--) {
            clockwise[i] = scanner.nextInt();
            max = Math.max(max, clockwise[i]);
            if (i == 0)
                anticlockwise[i] = clockwise[i];
            else
                anticlockwise[j] = clockwise[i];
        }
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int k = scanner.nextInt();
        scanner.close();

        int maxWin = 0;
        int iter = (b - a) / k + 1;

        for (int i = a, j = 0; j < iter; i += k, j++) {
            int div = i % k == 0 ? i / k - 1 : i / k;
            int temp = div % n;
            if (clockwise[temp] > maxWin || anticlockwise[temp] > maxWin)
                maxWin = Math.max(clockwise[temp], anticlockwise[temp]);
            if (maxWin == max)
                break;
        }

        System.out.print(maxWin);
    }
}
