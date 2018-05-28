package com.ekart.transport.core.dto.response;

import com.ekart.transport.core.enumInterfaces.Errors;
import com.ekart.transport.core.enumInterfaces.SuccessCodes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by ankush.a on 27/06/17.
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponse<T> {

    private boolean success = true;
    private String successCode;
    private String successDescription;
    private T data;
    private HttpStatus httpStatus = HttpStatus.OK;
    private ErrorResponse errorResponse;

    public ClientResponse(SuccessCodes successCode){
        this.setSuccess(true);
        this.successCode = successCode.getCode();
        this.successDescription = successCode.getDescription();
    }

    public ClientResponse(SuccessCodes successCode, HttpStatus httpStatus){
        this.setSuccess(true);
        this.successCode = successCode.getCode();
        this.successDescription = successCode.getDescription();
        this.httpStatus = httpStatus;
    }

    public ClientResponse(SuccessCodes successCode, T data) {
        this.setSuccess(true);
        this.successCode = successCode.getCode();
        this.successDescription = successCode.getDescription();
        this.data = data;
    }

    public ClientResponse(SuccessCodes successCode, HttpStatus httpStatus, T data) {
        this.setSuccess(true);
        this.successCode = successCode.getCode();
        this.successDescription = successCode.getDescription();
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public ClientResponse(HttpStatus httpStatus){
        this.setSuccess(true);
        this.httpStatus = httpStatus;
    }

    public ClientResponse(Errors errors){
        this.setSuccess(false);
        this.setHttpStatus(errors.getStatus());
        this.setErrorResponse(new ErrorResponse(errors));
    }
}
