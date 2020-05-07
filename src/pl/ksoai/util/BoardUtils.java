package pl.ksoai.util;

import pl.ksoai.Game;

public class BoardUtils {

    public static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Game.board[i][j] = ' ';
            }
        }
    }

    public static void printBoard() {
        System.out.println("   1    2    3\n"
                +"  -------------");

        for (int i = 0; i < 3; i++) {
            System.out.print(3 - i + " |");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + Game.board[i][j] + " |");
            }

            System.out.println();
        }

        System.out.println("  -------------");
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Game.board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCellFree(int x, int y) {
        return Game.board[x][y] == ' ';
    }

}
