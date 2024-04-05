package org.example.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class G3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        HashSet<Point> points = new HashSet<>();
        for (int i = 0; i < n; i++) {
            String[] split = br.readLine().split(" ");
            points.add(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        int limit = 1_000_000_000;

        Map<Integer, int[]> missingPoints = new HashMap<>();
        for (Point point1 : points) {
            for (Point point2 : points) {
                if (!point1.equals(point2)) {
                    int[] cb = findPoints(point1.x, point1.y, point2.x, point2.y);
                    Point c = new Point(cb[0], cb[1]);
                    Point b = new Point(cb[2], cb[3]);
                    if (Math.abs(cb[0]) > limit || Math.abs(cb[1]) > limit || Math.abs(cb[2]) > limit || Math.abs(cb[3]) > limit) continue;
                    if (!points.contains(c) && !points.contains(b)) {
                        missingPoints.put(2, cb);
                    } else if (!points.contains(c)) {
                        missingPoints.put(1, new int[]{cb[0], cb[1]});
                    } else if (!points.contains(b)) {
                        missingPoints.put(1, new int[]{cb[2], cb[3]});
                    } else {
                        missingPoints.put(0, new int[2]);
                    }
                }
            }
        }

        if (missingPoints.get(0) != null) {
            System.out.print(0);
        } else if (missingPoints.get(1) != null) {
            int[] ans = missingPoints.get(1);
            System.out.print(1 + "\n" + ans[0] + " " + ans[1]);
        } else if (missingPoints.get(2) != null) {
            int[] ans = missingPoints.get(2);
            System.out.print(2 + "\n" + ans[0] + " " + ans[1] + "\n" + ans[2] + " " + ans[3]);
        } else {
            for (int[] value : missingPoints.values()) {
                System.out.print(3 + "\n" + value[0] + " " + (value[1] - 1) + "\n"
                                        + (value[0] + 1) + " " + value[1] + "\n"
                                        + (value[0] + 1) + " " + (value[1] - 1));
                break;
            }
        }
    }

    public static int[] findPoints(int ax, int ay, int bx, int by) {
        int abx = bx - ax;
        int aby = by - ay;
        return new int[] {-aby + ax, abx + ay, -aby + bx, abx + by};
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
