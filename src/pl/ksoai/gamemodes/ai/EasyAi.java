package pl.ksoai.gamemodes.ai;

import pl.ksoai.Game;
import pl.ksoai.util.BoardUtils;
import pl.ksoai.util.RandomUtils;

import java.util.Scanner;

public class EasyAi {
    private static EasyAi instance;

    private EasyAi() {}

    public static EasyAi getInstance() {
        if (instance == null) {
            instance = new EasyAi();
        }

        return instance;
    }

    private void makeEasyMove() {
        System.out.println("Making move level \"easy\"");
        int x = RandomUtils.generateRandomNumber();
        int y = RandomUtils.generateRandomNumber();

        while (!BoardUtils.isCellFree(x,  y)) {
            x = RandomUtils.generateRandomNumber();
            y = RandomUtils.generateRandomNumber();
        }

        Game.board[x][y] = Game.currentPlayerMark;
        BoardUtils.printBoard();
    }

    public void easyVsEasy() {
        do {
            makeEasyMove();
            Game.changePlayer();
        } while (!Game.checkForWin() && !BoardUtils.isBoardFull());

        if (!Game.checkForWin() && BoardUtils.isBoardFull()) {
            System.out.println("Draw");
        } else if (Game.checkForWin()) {
            System.out.println(Game.winningMark + " wins!");
        }
    }

    public void userVsEasy(String input, Scanner scanner) {
        String[] inputArray = input.split(" ");
        if (inputArray[1].equals("easy")) {
            do {
                makeEasyMove();
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
                    makeEasyMove();
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
