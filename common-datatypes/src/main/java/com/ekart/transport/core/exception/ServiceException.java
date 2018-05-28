package com.ekart.transport.core.exception;

import com.ekart.transport.core.dto.response.ErrorResponse;
import com.ekart.transport.core.enumInterfaces.Errors;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ServiceException extends Exception {

    private int errorStatusCode;
    private int errorInternalStatusCode;
    private String errorReasonCode;
    private String errorDescription;
    private HttpStatus status;
    private Map<String, String> additionalData;

    public ServiceException(int errorStatusCode, int errorInternalStatusCode, String errorReasonCode,
                                String errorDescription, HttpStatus status) {
        this.errorStatusCode = errorStatusCode;
        this.errorInternalStatusCode = errorInternalStatusCode;
        this.errorReasonCode = errorReasonCode;
        this.errorDescription = errorDescription;
        this.status = status;
    }

    public ServiceException(Errors errors, Exception e) {
        super(errors.getDescription());
        this.errorStatusCode = errors.getStatus().value();
        this.errorDescription = errors.getDescription();
        this.errorReasonCode = errors.getCode();
        this.status = errors.getStatus();
        this.errorInternalStatusCode = errors.getInternalErrorCode();
        this.addSuppressed(e);
    }

    public ServiceException(Errors errors) {
        super(errors.getDescription());
        this.errorStatusCode = errors.getStatus().value();
        this.errorDescription = errors.getDescription();
        this.errorReasonCode = errors.getCode();
        this.status = errors.getStatus();
        this.errorInternalStatusCode = errors.getInternalErrorCode();
    }

    public ServiceException(Errors errors, Map<String, String> additionalData) {
        super(errors.getDescription());
        this.errorStatusCode = errors.getStatus().value();
        this.errorDescription = errors.getDescription();
        this.errorReasonCode = errors.getCode();
        this.status = errors.getStatus();
        this.errorInternalStatusCode = errors.getInternalErrorCode();
        if (additionalData != null)
            this.additionalData = additionalData;
    }

    public ServiceException(Errors errors, Map<String, String> additionalData, Exception e) {
        super(errors.getDescription());
        this.errorStatusCode = errors.getStatus().value();
        this.errorDescription = errors.getDescription();
        this.errorReasonCode = errors.getCode();
        this.status = errors.getStatus();
        this.errorInternalStatusCode = errors.getInternalErrorCode();
        if (additionalData != null)
            this.additionalData = additionalData;
        this.addSuppressed(e);
    }

    public ServiceException(ErrorResponse errors) {
        this.errorStatusCode = errors.getErrorStatusCode();
        this.errorDescription = errors.getErrorDescription();
        this.errorReasonCode = errors.getErrorReasonCode();
        this.status = HttpStatus.valueOf(errors.getErrorStatusCode() == 0 ? 500 : errors.getErrorStatusCode());
        this.errorInternalStatusCode = errors.getErrorInternalStatusCode();
        if (additionalData != null)
            this.additionalData = errors.getAdditionalData();
    }
}