package pl.ksoai.gamemodes.ai;

import pl.ksoai.Game;
import pl.ksoai.util.BoardUtils;
import pl.ksoai.util.RandomUtils;

import java.util.Scanner;

public class MediumAi {
    private static MediumAi instance;

    private MediumAi(){}

    public static MediumAi getInstance() {
        if (instance == null) {
            instance = new MediumAi();
        }

        return instance;
    }

    private boolean makeMoveIfInRowsAreTwoSame() {
        for (int i = 0; i < 3; i++) {
            if (Game.board[i][0] == Game.board[i][1] && BoardUtils.isCellFree(i, 2)) {
                Game.board[i][2] = Game.currentPlayerMark;
                return true;
            }

            if (Game.board[i][0] == Game.board[i][2] && BoardUtils.isCellFree(i, 1)) {
                Game.board[i][1] = Game.currentPlayerMark;
                return true;
            }

            if (Game.board[i][1] == Game.board[i][2] && BoardUtils.isCellFree(i, 0)) {
                Game.board[i][0] = Game.currentPlayerMark;
                return true;
            }
        }

        return false;
    }

    private boolean makeMoveIfInColumnsAreTwoSame() {
        for (int i = 0; i < 3; i++) {
            if (Game.board[0][i] == Game.board[1][i] && BoardUtils.isCellFree(2, i)) {
                Game.board[2][i] = Game.currentPlayerMark;
                return true;
            }

            if (Game.board[0][i] == Game.board[2][i] && BoardUtils.isCellFree(1, i)) {
                Game.board[1][i] = Game.currentPlayerMark;
                return true;
            }

            if (Game.board[1][i] == Game.board[2][i] && BoardUtils.isCellFree(0, i)) {
                Game.board[0][i] = Game.currentPlayerMark;
                return true;
            }
        }

        return false;
    }

    private boolean makeMoveIfInDiagonalsAreTwoSame() {
        if (Game.board[0][0] == Game.board[1][1] && BoardUtils.isCellFree(2, 2)) {
            Game.board[2][2] = Game.currentPlayerMark;
            return true;
        }

        if (Game.board[0][0] == Game.board[2][2] && BoardUtils.isCellFree(1, 1)) {
            Game.board[1][1] = Game.currentPlayerMark;
            return true;
        }

        if (Game.board[1][1] == Game.board[2][2] && BoardUtils.isCellFree(0, 0)) {
            Game.board[0][0] = Game.currentPlayerMark;
            return true;
        }

        return false;
    }

    private void makeMediumMove() {
        System.out.println("Making move level \"medium\"");

        if (!makeMoveIfInRowsAreTwoSame() && !makeMoveIfInColumnsAreTwoSame() && !makeMoveIfInDiagonalsAreTwoSame()) {
            int x = RandomUtils.generateRandomNumber();
            int y = RandomUtils.generateRandomNumber();

            while (!BoardUtils.isCellFree(x, y)) {
                x = RandomUtils.generateRandomNumber();
                y = RandomUtils.generateRandomNumber();
            }

            Game.board[x][y] = Game.currentPlayerMark;
        }

        BoardUtils.printBoard();
    }

    public void mediumVsMedium() {
        do {
            makeMediumMove();
            Game.changePlayer();
        } while (!Game.checkForWin() && !BoardUtils.isBoardFull());

        if (!Game.checkForWin() && BoardUtils.isBoardFull()) {
            System.out.println("Draw");
        } else if (Game.checkForWin()) {
            System.out.println(Game.winningMark + " wins!");
        }
    }

    public void userVsMedium(String input, Scanner scanner) {
        String[] inputArray = input.split(" ");
        if (inputArray[1].equals("medium")) {
            do {
                makeMediumMove();
                Game.changePlayer();

                if (!Game.checkForWin() && !BoardUtils.isBoardFull()) {
                    Game.askForMove(scanner);
                    Game.changePlayer();
                }
            } while (!Game.checkForWin() && !BoardUtils.isBoardFull());

            if (!Game.checkForWin() && BoardUtils.isBoardFull()) {
                System.out.println("Draw");
            } else if (Game.checkForWin()) {
                System.out.println(Game.winningMark + " wins!");
            }

        } else if (inputArray[1].equals("user")) {
            do {
                Game.askForMove(scanner);
                Game.changePlayer();

                if (!Game.checkForWin() && !BoardUtils.isBoardFull()) {
                    makeMediumMove();
                    Game.changePlayer();
                }

            } while (!Game.checkForWin() && !BoardUtils.isBoardFull());

            if (!Game.checkForWin() && BoardUtils.isBoardFull()) {
                System.out.println("Draw");
            } else if (Game.checkForWin()) {
                System.out.println(Game.winningMark + " wins!");
            }
        }
    }
}
