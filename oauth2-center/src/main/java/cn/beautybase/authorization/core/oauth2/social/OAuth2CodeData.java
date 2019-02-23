package cn.beautybase.authorization.core.oauth2.social;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class OAuth2CodeData implements Serializable {

    protected String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String aCode) {
        this.code = aCode;
    }

}
