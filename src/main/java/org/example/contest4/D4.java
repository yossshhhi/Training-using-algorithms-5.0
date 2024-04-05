package org.example.contest4;

import java.io.*;
import java.util.StringTokenizer;

public class D4 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             FileWriter writer = new FileWriter("output.txt")) {

            StringTokenizer st = new StringTokenizer(reader.readLine());
            int width = Integer.parseInt(st.nextToken());
            int[] first = new int[Integer.parseInt(st.nextToken())];
            int[] second = new int[Integer.parseInt(st.nextToken())];

            st = new StringTokenizer(reader.readLine());
            for (int i = 0; i < first.length; i++) {
                first[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(reader.readLine());
            for (int i = 0; i < second.length; i++) {
                second[i] = Integer.parseInt(st.nextToken());
            }

            writer.write("" + findMinPageLength(width, first, second));
            writer.flush();
        }
    }

    public static int findMinPageLength(int pageWidth, int[] leftArray, int[] rightArray) {
        int left = 1;
        int right = pageWidth;
        int lines = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int leftLines = countLines(leftArray, mid);
            int rightLines = countLines(rightArray, pageWidth - mid);

            if (leftLines == rightLines) {
                return leftLines;
            } else if (rightLines == -1) {
                right = mid - 1;
            } else if (leftLines == -1) {
                left = mid + 1;
            } else if (leftLines > rightLines) {
                left = mid + 1;
                lines = leftLines;
            } else {
                right = mid - 1;
                lines = rightLines;
            }
        }

        return lines;
    }

    public static int countLines(int[] wordLengths, int pageWidth) {
        int lines = 1;
        int remainingWidth = pageWidth;

        for (int length : wordLengths) {
            if (length > pageWidth) {
                return -1;
            }
            if (length <= remainingWidth) {
                remainingWidth -= length;
            } else {
                lines++;
                remainingWidth = pageWidth - length;
            }
            if (remainingWidth > 0) {
                remainingWidth--;
            }
        }

        return lines;
    }
}
