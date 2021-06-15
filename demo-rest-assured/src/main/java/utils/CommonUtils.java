package utils;

import java.util.Random;

public class CommonUtils {
    private static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_LIST = "0123456789";

    public static String getRandomFiveCharsString() {

        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < 5) {
            int index = new Random().nextInt(CHAR_LIST.length());
            rndString.append(CHAR_LIST.charAt(index));
        }
        return rndString.toString();
    }

    public static String getRandomSixNumbersString() {

        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < 6) {
            int index = new Random().nextInt(NUMBER_LIST.length());
            rndString.append(NUMBER_LIST.charAt(index));
        }
        return rndString.toString();
    }
}
