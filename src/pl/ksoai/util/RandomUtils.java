package pl.ksoai.util;

import java.util.Random;

public class RandomUtils {

    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(3);
    }
}
