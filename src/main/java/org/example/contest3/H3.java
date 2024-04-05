package org.example.contest3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class H3 {

    public static class Vector {
        int a, b;

        public Vector(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Vector vector = (Vector) object;
            return a == vector.a && b == vector.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    public static class Segment {
        int x1, y1, x2, y2;
        int a, b;
        public Segment(int x1, int y1, int x2, int y2) {
            if (y1 > y2 || (y1 == y2 && x1 > x2)) {
                this.x1 = x2; this.y1 = y2; this.x2 = x1; this.y2 = y1;
            } else {
                this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
            }
            this.a = this.x2 - this.x1;
            this.b = this.y2 - this.y1;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Segment segment = (Segment) object;
            return x1 == segment.x1 && y1 == segment.y1 && x2 == segment.x2 && y2 == segment.y2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("23H3.txt")));
        int n = Integer.parseInt(br.readLine());
        Set<Segment> setA = new HashSet<>();
        Set<Segment> setB = new HashSet<>();
        Map<Vector, List<Segment>> segmentsA = readInput(br, n, setA);
        Map<Vector, List<Segment>> segmentsB = readInput(br, n, setB);
        Map<Vector, Integer> shifts = new HashMap<>();

        int maxOverlap = Integer.MIN_VALUE;
        for (Map.Entry<Vector, List<Segment>> a : segmentsA.entrySet()) {
            Vector key = a.getKey();
            if (segmentsB.containsKey(key)) {
                findShifts(a.getValue(), segmentsB.get(key), shifts);
            }
        }
        Vector maxShift = null;
        for (Map.Entry<Vector, Integer> entry : shifts.entrySet()) {
            Vector shift = entry.getKey();
            int overlap = entry.getValue();
            if (overlap > maxOverlap) {
                maxOverlap = overlap;
                maxShift = shift;
            }
        }
        int overlap = maxShift != null ? calculateOverlap(setA, setB, maxShift.a, maxShift.b) : 0;
        if (overlap > maxOverlap) {
            maxOverlap = overlap;
        }

        System.out.println(n - maxOverlap);
    }

    public static void findShifts(List<Segment> segmentsA, List<Segment> segmentsB, Map<Vector, Integer> shifts) {
        for (Segment a : segmentsA) {
            for (Segment b : segmentsB) {
                Vector vector = new Vector(b.x1 - a.x1, b.y1 - a.y1);
                shifts.put(vector, shifts.getOrDefault(vector, 0) + 1);
            }
        }
    }

    private static int calculateOverlap(Set<Segment> segmentsA, Set<Segment> segmentsB, int shiftX, int shiftY) {
        int count = 0;
        for (Segment segmentA : segmentsA) {
            if (segmentsB.contains(overlap(segmentA, shiftX, shiftY))) {
                count++;
            }
        }
        return count;
    }

    private static Segment overlap(Segment segmentA, int shiftX, int shiftY) {
        int ax1 = segmentA.x1 + shiftX;
        int ay1 = segmentA.y1 + shiftY;
        int ax2 = segmentA.x2 + shiftX;
        int ay2 = segmentA.y2 + shiftY;

        return new Segment(ax1, ay1, ax2, ay2);
    }

    private static Map<Vector, List<Segment>> readInput(BufferedReader br, int n, Set<Segment> set) throws IOException {
        Map<Vector, List<Segment>> segments = new HashMap<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer in = new StringTokenizer(br.readLine());
            Segment segment = new Segment(Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()));
            Vector vector = new Vector(segment.a, segment.b);
            List<Segment> list = segments.getOrDefault(vector, new ArrayList<>());
            list.add(segment);
            segments.put(vector, list);
            set.add(segment);
        }
        return segments;
    }
}
