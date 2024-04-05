package org.example.contest4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class A4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(nums);

        int requests = Integer.parseInt(br.readLine());
        for (int i = 0; i < requests; i++) {
            StringTokenizer line = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(line.nextToken());
            int to = Integer.parseInt(line.nextToken());

            int count = countNumbersInRange(nums, from, to);
            System.out.print(count + " ");
        }
    }

    private static int countNumbersInRange(int[] nums, int from, int to) {
        int leftIndex = findFirstGreaterOrEqual(nums, from);
        int rightIndex = findLastLessOrEqual(nums, to);

        if (leftIndex > rightIndex) {
            return 0;
        }

        return rightIndex - leftIndex + 1;
    }

    private static int findFirstGreaterOrEqual(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private static int findLastLessOrEqual(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
}
