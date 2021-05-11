package com.covid19.plasma.service;

import com.covid19.plasma.util.PlasmaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Service
public class OtpService {

    @Autowired
    OkHttpClient client;
    @Value("${spring.otp.endpoint}")
    private String apiEndpoint;
    @Value("${spring.otp.user}")
    private String apiUser;
    @Value("${spring.otp.password}")
    private String apiPassword;
    @Value("${spring.otp.route}")
    private String apiRoute;

    public String sendSms(String number, String text) throws IOException {

        return "Global Variable tto be implemented";
/*        
        try {
            String getResponse = client.doGetRequest(generalOtpUrl(number, text));
            System.out.println(getResponse);
            //{"ErrorCode":"000","ErrorMessage":"Done","JobId":"3046870","MessageData":[{"Number":"919911141400","MessageId":"6B0IyoxBPUC8VF6PZY4Q0Q"}]}
        } catch (Exception e) {
            return "error";
        }
        return "send";*/
    }

    private String generalOtpUrl(String number, String text) throws UnsupportedEncodingException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("user", apiUser);
        requestParams.put("password", apiPassword);
        requestParams.put("senderid", "CACLAS");
        requestParams.put("channel", "TRANS");
        requestParams.put("DCS", "0");
        requestParams.put("flashsms", "0");
        requestParams.put("number", number);
        requestParams.put("text", text);
        requestParams.put("route", apiRoute);

        String encodedURL = requestParams.keySet().stream()
                .map(key -> {
                    try {
                        return key + "=" + PlasmaUtil.encodeValue(requestParams.get(key));
                    } catch (UnsupportedEncodingException e) {
                        return "";
                    }
                })
                .collect(joining("&", apiEndpoint, ""));
        return encodedURL;
    }
}
