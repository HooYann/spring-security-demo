package cn.beautybase.authorization.core.oauth2.config;

import cn.beautybase.authorization.core.oauth2.clientdetails.CustomizedClientDetailsService;
import cn.beautybase.authorization.core.oauth2.handler.CustomizedOAuth2AuthenticationEntryPoint;
import cn.beautybase.authorization.core.oauth2.provider.token.*;
import cn.beautybase.authorization.core.security.userdetails.SocialUserDetailsService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * security oauth2 认证服务器配置
 * @author Yann
 * @since 2019-01-19
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SocialUserDetailsService socialUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KeyPair keyPair;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer config) throws Exception {

        config.authenticationEntryPoint(new CustomizedOAuth2AuthenticationEntryPoint());
        config.passwordEncoder(passwordEncoder);

        config.tokenKeyAccess("isAuthenticated()");
        config.checkTokenAccess("permitAll()");

        //允许表单认证
        config.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer config) throws Exception {
        /*config.inMemory()
                .withClient("client_test")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes(
                        "authorization_code",
                        "implicit",
                        "password",
                        "client_credentials",
                        "refresh_token")
                .scopes("user")
                .redirectUris("http://localhost:8088")
                //.authorities("oauth2")
                .autoApprove(true);*/
        config.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer config) throws Exception {
        //设置自己的令牌点
        //config.pathMapping("/oauth/token", "/oauth2/token");
        //config.pathMapping("/oauth/token", "/signin");

        config.authenticationManager(authenticationManager);
        //refresh_token
        config.userDetailsService(userDetailsService);
        config.setClientDetailsService(clientDetailsService());

        //CustomizedTokenGranter tokenGranter = tokenGranter();
        //tokenGranter.setDelegate(new CompositeTokenGranter(getTokenGranters(config)));
        config.tokenGranter(tokenGranter());

        config.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());
        //下面这个好像没起作用
        //config.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        return new CustomizedClientDetailsService();
    }

    @Bean
    public TokenGranter tokenGranter() {
        DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService());

        AuthorizationCodeServices codeServices = authorizationCodeServices();
        AuthorizationServerTokenServices tokenServices = tokenServices();

        List<TokenGranter> tokenGranters = Arrays.asList(
                //授权码模式
                new AuthorizationCodeTokenGranter(tokenServices, codeServices, clientDetailsService(), requestFactory),
        //刷新token
        new RefreshTokenGranter(tokenServices, clientDetailsService(), requestFactory),
        //隐式模式
        new ImplicitTokenGranter(tokenServices, clientDetailsService(), requestFactory),
        //客户端模式
        new ClientCredentialsTokenGranter(tokenServices, clientDetailsService(), requestFactory),

            //密码模式
            new ResourceOwnerPasswordTokenGranter(this.authenticationManager, tokenServices, clientDetailsService(), requestFactory),
            //自定义 短信模式
            new ResourceOwnerSmsCodeTokenGranter(this.authenticationManager, tokenServices, clientDetailsService(), requestFactory),
            //自定义 微信小程序授权模
            new WechatMiniappTokenGranter(socialUserDetailsService, this.authenticationManager, tokenServices, clientDetailsService(), requestFactory));
        return new CompositeTokenGranter(tokenGranters);
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Bean
    public TokenStore tokenStore() {
        //无状态jwt
        return new JwtTokenStore(accessTokenConverter());

        //缓存
        //return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //jwt
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair);

        CustomizedUserAuthenticationConverter userAuthenticationConverter = new CustomizedUserAuthenticationConverter();
        //userAuthenticationConverter.setUserDetailsService(userDetailsService);

        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(userAuthenticationConverter);
        converter.setAccessTokenConverter(tokenConverter);

        return converter;
    }

    /**
     * 重新登录token生成器
     * @return
     */
    @Bean
    public AutoReSignInTokenGenerator autoReSignInTokenGenerator() {
        return new AutoReSignInTokenGenerator(tokenServices());
    }

}

@Configuration
class KeyConfig {
    @Bean
    KeyPair keyPair() {
        try {
            String privateExponent = "3851612021791312596791631935569878540203393691253311342052463788814433805390794604753109719790052408607029530149004451377846406736413270923596916756321977922303381344613407820854322190592787335193581632323728135479679928871596911841005827348430783250026013354350760878678723915119966019947072651782000702927096735228356171563532131162414366310012554312756036441054404004920678199077822575051043273088621405687950081861819700809912238863867947415641838115425624808671834312114785499017269379478439158796130804789241476050832773822038351367878951389438751088021113551495469440016698505614123035099067172660197922333993";
            String modulus = "18044398961479537755088511127417480155072543594514852056908450877656126120801808993616738273349107491806340290040410660515399239279742407357192875363433659810851147557504389760192273458065587503508596714389889971758652047927503525007076910925306186421971180013159326306810174367375596043267660331677530921991343349336096643043840224352451615452251387611820750171352353189973315443889352557807329336576421211370350554195530374360110583327093711721857129170040527236951522127488980970085401773781530555922385755722534685479501240842392531455355164896023070459024737908929308707435474197069199421373363801477026083786683";
            String exponent = "65537";

            RSAPublicKeySpec publicSpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(exponent));
            RSAPrivateKeySpec privateSpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return new KeyPair(factory.generatePublic(publicSpec), factory.generatePrivate(privateSpec));
        } catch ( Exception e ) {
            throw new IllegalArgumentException(e);
        }
    }
}

@FrameworkEndpoint
class JwkSetEndpoint {

    KeyPair keyPair;

    public JwkSetEndpoint(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @GetMapping("/.well-know/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey(Principal principal) {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}