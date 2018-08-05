/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PERSONAL_ID_REGEX
            = Pattern.compile("^[0-9]{8,8}[A-Za-z]$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public static boolean validatePersonalId(String personalIdStr) {
        Matcher matcher = VALID_PERSONAL_ID_REGEX.matcher(personalIdStr);
        return matcher.find();
    }

    public static String underscoreToCapitalized(String textUnderscored) {
        String[] array = textUnderscored.split("_");
        for (int i = 0; i < array.length; i++) {
            String lower = array[i].toLowerCase();
            array[i] = lower.substring(0, 1).toUpperCase() + lower.substring(1, lower.length());
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : array) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        String aux = stringBuilder.toString();
        if (aux.length() > 2) {
            aux = aux.substring(0, aux.length() - 1);
        }
        return aux;
    }

    public static String floatToPrice(float f) {
        return String.format("%.2f", f) + " €";
    }

    public static String floatToRate(float f) {
        return String.format("%.0f", f * 100) + "%";
    }

}
