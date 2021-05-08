package com.covid19.plasma.util;

import java.util.Base64;

public class GlobalId {

    public static String encode(Long originalInput) {
        return Base64.getEncoder().encodeToString(originalInput.toString().getBytes());
    }

    public static Long decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return decodedBytes != null ? Long.parseLong(new String(decodedBytes)) : 0L;
    }
}
