package mahaveer.auditlog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Payload for audit logging requests.
 * 
 * @author npz960 - Matt Blum
 * @since 1.0
 */
@ApiModel(value = "The request payload for audit logging requests.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditLogPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    //Headers need be to case insensitive
    private Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private Map<String, String> attributes = new HashMap<>();

    @NotNull
    @ApiModelProperty("Session ID")
    private String sessionId;

    @ApiModelProperty("Client-side tracking link")
    private String clientSideTrackingLink;

    @NotNull
    @ApiModelProperty("Business Event")
    private String businessEvent;

    @NotNull
    @ApiModelProperty("Business Result")
    private String businessResult;

    @ApiModelProperty("Disposition")
    private String disposition;

    @NotNull
    @ApiModelProperty("Event ID")
    private String eventId;

    @NotNull
    @ApiModelProperty("Channel")
    private String channel;

    @NotNull
    @ApiModelProperty("Mobile ID")
    private String mobileId;

    @NotNull
    @ApiModelProperty("Mobile Device Type")
    private String deviceType;

    @NotNull
    @ApiModelProperty("Mobile App Version")
    private String appVersion;

    @NotNull
    @ApiModelProperty("Mobile App Type")
    private String appType;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClientSideTrackingLink() {
        return clientSideTrackingLink;
    }

    public void setClientSideTrackingLink(String clientSideTrackingLink) {
        this.clientSideTrackingLink = clientSideTrackingLink;
    }

    public String getBusinessEvent() {
        return businessEvent;
    }

    public void setBusinessEvent(String businessEvent) {
        this.businessEvent = businessEvent;
    }

    public String getBusinessResult() {
        return businessResult;
    }

    public void setBusinessResult(String businessResult) {
        this.businessResult = businessResult;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String setHeader(String key, String value){
        return headers.put(key,value);
    }

    public String getHeader(String key){
        return headers.get(key);
    }

    public String setAttribute(String key, String value){
        return attributes.put(key, value);
    }

    public String getAttribute(String key){
        return attributes.get(key);
    }


}

/*
 * Copyright 2016 Capital One Financial Corporation All Rights Reserved.
 * 
 * This software contains valuable trade secrets and proprietary information of Capital One and is protected by law. It
 * may not be copied or distributed in any form or medium, disclosed to third parties, reverse engineered or used in any
 * manner without prior written authorization from Capital One.
 */
