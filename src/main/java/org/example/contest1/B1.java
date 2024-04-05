package org.example.contest1;

import java.util.Scanner;

public class B1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] firstLine = scanner.nextLine().split(":");
        int game1team1 = Integer.parseInt(firstLine[0]);
        int game1team2 = Integer.parseInt(firstLine[1]);

        String[] secondLine = scanner.nextLine().split(":");
        int game2team1 = Integer.parseInt(secondLine[0]);
        int game2team2 = Integer.parseInt(secondLine[1]);

        int homeOrAway = scanner.nextInt();

        int goalsOfTeam1 = game1team1 + game2team1;
        int goalsOfTeam2 = game1team2 + game2team2;

        int result;

        if (goalsOfTeam2 == goalsOfTeam1) {
            result = (homeOrAway == 1) ? (game2team1 > game1team2 ? 0 : 1) : (game1team1 > game2team2 ? 0 : 1);
        } else if (goalsOfTeam2 > goalsOfTeam1) {
            int difference = goalsOfTeam2 - goalsOfTeam1;
            if (homeOrAway == 1) {
                result = (game2team1 > game2team2 || game2team1 + difference > game2team2) ?
                        difference :
                        (game2team1 + difference == game2team2 ? ifEqual1(game2team1 + difference, game1team2, difference) : difference + 1);
            } else {
                result = (game1team1 > game1team2) ? difference : difference + 1;
            }
        } else {
            result = 0;
        }

        System.out.println(result);

        scanner.close();
    }

    private static int ifEqual1(int game2team1, int game1team2, int diff) {
        return (game2team1 > game1team2) ? diff : 1;
    }
}

