package org.example.contest4;

import java.io.*;
import java.util.*;

public class F4 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String[] parts = reader.readLine().split(" ");
            int tiles = Integer.parseInt(parts[2]);
            TreeSet<Integer> points = new TreeSet<>();
            int[][] xy = new int[tiles][2];
            for (int i = 0; i < tiles; i++) {
                parts = reader.readLine().split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                xy[i] = new int[]{x, y};
                points.add(x);
            }
            Arrays.sort(xy, Comparator.comparingInt(a -> a[0]));

            TreeMap<Integer, int[]> prefixMinMax = new TreeMap<>();
            TreeMap<Integer, int[]> reversePrefixMinMax = new TreeMap<>();
            List<Integer> sortedSet = new ArrayList<>(tiles);
            sortedSet.add(xy[0][0]);

            int maxSoFar = Integer.MIN_VALUE;
            int minSoFar = Integer.MAX_VALUE;
            int rightMaxSoFar = Integer.MIN_VALUE;
            int rightMinSoFar = Integer.MAX_VALUE;
            int j = xy.length - 1;
            int last = sortedSet.getFirst();

            for (int i = 0; i < xy.length; i++, j--) {
                int x = xy[i][0];
                int y = xy[i][1];
                int rx = xy[j][0];
                int ry = xy[j][1];

                maxSoFar = Math.max(maxSoFar, y);
                minSoFar = Math.min(minSoFar, y);
                rightMaxSoFar = Math.max(rightMaxSoFar, ry);
                rightMinSoFar = Math.min(rightMinSoFar, ry);
                prefixMinMax.put(x, new int[]{maxSoFar, minSoFar});
                reversePrefixMinMax.put(rx, new int[]{rightMaxSoFar, rightMinSoFar});

                if (last != x) {
                    sortedSet.add(x);
                    last = x;
                }
            }

            int[] indexStart = new int[]{-1, 0};
            int result = Integer.MAX_VALUE;
            int left = 0, right = points.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                indexStart = check(sortedSet.get(mid), sortedSet, points, indexStart[1], prefixMinMax, reversePrefixMinMax);
                if (indexStart[0] == 0) {
                    right = mid - 1;
                    result = Math.min(result, mid);
                } else {
                    left = mid + 1;
                }
            }
            if (result - 1 >= 0) {
                left = sortedSet.get(result - 1);
            } else {
                left = 1;
            }

            right = sortedSet.get(result);
            result = Integer.MAX_VALUE;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                indexStart = check(mid, sortedSet, points, indexStart[1], prefixMinMax, reversePrefixMinMax);
                if (indexStart[0] == 0) {
                    right = mid - 1;
                    result = Math.min(result, mid);
                } else {
                    left = mid + 1;
                }
            }
            writer.write(String.valueOf(result));
            writer.flush();

        }
    }

    public static int[] check(int width, List<Integer> tiles, TreeSet<Integer> set, int index, TreeMap<Integer, int[]> leftMaxMin, TreeMap<Integer, int[]> rightMaxMin) {
        int max;
        int min;
        Integer prev = null;
        Integer next;
        int last = tiles.getLast();

        for (int i = index; i < tiles.size(); i++) {
            int current = tiles.get(i);
            if (i - 1 >= 0) {
                prev = tiles.get(i - 1);
            }
            int target = current + width;
            if (target == last) {
                next = last;
            } else {
                if (last > target) {
                    next = set.ceiling(target);
                } else {
                    next = null;
                }
            }

            if (prev == null && next == null) {
                return new int[]{0, i};
            } else if (prev == null) {
                int[] minMax = rightMaxMin.get(next);
                max = minMax[0];
                min = minMax[1];
            } else if (next == null) {
                int[] minMax = leftMaxMin.get(prev);
                max = minMax[0];
                min = minMax[1];
            } else {
                int[] left = leftMaxMin.get(prev);
                int[] right = rightMaxMin.get(next);
                max = Math.max(right[0], left[0]);
                min = Math.min(right[1], left[1]);
            }

            if (max - min + 1 <= width) {
                return new int[]{0, i};
            }

        }
        return new int[]{-1, index};
    }
}