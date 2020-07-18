package com.invicto.vaconfigureservice.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestValidator {

    public static boolean isValidUsername(String name) {

        String regex = "^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean isValidPath(String name) {

        if (!(name.indexOf("//") == -1)) {
            return false;
        }
        if (name.charAt(0) != '/')
            return false;
        return true;
    }

    public static boolean isValidProjectName(String name) {

        String regex = "^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean isValidOrganizationName(String name) {

        String regex = "^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean isValidApiMethod(String name) {
        final String[] values = new String[]{"GET", "POST", "PUT", "DELETE", "PATCH"};
        return Arrays.stream(values).anyMatch(name::equals);
    }

    public static boolean isValidHttpStatus(String name) {

        String regex = "^[aA-zZ]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }
}
