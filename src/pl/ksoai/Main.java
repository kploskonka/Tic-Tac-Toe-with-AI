package pl.ksoai;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static char[][] board = new char[3][3];
    private static char winningMark = ' ';
    private static char currentPlayerMark = 'X';
    private static boolean gameOn;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        gameOn = true;

        do {
            System.out.print("Input command: ");
            String input = scanner.nextLine().toLowerCase();

            while (!isInputValid(input, scanner)) {
                System.out.println("Bad parameters!");
                System.out.print("Input command: ");
                input = scanner.nextLine();
            }

            initializeBoard();
            printBoard();

            switch (input) {
                case "start easy easy":
                    easyVsEasy();
                    break;
                case "start medium medium":
                    mediumVsMedium();
                    break;
                case "start hard hard":
                    break;
                case "start user easy":
                case "start easy user":
                    userVsEasy(input, scanner);
                    break;
                case "start medium user":
                case "start user medium":
                    userVsMedium(input, scanner);
                    break;
                case "start hard user":
                case "start user hard":
                    break;
                case "start user user":
                    userVsUser(scanner);
                    break;
                default:
                    System.out.println("Bad parameters!");
                    break;
            }

            currentPlayerMark = 'X';
        } while (gameOn);

        scanner.close();
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void printBoard() {
        System.out.println("---------");

        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }

        System.out.println("---------");
    }

    private static boolean isInputValid(String input, Scanner scanner) {
        String[] inputArray = input.split(" ");

        if (input.equals("exit")) {
            gameOn = false;
            scanner.close();
            System.exit(0);
        }

        if (inputArray.length == 3 && inputArray[0].equals("start")) {
            return isParameterValid(inputArray[1]) && isParameterValid(inputArray[2]);
        } else {
            return false;
        }
    }

    private static boolean isParameterValid(String parameter) {
        return parameter.equals("user") || parameter.equals("easy") || parameter.equals("medium");
    }

    private static void askForMove(Scanner scanner) {
        System.out.print("Enter the coordinates: ");
        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        int[] cellCoords = convertStringArrayToIntArray(inputArray);

        if (cellCoords != null && cellCoords.length == 2) {
            int y = cellCoords[0] - 1;
            int x = cellCoords[1] - 1;

            if (x == 0) {
                x = 2;
            } else if (x == 2) {
                x = 0;
            }

            if (isCellValid(x, y)) {
                board[x][y] = currentPlayerMark;
                printBoard();
            } else {
                askForMove(scanner);
            }

        } else {
            askForMove(scanner);
        }
    }

    private static int[] convertStringArrayToIntArray(String[] stringArray) {
        int[] intArray = new int[stringArray.length];
        try {
            for (int i = 0; i < stringArray.length; i++) {
                intArray[i] = Integer.parseInt(stringArray[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return null;
        }

        return intArray;
    }

    private static boolean isCellValid(int x, int y) {
       if (isCoordOnBoard(x, y)) {
          if (isCellFree(x, y)) {
              return true;
          } else {
              System.out.println("This cell is occupied! Choose another one!");
              return false;
          }
       } else {
           System.out.println("Coordinates should be from 1 to 3!");
           return false;
       }
    }

    private static boolean isCellFree(int x, int y) {
        return board[x][y] == ' ';
    }

    private static boolean isCoordOnBoard(int x, int y) {
        return x <= 2 && x >= 0 && y <=2 && y >= 0;
    }

    private static boolean checkRowColForWin(char c1, char c2, char c3) {
        return c1 != ' ' && c1 == c2 && c2 == c3;
    }

    private static boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowColForWin(board[i][0], board[i][1], board[i][2])) {
                winningMark = board[i][0];
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowColForWin(board[0][i], board[1][i], board[2][i])) {
                winningMark = board[0][i];
                return true;
            }
        }

        return false;
    }

    private static boolean checkDiagonalsForWin() {
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

    private static boolean checkForWin() {
        return checkRowsForWin() || checkDiagonalsForWin() || checkColumnsForWin();
    }

    private static boolean makeMoveIfInRowsAreTwoSame() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && isCellFree(i, 2)) {
                board[i][2] = currentPlayerMark;
                return true;
            }

            if (board[i][0] == board[i][2] && isCellFree(i, 1)) {
                board[i][1] = currentPlayerMark;
                return true;
            }

            if (board[i][1] == board[i][2] && isCellFree(i, 0)) {
                board[i][0] = currentPlayerMark;
                return true;
            }
        }

        return false;
    }

    private static boolean makeMoveIfInColumnsAreTwoSame() {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && isCellFree(2, i)) {
                board[2][i] = currentPlayerMark;
                return true;
            }

            if (board[0][i] == board[2][i] && isCellFree(1, i)) {
                board[1][i] = currentPlayerMark;
                return true;
            }

            if (board[1][i] == board[2][i] && isCellFree(0, i)) {
                board[0][i] = currentPlayerMark;
                return true;
            }
        }

        return false;
    }

    private static boolean makeMoveIfInDiagonalsAreTwoSame() {
        if (board[0][0] == board[1][1] && isCellFree(2, 2)) {
            board[2][2] = currentPlayerMark;
            return true;
        }

        if (board[0][0] == board[2][2] && isCellFree(1, 1)) {
            board[1][1] = currentPlayerMark;
            return true;
        }

        if (board[1][1] == board[2][2] && isCellFree(0, 0)) {
            board[0][0] = currentPlayerMark;
            return true;
        }

        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void makeEasyMove() {
        System.out.println("Making move level \"easy\"");
        int x = generateRandomNumber();
        int y = generateRandomNumber();

        while (!isCellFree(x,  y)) {
            x = generateRandomNumber();
            y = generateRandomNumber();
        }

        board[x][y] = currentPlayerMark;
        printBoard();
    }

    private static void makeMediumMove() {
        System.out.println("Making move level \"medium\"");

        if (!makeMoveIfInRowsAreTwoSame() && !makeMoveIfInColumnsAreTwoSame() && !makeMoveIfInDiagonalsAreTwoSame()) {
            int x = generateRandomNumber();
            int y = generateRandomNumber();

            while (!isCellFree(x, y)) {
                x = generateRandomNumber();
                y = generateRandomNumber();
            }

            board[x][y] = currentPlayerMark;
        }

        printBoard();
    }

    private static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(3);
    }

    private static void changePlayer() {
        if (currentPlayerMark == 'X') {
            currentPlayerMark = 'O';
        } else {
            currentPlayerMark = 'X';
        }
    }

    private static void userVsUser(Scanner scanner) {
        do {
            askForMove(scanner);
            changePlayer();
        } while (!checkForWin() && !isBoardFull());

        if (!checkForWin() && isBoardFull()) {
            System.out.println("Draw");
        } else if (checkForWin()) {
            System.out.println(winningMark + " wins!");
        }
    }

    private static void easyVsEasy() {
        do {
            makeEasyMove();
            changePlayer();
        } while (!checkForWin() && !isBoardFull());

        if (!checkForWin() && isBoardFull()) {
            System.out.println("Draw");
        } else if (checkForWin()) {
            System.out.println(winningMark + " wins!");
        }
    }

    private static void userVsEasy(String input, Scanner scanner) {
        String[] inputArray = input.split(" ");
        if (inputArray[1].equals("easy")) {
            do {
                makeEasyMove();
                changePlayer();

                if (!checkForWin() && !isBoardFull()) {
                    askForMove(scanner);
                    changePlayer();
                }
            } while (!checkForWin() && !isBoardFull());

            if (!checkForWin() && isBoardFull()) {
                System.out.println("Draw");
            } else if (checkForWin()) {
                System.out.println(winningMark + " wins!");
            }
        } else if (inputArray[1].equals("user")) {
            do {
                askForMove(scanner);
                changePlayer();

                if (!checkForWin() && !isBoardFull()) {
                    makeEasyMove();
                    changePlayer();
                }

            } while (!checkForWin() && !isBoardFull());

            if (!checkForWin() && isBoardFull()) {
                System.out.println("Draw");
            } else if (checkForWin()) {
                System.out.println(winningMark + " wins!");
            }
        }
    }

    private static void mediumVsMedium() {
        do {
            makeMediumMove();
            changePlayer();
        } while (!checkForWin() && !isBoardFull());

        if (!checkForWin() && isBoardFull()) {
            System.out.println("Draw");
        } else if (checkForWin()) {
            System.out.println(winningMark + " wins!");
        }
    }

    private static void userVsMedium(String input, Scanner scanner) {
        String[] inputArray = input.split(" ");
        if (inputArray[1].equals("medium")) {
            do {
                makeMediumMove();
                changePlayer();

                if (!checkForWin() && !isBoardFull()) {
                    askForMove(scanner);
                    changePlayer();
                }
            } while (!checkForWin() && !isBoardFull());

            if (!checkForWin() && isBoardFull()) {
                System.out.println("Draw");
            } else if (checkForWin()) {
                System.out.println(winningMark + " wins!");
            }

        } else if (inputArray[1].equals("user")) {
            do {
                askForMove(scanner);
                changePlayer();

                if (!checkForWin() && !isBoardFull()) {
                    makeMediumMove();
                    changePlayer();
                }

            } while (!checkForWin() && !isBoardFull());

            if (!checkForWin() && isBoardFull()) {
                System.out.println("Draw");
            } else if (checkForWin()) {
                System.out.println(winningMark + " wins!");
            }
        }
    }

    private static int evaluate(char[][] board) {

        // Checking for Rows for X or O victory.
        for (int row = 0; row<3; row++) {
            if (board[row][0] == board[row][1] &&
                    board[row][1] == board[row][2]) {
                if (board[row][0] == currentPlayerMark)
                    return +10;
                else if (board[row][0] != ' ')
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col<3; col++)
        {
            if (board[0][col]==board[1][col] &&
                    board[1][col]==board[2][col])
            {
                if (board[0][col] == currentPlayerMark)
                    return +10;

                else if (board[0][col] != ' ')
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2])
        {
            if (board[0][0] == currentPlayerMark)
                return +10;
            else if (board[0][0] != ' ')
                return -10;
        }

        if (board[0][2]==board[1][1] && board[1][1]==board[2][0])
        {
            if (board[0][2] == currentPlayerMark)
                return +10;
            else if (board[0][2] != ' ')
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    private static int minimax(char[][] board, int depth) {
        int score = evaluate(board);

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score == 10) return score;

        // If Minimizer has won the game return his/her
        // evaluated score
        if (score == -10) return score;

        // If there are no more moves and no winner then
        // it is a tie
        if (!isBoardFull()) return 0;

        int best = -1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='_')
                    {
                        // Make the move
                        board[i][j] = currentPlayerMark;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board, depth + 1) );

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
    }

}
