package org.example.contest1;

import java.util.Scanner;

public class C1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int lines = scanner.nextInt();
        long count = 0;
        for (int i = 0; i < lines; i++) {
            int spaces = scanner.nextInt();
            while (spaces > 0) {
                count += spaces / 4;
                spaces = spaces % 4;
                if (spaces > 0 && spaces < 3) {
                    count += spaces;
                    spaces = 0;
                } else if (spaces == 3) {
                    count += 2;
                    spaces = 0;
                }
            }
        }

        System.out.println(count);
    }
}
