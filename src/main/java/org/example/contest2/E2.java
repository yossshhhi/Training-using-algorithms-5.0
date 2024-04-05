package org.example.contest2;

import java.util.*;

public class E2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Berry> positive = new ArrayList<>();
        List<Berry> negative = new ArrayList<>();
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            Berry berry = new Berry(i + 1, scanner.nextInt(), scanner.nextInt());
            if (berry.getPotentialHeight() >= 0)
                positive.add(berry);
            else
                negative.add(berry);
        }

        long sumPos = positive.stream().mapToLong(Berry::getPotentialHeight).sum();
        int indexOfNeg = 0;
        int indexOfPos = 0;
        long temp;
        long maxPos = 0;
        for (Berry berry : positive) {
            temp = sumPos - berry.getPotentialHeight() + berry.getUp();
            if (temp > maxPos) {
                maxPos = temp;
                indexOfPos = positive.indexOf(berry);
            }
        }
        moveElement(positive, indexOfPos, positive.size() - 1);

        for (Berry berry : negative) {
            temp = sumPos + berry.getUp();
            if (temp > maxPos) {
                maxPos = temp;
                indexOfNeg = negative.indexOf(berry);
            }
        }
        if (indexOfNeg != 0) {
            moveElement(negative, indexOfNeg, 0);
        }

        System.out.print(maxPos + "\n");
        positive.forEach(berry -> System.out.print(berry.getId() + " "));
        negative.forEach(berry -> System.out.print(berry.getId() + " "));
    }

    static void moveElement(List<Berry> list, int currentIndex, int newIndex) {
        if (currentIndex >= 0 && currentIndex < list.size() &&
                newIndex >= 0 && newIndex < list.size()) {
            Berry element = list.remove(currentIndex);
            list.add(newIndex, element);
        }
    }

    static class Berry {
        private long id;
        private long up;
        private long down;

        public Berry(long id, long up, long down) {
            this.id = id;
            this.up = up;
            this.down = down;
        }

        public long getId() {
            return id;
        }

        public long getUp() {
            return up;
        }

        public long getPotentialHeight() {
            return up - down;
        }
    }
}
