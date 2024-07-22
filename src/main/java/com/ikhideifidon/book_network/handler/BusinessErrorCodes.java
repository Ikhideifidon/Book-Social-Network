package com.ikhideifidon.book_network.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCodes {

    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No Code"),
    INCORRECT_CURRENT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Password is Incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "Current Password is Incorrect"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "User Account Locked"),
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "User Account is Disabled"),
    BAD_CREDENTIALS(304, HttpStatus.FORBIDDEN, "Login Username and / or Password is/are Incorrect");

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;


    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

}
