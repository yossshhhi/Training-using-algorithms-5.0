package org.example.contest1;

import java.util.Scanner;

public class A1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] firstLine = scanner.nextLine().split(" ");
        int vsBucketNearTree = Integer.parseInt(firstLine[0]);
        int distanceFromVsBucket = Integer.parseInt(firstLine[1]);
        String[] secondLine = scanner.nextLine().split(" ");
        int msBucketNearTree = Integer.parseInt(secondLine[0]);
        int distanceFromMsBucket = Integer.parseInt(secondLine[1]);

        int startOfVsTrees = vsBucketNearTree - distanceFromVsBucket;
        int endOfVsTrees = vsBucketNearTree + distanceFromVsBucket;
        int startOfMsTrees = msBucketNearTree - distanceFromMsBucket;
        int endOfMsTrees = msBucketNearTree + distanceFromMsBucket;

        int minValue = Math.min(Math.min(startOfVsTrees, endOfVsTrees), Math.min(startOfMsTrees, endOfMsTrees));
        int maxValue = Math.max(Math.max(startOfVsTrees, endOfVsTrees), Math.max(startOfMsTrees, endOfMsTrees));

        int treesLength = Math.abs(maxValue - minValue);
        if (minValue <= 0) {
            treesLength++;
        }
        int currentTree = minValue;
        int paintedTrees = 0;

        for (int i = 0; i < treesLength; i++) {
            if ((startOfVsTrees <= currentTree && endOfVsTrees >= currentTree) || (startOfMsTrees <= currentTree && endOfMsTrees >= currentTree)) {
                paintedTrees++;
            }
            currentTree++;
        }

        System.out.println(paintedTrees);
        scanner.close();
    }
}
