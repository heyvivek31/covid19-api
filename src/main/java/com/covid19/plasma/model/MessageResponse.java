package com.covid19.plasma.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ErrorCode",
        "ErrorMessage",
        "JobId",
        "MessageData"
})
public class MessageResponse {

    @JsonProperty("ErrorCode")
    private String errorCode;
    @JsonProperty("ErrorMessage")
    private String errorMessage;
    @JsonProperty("JobId")
    private String jobId;
    @JsonProperty("MessageData")
    private List<MessageDatum> messageData = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ErrorCode")
    public String getErrorCode() {
        return errorCode;
    }

    @JsonProperty("ErrorCode")
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("ErrorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("ErrorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("JobId")
    public String getJobId() {
        return jobId;
    }

    @JsonProperty("JobId")
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @JsonProperty("MessageData")
    public List<MessageDatum> getMessageData() {
        return messageData;
    }

    @JsonProperty("MessageData")
    public void setMessageData(List<MessageDatum> messageData) {
        this.messageData = messageData;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
