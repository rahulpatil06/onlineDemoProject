package com.altimetrik.Inventory.errorHandling;

import org.springframework.http.HttpStatusCode;

public class ErroCodes {
    public final int BAD_REQUEST=400;
    public final int INTERNAL_SERVER_ERROR=500;
    public final int NOT_FOUND = 404;
    public final int SERVICE_UNAVAILABLE=503;

    public static int getCode(HttpStatusCode statusCode) {
        return statusCode.value();
    }
}
