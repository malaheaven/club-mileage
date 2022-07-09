package com.triple.clubmileage.exception;

import com.triple.clubmileage.common.enums.ResponseType;

public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7596643121348778552L;

    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public static DataNotFoundException from(final ResponseType returnCode) {
        return new DataNotFoundException(returnCode.getMessage());
    }
}
