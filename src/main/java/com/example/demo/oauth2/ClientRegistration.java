package com.example.demo.oauth2;

import java.util.Map;
import java.util.Set;

public class ClientRegistration {
    private String registrationId;
    private String clientId;
    private String clientSecret;
    private ClientAuthenticationMethod clientAuthenticationMethod;
    private AuthorizationGrantType authorizationGrantType;
    private String redirectUriTemplate;
    private Set<String> scopes;
    private ProviderDetails providerDetails;
    private String clientName;

    public class ProviderDetails {
        private String authorizationUri;
        private String tokenUri;
        private UserInfoEndpoint userInfoEndpoint;
        private String jwtSetUri;
        private Map<String, Object> configurationMetadata;

        public class UserInfoEndpoint {
            private String uri;
            private AuthenticationMethod authenticationMethod;
            private String userNameAttributeName;
        }
    }
}
