package com.ekart.transport.core.enums;

import com.ekart.transport.core.enumInterfaces.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CoreErrors implements Errors {

    REQUEST_ID_MISSING("REQUEST_ID_MISSING",1401,HttpStatus.BAD_REQUEST,"X_REQUEST_ID is missing"),
    REQUEST_ID_DUPLICATE_FOR_SAME_METHOD("REQUEST_ID_DUPLICATE_FOR_SAME_METHOD", 1402, HttpStatus.BAD_REQUEST, "Request Id is duplicate for same method"),
    INVALID_WEIGHT_UNIT("INVALID_WEIGHT_UNIT", 1403 , HttpStatus.BAD_REQUEST, "Invalid weight unit"),
    INVALID_VOLUME_UNIT("INVALID_VOLUME_UNIT", 1404 , HttpStatus.BAD_REQUEST, "Invalid volume unit"),
    INVALID_DIMENSION_UNIT("INVALID_DIMENSION_UNIT", 1405, HttpStatus.BAD_REQUEST, "Invalid dimension unit"),
    INVALID_CURRENCY_UNIT("INVALID_CURRENCY_UNIT", 1406 , HttpStatus.BAD_REQUEST, "Invalid currency unit");

    @Getter
    String code;

    @Getter
    int internalErrorCode;

    @Getter
    HttpStatus status;

    @Getter
    String description;

}
