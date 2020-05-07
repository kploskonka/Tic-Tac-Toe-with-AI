package pl.ksoai;

import pl.ksoai.gamemodes.UserVsUser;
import pl.ksoai.gamemodes.ai.EasyAi;
import pl.ksoai.gamemodes.ai.MediumAi;
import pl.ksoai.util.BoardUtils;
import pl.ksoai.util.StringArrayUtils;
import pl.ksoai.util.Validator;

import java.util.Scanner;

public class Game {

    public static final char[][] board = new char[3][3];
    public static char winningMark = ' ';
    public static char currentPlayerMark = 'X';
    public static boolean gameOn;

    private static void printMenu() {
        System.out.println("=============");
        System.out.println("Available commands:\n" +
                "start user user - starts the game between two players\n" +
                "start user easy - starts the game between user and AI on easy level\n" +
                "start user medium - starts the game between user and AI on medium level\n" +
                "start easy easy - starts the game between two AIs on easy level\n" +
                "start medium medium - starts the game between two AIs on medium level\n" +
                "exit - exits the program");
        System.out.println("=============");
        System.out.print("Input command: ");
    }

    public static void askForMove(Scanner scanner) {
        System.out.print("Enter the coordinates: ");
        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        int[] cellCoords = StringArrayUtils.convertStringArrayToIntArray(inputArray);

        if (cellCoords != null && cellCoords.length == 2) {
            int y = cellCoords[0] - 1;
            int x = cellCoords[1] - 1;

            if (x == 0) {
                x = 2;
            } else if (x == 2) {
                x = 0;
            }

            if (Validator.isCellValid(x, y)) {
                board[x][y] = currentPlayerMark;
                BoardUtils.printBoard();
            } else {
                askForMove(scanner);
            }

        } else {
            askForMove(scanner);
        }
    }

    public static boolean checkRowColForWin(char c1, char c2, char c3) {
        return c1 != ' ' && c1 == c2 && c2 == c3;
    }

    public static boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowColForWin(board[i][0], board[i][1], board[i][2])) {
                winningMark = board[i][0];
                return true;
            }
        }
        return false;
    }

    public static boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowColForWin(board[0][i], board[1][i], board[2][i])) {
                winningMark = board[0][i];
                return true;
            }
        }

        return false;
    }

    public static boolean checkDiagonalsForWin() {
        if (checkRowColForWin(board[0][0], board[1][1], board[2][2])) {
            winningMark = board[1][1];
            return true;
        }

        if (checkRowColForWin(board[0][2], board[1][1], board[2][0])) {
            winningMark = board[1][1];
            return true;
        }

        return false;
    }

    public static boolean checkForWin() {
        return checkRowsForWin() || checkDiagonalsForWin() || checkColumnsForWin();
    }

    public static void changePlayer() {
        if (currentPlayerMark == 'X') {
            currentPlayerMark = 'O';
        } else {
            currentPlayerMark = 'X';
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        gameOn = true;

        do {
            printMenu();
            String input = scanner.nextLine().toLowerCase();

            while (!Validator.isInputValid(input, scanner)) {
                System.out.println("Bad parameters!");
                printMenu();
                input = scanner.nextLine();
            }

            BoardUtils.initializeBoard();
            BoardUtils.printBoard();

            switch (input) {
                case "start easy easy":
                    EasyAi.getInstance().easyVsEasy();
                    break;
                case "start medium medium":
                    MediumAi.getInstance().mediumVsMedium();
                    break;
                case "start hard hard":
                    break;
                case "start user easy":
                case "start easy user":
                    EasyAi.getInstance().userVsEasy(input, scanner);
                    break;
                case "start medium user":
                case "start user medium":
                    MediumAi.getInstance().userVsMedium(input, scanner);
                    break;
                case "start user user":
                    UserVsUser.getInstance().userVsUser(scanner);
                    break;
                default:
                    System.out.println("Bad parameters!");
                    break;
            }

            currentPlayerMark = 'X';
        } while (gameOn);

        scanner.close();
    }
}
