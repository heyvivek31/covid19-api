package com.covid19.plasma.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlasmaUtil {

    public static String maskLeavingLast4(String inputPhoneNum) {
        //return inputPhoneNum.replaceAll("\\d(?=(?:\\D*\\d){4})", "X");
        return inputPhoneNum.replaceAll("(?<!^.?).(?!.?$)", "X");
    }

    public static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    public static boolean issTenDigitsNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
