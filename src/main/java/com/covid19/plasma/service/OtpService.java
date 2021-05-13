package com.covid19.plasma.service;

import com.covid19.plasma.dao.MessageRepoisitory;
import com.covid19.plasma.dao.entities.MessageLog;
import com.covid19.plasma.exception.InvalidPhoneNumberException;
import com.covid19.plasma.exception.TooManyRequestException;
import com.covid19.plasma.exception.UnknownException;
import com.covid19.plasma.model.MessageResponse;
import com.covid19.plasma.util.PlasmaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;
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

    @Value("${spring.otp.threshold.one-minute:3}")
    private int thresholdOneMinute;

    @Value("${spring.otp.threshold.one-hour:10}")
    private int threshHoldOneHour;

    @Value("${spring.otp.enable:false}")
    private Boolean isOtpEnabled;

    @Autowired
    private MessageRepoisitory messageRepoisitory;

    public void sendSms(String number, String text) throws IOException {
        if (!isOtpEnabled) {
            return;
        }
        
        if (!PlasmaUtil.issTenDigitsNumber(number)) {
            throw new InvalidPhoneNumberException("invalid phonenumber");
        }
        checkThresholdExceeded(number);
        MessageLog saveMessageLog = saveAndGetMessageLog(number);
        
        //String apiResponse = "{\"ErrorCode\":\"000\"}";
        String apiResponse = null;
        try {
            apiResponse = client.doGetRequest(generalOtpUrl(number, text));
            
            if (isNull(apiResponse) || StringUtils.isEmpty(apiResponse)) {
                throw new UnknownException("Technical issue. Please try again later");
            }
            
        } catch (Exception e) {
            saveMessageLog.setErrorMessage(e.getLocalizedMessage());
            saveMessageLog.setErrorCode(e.getClass().getCanonicalName());
            messageRepoisitory.save(saveMessageLog);
            throw new UnknownException("We are facing technical glitch. Please try again later");
        }

        MessageResponse messageResponse = getMessageResponse(apiResponse);
        
        saveMessageLog.setErrorCode(messageResponse.getErrorCode());
        saveMessageLog.setErrorMessage(messageResponse.getErrorMessage());
        
        messageRepoisitory.save(saveMessageLog);
        //{"ErrorCode":"000","ErrorMessage":"Done","JobId":"3046870","MessageData":[{"Number":"919911141400","MessageId":"6B0IyoxBPUC8VF6PZY4Q0Q"}]}
    }

    public MessageResponse getMessageResponse(String smsResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageResponse messageResponse = objectMapper.readValue(smsResponse, MessageResponse.class);
        return messageResponse;
    }

    private MessageLog saveAndGetMessageLog(String phoneNumber) {
        MessageLog messageLog = new MessageLog();
        messageLog.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        messageLog.setPhoneNumber(phoneNumber);
        return messageRepoisitory.save(messageLog);
    }

    private void checkThresholdExceeded(String phoneNumber) {
        Timestamp lastOneMinute = new Timestamp(System.currentTimeMillis() - 60000);
        Timestamp lastOneHour = new Timestamp(System.currentTimeMillis() - 3600000);

        long lastOneMinuteCount = messageRepoisitory.countByCreatedAtGreaterThanEqualAndPhoneNumber(lastOneMinute, phoneNumber);
        long lastOneHourCount = messageRepoisitory.countByCreatedAtGreaterThanEqualAndPhoneNumber(lastOneHour, phoneNumber);

        if (lastOneMinuteCount >= thresholdOneMinute || lastOneHourCount >= threshHoldOneHour) {
            throw new TooManyRequestException("limit exceeded");
        }
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
