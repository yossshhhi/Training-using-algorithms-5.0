package org.example.contest1;

import java.util.Scanner;

public class G1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int soldiers = scanner.nextInt();
        int barracks = scanner.nextInt();
        int newEnemies = scanner.nextInt();

        int enemies = 0;
        int actions = soldiers;
        int round = 0;

        do {
            if (barracks > 0) {
                int checkWin = checkWin(soldiers, barracks, enemies);

                if (checkWin > 1) {
                    round += checkWin;
                    break;
                }

                int committedActions = soldiers > enemies ?
                        (checkWin == 1
                                ? barracks
                                : soldiers - enemies)
                        : Math.min(soldiers, barracks);
                barracks -= committedActions;
                actions = soldiers - committedActions;
            }

            if (enemies > 0 && actions > 0) {
                enemies -= actions;
            }

            if (enemies > 0) {
                soldiers -= enemies;
                if (soldiers < 1) {
                    System.out.println(-1);
                    return;
                } else {
                    actions = soldiers;
                }
            }

            if (barracks > 0) {
                enemies += newEnemies;
            }

            round++;
        } while (enemies > 0 || barracks > 0);

        System.out.println(round);
        scanner.close();
    }

    private static int checkWin(int soldiers, int barracks, int enemies) {
        if (enemies < 1) return 0;
        if (soldiers * 1.618 >= barracks + enemies && barracks <= soldiers) {
            int rounds = isLastFourRounds(soldiers, barracks, enemies);
            if (rounds > 0)
                return rounds;
            else return 1;
        }
        return 0;
    }

    private static int isLastFourRounds(int soldiers, int barracks, int enemies) {
        if (barracks < 1) return 0;
        // 1
        int attackForBarracks = soldiers - enemies;
        int barracksLeft = barracks - attackForBarracks;
        //2
        int x = soldiers - barracksLeft;
        int enemiesLeft = enemies - x;
        int soldiersLeft = soldiers - enemiesLeft;
        int rounds = 2;

        while (enemiesLeft > 0) {
            enemiesLeft -= soldiersLeft;
            soldiersLeft -= enemiesLeft;
            rounds++;
        }

        int x2 = soldiers - barracks;
        int enemiesLeft2 = enemies - x2;
        int soldiersLeft2 = soldiers - enemiesLeft2;
        int rounds2 = 1;

        while (enemiesLeft2 > 0) {
            enemiesLeft2 -= soldiersLeft2;
            soldiersLeft2 -= enemiesLeft2;
            rounds2++;
        }

        if (rounds > 2 && rounds2 > 1) {
            return Math.min(rounds, rounds2);
        }

        return 0;
    }
}
