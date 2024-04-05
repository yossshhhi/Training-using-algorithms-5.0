package org.example.contest2;

import java.util.*;

public class I2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[][] board = new int[n][n];
        List<Integer> columns = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            board[--row][--col] = 1;
            columns.add(col);
        }

        Collections.sort(columns);
        int midlCol = columns.size() % 2 != 0 ? columns.get(columns.size() / 2)
                : (columns.get(columns.size() / 2 - 1) + columns.get(columns.size() / 2)) / 2;
        int moves = count(board, midlCol);
        System.out.println(moves);
    }

    public static int topDown(int[][] board, int row, int col) {
        int moves = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    moves += Math.abs(row - i);
                    moves += Math.abs(col - j);
                    board[row][col] = 2;
                    board[i][j] = 0;
                    return moves;
                }
            }
        }
        return moves;
    }

    public static int count(int[][] board, int midlCol) {
        int moves = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][midlCol] == 0) {
                moves += topDown(board, i, midlCol);
            } else {
                board[i][midlCol] = 2;
            }
        }
        return moves;
    }
}
