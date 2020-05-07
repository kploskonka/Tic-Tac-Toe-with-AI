package pl.ksoai.util;

import pl.ksoai.Game;

import java.util.Scanner;

public class Validator {

    private static boolean isParameterValid(String parameter) {
        return parameter.equals("user") || parameter.equals("easy") || parameter.equals("medium");
    }

    public static boolean isInputValid(String input, Scanner scanner) {
        String[] inputArray = input.split(" ");

        if (input.equals("exit")) {
            Game.gameOn = false;
            scanner.close();
            System.exit(0);
        }

        if (inputArray.length == 3 && inputArray[0].equals("start")) {
            return isParameterValid(inputArray[1]) && isParameterValid(inputArray[2]);
        } else {
            return false;
        }
    }

    private static boolean isCoordinateOnBoard(int x, int y) {
        return x <= 2 && x >= 0 && y <=2 && y >= 0;
    }

    public static boolean isCellValid(int x, int y) {
        if (isCoordinateOnBoard(x, y)) {
            if (BoardUtils.isCellFree(x, y)) {
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
}
