package cn.beautybase.authorization.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public <T> Result<T> result(T data) {
        return new Result(data);
    }

    xxxx

}
