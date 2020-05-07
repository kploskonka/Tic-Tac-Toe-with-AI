package pl.ksoai.gamemodes;

import pl.ksoai.Game;
import pl.ksoai.util.BoardUtils;

import java.util.Scanner;

public class UserVsUser {
    private static UserVsUser instance;

    private UserVsUser(){}

    public static UserVsUser getInstance() {
        if (instance == null) {
            instance = new UserVsUser();
        }

        return instance;
    }

    public void userVsUser(Scanner scanner) {
        do {
            Game.askForMove(scanner);
            Game.changePlayer();
        } while (!Game.checkForWin() && !BoardUtils.isBoardFull());

        if (!Game.checkForWin() && BoardUtils.isBoardFull()) {
            System.out.println("Draw");
        } else if (Game.checkForWin()) {
            System.out.println(Game.winningMark + " wins!");
        }
    }
}
