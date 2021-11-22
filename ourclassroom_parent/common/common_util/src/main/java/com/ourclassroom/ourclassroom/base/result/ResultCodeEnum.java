package com.ourclassroom.ourclassroom.base.result;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
// todo: change the message
public enum ResultCodeEnum {

    SUCCESS(true, 20000,"SUCCESS"),
    UNKNOWN_REASON(false, 20001, "UNKNOWN ERROR"),

    BAD_SQL_GRAMMAR(false, 21001, "SQL STATEMENT ERROR"),
    JSON_PARSE_ERROR(false, 21002, "JASON PARSE ERROR"),
    PARAM_ERROR(false, 21003, "PARAM ERROR"),

    FILE_UPLOAD_ERROR(false, 21004, "FILE UPLOAD ERROR"),
    FILE_DELETE_ERROR(false, 21005, "FILE DELETE ERROR"),
    EXCEL_DATA_IMPORT_ERROR(false, 21006, "EXCEL DATA IMPORT ERROR"),

    VIDEO_UPLOAD_ERROR(false, 22001, "VIDEO UPLOAD ERROR"),
    VIDEO_UPLOAD_SERVER_ERROR(false, 22002, "VIDEO UPLOAD SERVER ERROR"),
    VIDEO_DELETE_ERROR(false, 22003, "VIDEO DELETE ERROR"),

    URL_ENCODE_ERROR(false, 23001, "URL ENCODE ERROR"),
    ILLEGAL_CALLBACK_REQUEST_ERROR(false, 23002, "ILLEGAL CALLBACK REQUEST ERROR"),
    FETCH_ACCESSTOKEN_FAILD(false, 23003, "GET ACCESS TOKEN ERROR"),
    FETCH_USERINFO_ERROR(false, 23004, "GET USER INFO ERROR"),
    LOGIN_ERROR(false, 23005, "LOGIN ERROR"),

    PAY_UNIFIEDORDER_ERROR(false, 25001, "PAYMENT ERROR"),
    PAY_ORDERQUERY_ERROR(false, 25002, "FAILED TO GET ORDER STATUS"),

    FETCH_PLAYAUTH_ERROR(false, 22006, "AUTH REQUIRED"),

    ORDER_EXIST_ERROR(false, 25003, "ORDER ALREADY EXISTS"),

    GATEWAY_ERROR(false, 26000, "GATEWAY ERROR"),

    CODE_ERROR(false, 28000, "CODE ERROR"),

    LOGIN_EMAIL_ERROR(false, 28009, "INVALID EMAIL"),
    LOGIN_USERNAME_ERROR(false, 28001, "INVALID USERNAME"),
    LOGIN_PASSWORD_ERROR(false, 28008, "INVALID PASSWORD"),
    LOGIN_DISABLED_ERROR(false, 28002, "This account is disabled"),
    REGISTER_EMAIL_ERROR(false, 28003, "This email is already registered"),
    LOGIN_AUTH(false, 28004, "LOGIN AUTH REQUIRED"),
    LOGIN_ACL(false, 28005, "LOGIN AUTH ERROR");

    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
