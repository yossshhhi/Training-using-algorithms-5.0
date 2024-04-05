package org.example.contest1;

import java.util.*;

public class D1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] lines = new String[8];
        for (int i = 0; i < 8; i++) {
            lines[i] = scanner.nextLine();
        }

        char[][] chessboard = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboard[i][j] = lines[i].charAt(j);
            }
        }


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j] == 'R') {
                    rookMoves(i, j, chessboard);
                } else if (chessboard[i][j] == 'B') {
                    bishopMoves(i, j, chessboard);
                }
            }
        }

        int count = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j] == '*') {
                    count++;
                }
            }
        }

        System.out.println(count);
        scanner.close();
    }

    private static void rookMoves(int i, int j, char[][] chessboard) {
        int x = i;
        int y = j;

        // top
        while (x > 0) {
            char cell = chessboard[--x][y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }
        x = i;
        // bottom
        while (x < 7) {
            char cell = chessboard[++x][y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }
        x = i;
        // left
        while (y > 0) {
            char cell = chessboard[x][--y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }
        y = j;
        // right
        while (y < 7) {
            char cell = chessboard[x][++y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }
    }

    private static void bishopMoves(int i, int j, char[][] chessboard) {
        int x = i;
        int y = j;

        while (x > 0 && y > 0) {
            char cell = chessboard[--x][--y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }

        x = i;
        y = j;

        while (x < 7 && y < 7) {
            char cell = chessboard[++x][++y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }

        x = i;
        y = j;

        while (x > 0 && y < 7) {
            char cell = chessboard[--x][++y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }

        x = i;
        y = j;

        while (x < 7 && y > 0) {
            char cell = chessboard[++x][--y];
            if (cell == '*' || cell == '-') {
                chessboard[x][y] = '-';
            } else {
                break;
            }
        }
    }
}
