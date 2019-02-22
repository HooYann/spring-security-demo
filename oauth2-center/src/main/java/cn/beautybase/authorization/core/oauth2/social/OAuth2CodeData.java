package cn.beautybase.authorization.core.oauth2.social;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class OAuth2CodeData implements Serializable {

    protected String code;

    protected String getCode() {
        return this.code;
    }

    protected void setCode(String aCode) {
        this.code = aCode;
    }

}
