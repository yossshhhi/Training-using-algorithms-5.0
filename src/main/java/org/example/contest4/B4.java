package org.example.contest4;

import java.util.Scanner;

public class B4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();

        long left = 1;
        long right = n;
        long maxK = 0;

        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (canPlaceShips(n, mid)) {
                maxK = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(maxK);
    }

    private static boolean canPlaceShips(long n, long k) {
        long remaining = n;
        for (long ship = 1, cell = k; ship <= k; ship++, cell--) {
            remaining -= ship * cell;
            remaining -= ship;
            if (remaining < -1)
                break;
        }
        remaining++;
        return remaining >= 0;
    }
}
