package cn.beautybase.authorization.biz.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public <T> Result<T> succeed() {
       return Result.buildSuccess();
    }

    public <T> Result<T> succeed(T data) {
        return Result.buildSuccess(data);
    }

    public <T> Result<T> succeed(String msg, T data) {
        return Result.buildSuccess(msg, data);
    }

    public <T> Result<T> fail() {
        return Result.buildFailure();
    }

    public <T> Result<T> fail(int code) {
        return Result.buildFailure(code);
    }

    public <T> Result<T> fail(String message) {
        return Result.buildFailure(message);
    }

    public <T> Result<T> fail(int code, String message) {
        return Result.buildFailure(code, message);
    }

}
