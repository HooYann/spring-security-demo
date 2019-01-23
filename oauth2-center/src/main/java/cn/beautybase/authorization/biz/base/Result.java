package cn.beautybase.authorization.biz.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILURE = 1;
    public static final int CODE_NO_PERMISSION = 2;

    public static final String MSG_EMPTY = "";

    private boolean success;
    private int code;
    private String msg;
    private T data;

    public Result(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.msg = message;
        this.data = data;
    }


    public static <T> Result<T> buildSuccess() {
        return buildSuccess(MSG_EMPTY, null);
    }
    public static <T> Result<T> buildSuccess(String msg) {
        return buildSuccess(msg, null);
    }
    public static <T> Result<T> buildSuccess(T data) {
        return buildSuccess(MSG_EMPTY, data);
    }
    public static <T> Result<T> buildSuccess(String msg, T data) {
        return build(true, CODE_SUCCESS, msg, data);
    }


    public static <T> Result<T> buildFailure() {
        return buildFailure(CODE_FAILURE, MSG_EMPTY);
    }
    public static <T> Result<T> buildFailure(int code) {
        return buildFailure(code, MSG_EMPTY);
    }
    public static <T> Result<T> buildFailure(Throwable e) {
        return buildFailure(e.getMessage());
    }
    public static <T> Result<T> buildFailure(String msg) {
        return buildFailure(CODE_FAILURE, msg);
    }
    public static <T> Result<T> buildFailure(int code, String msg) {
        return build(false, code, msg, null);
    }


    public static <T> Result<T> build(boolean success, int code, String msg, T data) {
        return new Result<>(success, code, msg, data);
    }
}
