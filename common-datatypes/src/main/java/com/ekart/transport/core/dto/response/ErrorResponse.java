package com.ekart.transport.core.dto.response;

import com.ekart.transport.core.enumInterfaces.Errors;
import com.ekart.transport.core.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by ankush.a on 04/06/17.
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private int errorStatusCode;
    private int errorInternalStatusCode;
    private String errorReasonCode;
    private String errorDescription;
    private Map<String, String> additionalData;


    public ErrorResponse(ServiceException se) {
        this.errorStatusCode = se.getErrorStatusCode();
        this.errorReasonCode = se.getErrorReasonCode();
        this.errorDescription = se.getErrorDescription();
        this.errorInternalStatusCode = se.getErrorInternalStatusCode();
        if (se.getAdditionalData() != null)
            this.additionalData = se.getAdditionalData();
    }

    public ErrorResponse(Errors errors) {
        this.errorStatusCode = errors.getStatus().value();
        this.errorReasonCode = errors.getCode();
        this.errorDescription = errors.getDescription();
        this.errorInternalStatusCode = errors.getInternalErrorCode();
    }
}
