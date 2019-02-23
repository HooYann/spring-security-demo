package cn.beautybase.authorization.biz.base;

import cn.beautybase.authorization.biz.base.ErrorCode;

/**
 * 平台服务层异常，主要是在业务数据或者状态异常时使用
 *
 * @author LIQIU
 * @date 2017.12.25
 */
public class ServiceException extends RuntimeException {

    private Integer code;

    public ServiceException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        this.code = ErrorCode.SERVICE_EXCEPTION.value();
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
        this.code = ErrorCode.SERVICE_EXCEPTION.value();
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
