package org.example.contest2;

import java.io.*;
import java.util.*;

public class G2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        for (int i = 0; i < n; i++) {
            int arrayLength = Integer.parseInt(reader.readLine());
            int[] numbers = new int[arrayLength];
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < arrayLength; j++) {
                numbers[j] = Integer.parseInt(tokenizer.nextToken());
            }

            int segmentCount = 0;
            int k = 0;
            StringBuilder sb = new StringBuilder();
            while (k < numbers.length) {
                int segmentLength = 1;
                int min = numbers[k + segmentLength - 1];
                while (k + segmentLength <= numbers.length && isValidSegment(k, k + segmentLength, min)) {
                    segmentLength++;
                    if (k + segmentLength <= numbers.length)
                        min = Math.min(min, numbers[k + segmentLength - 1]);
                }
                segmentCount++;
                k += segmentLength - 1;
                sb.append(segmentLength - 1);
                if (k < numbers.length) {
                    sb.append(" ");
                }
            }

            System.out.print(segmentCount + "\n");
            System.out.print(sb + "\n");
        }
    }

    private static boolean isValidSegment(int start, int end, int min) {
        int segmentLength = end - start;
        return min >= segmentLength;
    }
}
