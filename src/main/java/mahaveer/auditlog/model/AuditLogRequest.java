package mahaveer.auditlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Request parameters for audit logging.
 * 
 * @author npz960 - Matt Blum
 * @since 1.0
 */
public class AuditLogRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Context
    @JsonIgnore
    HttpServletRequest httpRequest;

    @ApiParam(value = "SSO Id parameter.", required = true)
    //@HeaderParam("SSOID")
    @NotNull
    private String ssoId;

    @ApiParam(value = "IP address of the client", required = false)
    //@HeaderParam("X-FORWARDED-FOR")
    private String clientIpAddress;

    @ApiParam(value = "Client correlation ID", required = false)
    //@HeaderParam("Client-Correlation-ID")
    private String clientCorrelationId;

    @ApiParam(value = "Device ID", required = true)
    //@HeaderParam("Device-ID")
    @NotNull
    private String deviceId;

    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public String getClientCorrelationId() {
        return clientCorrelationId;
    }

    public void setClientCorrelationId(String clientCorrelationId) {
        this.clientCorrelationId = clientCorrelationId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(Object rhs) {
        return EqualsBuilder.reflectionEquals(this, rhs, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

/*
 * Copyright 2016 Capital One Financial Corporation All Rights Reserved.
 * 
 * This software contains valuable trade secrets and proprietary information of Capital One and is protected by law. It
 * may not be copied or distributed in any form or medium, disclosed to third parties, reverse engineered or used in any
 * manner without prior written authorization from Capital One.
 */
