package com.covid19.plasma.util;

public class PlasmaUtil {

    public static String maskLeavingLast4(String inputPhoneNum) {
        return inputPhoneNum.replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }
}
