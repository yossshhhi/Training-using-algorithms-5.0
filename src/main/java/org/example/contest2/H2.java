package org.example.contest2;

import java.util.*;

public class H2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int races = scanner.nextInt();
        int classes = scanner.nextInt();

        Character max1 = new Character(0, 0 ,0);
        Character max2 = new Character(0, 0 ,0);
        Character max3 = new Character(0, 0 ,0);
        int[][] characters = new int[races][classes];

        for (int i = 0; i < races; i++) {
            for (int j = 0; j < classes; j++) {
                characters[i][j] = scanner.nextInt();
                if (characters[i][j] > max1.getStrength())
                    max1.setCharacters(characters[i][j], i, j);
            }
        }

        int[] rowMax1 = copyRow(max1.getRace(), characters, -1);
        int[] colMax1 = copyCol(max1.getClas(), characters, -1);

        for (int i = 0; i < races; i++) {
            for (int j = 0; j < classes; j++) {
                if (i != max1.getRace() && characters[i][j] > max2.getStrength())
                    max2.setCharacters(characters[i][j], i, j);
            }
        }
        for (int i = 0; i < races; i++) {
            for (int j = 0; j < classes; j++) {
                if (j != max1.getClas() && characters[i][j] > max3.getStrength())
                    max3.setCharacters(characters[i][j], i, j);
            }
        }

        int[] rowMax2 = copyRow(max3.getRace(), characters, max1.getClas());
        int[] colMax2 = copyCol(max2.getClas(), characters, max1.getRace());

        int[] var1 = new int[rowMax1.length + colMax2.length];
        System.arraycopy(rowMax1, 0, var1, 0, rowMax1.length);
        System.arraycopy(colMax2, 0, var1, rowMax1.length, colMax2.length);
        int[] var2 = new int[rowMax2.length + colMax1.length];
        System.arraycopy(rowMax2, 0, var2, 0, rowMax2.length);
        System.arraycopy(colMax1, 0, var2, rowMax2.length, colMax1.length);

        Arrays.sort(var1);
        Arrays.sort(var2);

        boolean flag = true;
        for (int i = var1.length - 1; i >= 0; i--) {
            if (var1[i] > var2[i]) {
                System.out.print((max1.getRace() + 1) + " " + (max2.getClas() + 1));
                flag = false;
                break;
            } else if (var1[i] < var2[i]) {
                System.out.print((max3.getRace() + 1) + " " + (max1.getClas() + 1));
                flag = false;
                break;
            }
        }
        if (flag)
            System.out.print((max1.getRace() + 1) + " " + (max2.getClas() + 1));
    }

    private static int[] copyRow(int races, int[][] characters, int clas) {
        int length = clas == -1 ? characters[0].length : characters[0].length - 1;
        int[] row = new int[length];
        for (int i = 0, j = 0 ; i < characters[0].length; i++) {
            if (i != clas)
                row[j++] = characters[races][i];
        }
        return row;
    }

    private static int[] copyCol(int classes, int[][] characters, int race) {
        int length = race == -1 ? characters.length : characters.length - 1;
        int[] col = new int[length];
        for (int i = 0, j = 0; i < characters.length; i++) {
            if (i != race)
                col[j++] = characters[i][classes];
        }
        return col;
    }

    public static class Character {
        private int strength;
        private int race;
        private int clas;

        public Character(int strength, int race, int clas) {
            this.strength = strength;
            this.race = race;
            this.clas = clas;
        }

        public void setCharacters(int strength, int race, int clas) {
            this.strength = strength;
            this.race = race;
            this.clas = clas;
        }
        public int getStrength() {
            return strength;
        }
        public int getRace() {
            return race;
        }
        public int getClas() {
            return clas;
        }
    }
}