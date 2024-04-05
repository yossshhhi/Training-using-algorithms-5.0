package org.example.contest4;

import java.io.*;
import java.util.StringTokenizer;

public class C4 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
             FileWriter writer = new FileWriter("output.txt")) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int[] nums = new int[n];
            int[] result = new int[m];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                nums[i] = Integer.parseInt(st.nextToken());
            }

            long[] cumulativeSum = calculateCumulativeSum(nums);
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int regiments = Integer.parseInt(st.nextToken());
                long orcs = Long.parseLong(st.nextToken());

                result[i] = countNumberOrcs(nums, cumulativeSum, regiments, orcs);
            }

            for (int j : result) {
                writer.write((j == -1 ? j : j + 1) + "\n");
            }
            writer.flush();
        }
    }

    private static int countNumberOrcs(int[] nums, long[] cumulativeSum, int regiments, long orcs) {
        int left = 0;
        int right = nums.length - regiments;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long sum = calculateOrcs(cumulativeSum, mid, mid + regiments - 1);

            if (sum == orcs) {
                return mid;
            } else if (sum > orcs) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static long calculateOrcs(long[] cumulativeSum, int start, int end) {
        return start == 0 ? cumulativeSum[end] : cumulativeSum[end] - cumulativeSum[start - 1];
    }

    public static long[] calculateCumulativeSum(int[] array) {
        long[] cumulativeSum = new long[array.length];
        cumulativeSum[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            cumulativeSum[i] = cumulativeSum[i - 1] + array[i];
        }
        return cumulativeSum;
    }
}
