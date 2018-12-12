package com.example.demo.oauth2;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * The authentication method used when authenticating the client with the authorization server.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-2.3">Section 2.3 Client Authentication</a>
 */
public final class ClientAuthenticationMethod implements Serializable {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    public static final ClientAuthenticationMethod BASIC = new ClientAuthenticationMethod("basic");
    public static final ClientAuthenticationMethod POST = new ClientAuthenticationMethod("post");
    private final String value;

    /**
     * Constructs a {@code ClientAuthenticationMethod} using the provided value.
     *
     * @param value the value of the client authentication method
     */
    public ClientAuthenticationMethod(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }

    /**
     * Returns the value of the client authentication method.
     *
     * @return the value of the client authentication method
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ClientAuthenticationMethod that = (ClientAuthenticationMethod) obj;
        return this.getValue().equalsIgnoreCase(that.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
