package org.example.contest1;

import java.util.Scanner;

public class E1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] input = scanner.nextLine().split(" ");
        long initialProfit = Long.parseLong(input[0]);
        long founders = Long.parseLong(input[1]);
        long days = Long.parseLong(input[2]);
        String initialProfitBuild = input[0];
        long answer = initialProfit;

        for (int i = 0; i < days; i++) {
            long toCompare = answer;
            for (int j = 0; j < 10; j++) {
                long temp = Long.parseLong(initialProfitBuild + j);
                if (temp % founders == 0) {
                    if (j == 0) {
                        System.out.println(initialProfitBuild + "0".repeat((int) (days - i)));
                        return;
                    }
                    toCompare = temp;
                    initialProfitBuild += j;
                    break;
                }
            }
            if (answer == toCompare) {
                System.out.println(answer == initialProfit ? -1 : answer);
                return;
            } else {
                answer = toCompare;
            }
        }
        System.out.println(answer);
    }
}
