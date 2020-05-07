package pl.ksoai.util;

public class StringArrayUtils {

    public static int[] convertStringArrayToIntArray(String[] stringArray) {
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
}
