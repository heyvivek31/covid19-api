package com.covid19.plasma.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PlasmaUtil {

    public static String maskLeavingLast4(String inputPhoneNum) {
        return inputPhoneNum.replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }

    public static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
