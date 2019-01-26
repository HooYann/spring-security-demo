package cn.beautybase.authorization.core.security.authentication.smscode;

import cn.beautybase.authorization.biz.common.service.SmsCodeService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private UserDetailsService userDetailsService;
    private SmsCodeService smsCodeService;
    private boolean hideUserNotFoundExceptions = true;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken)authentication;
        String username = smsCodeAuthenticationToken.getName();
        try{
            if(!StringUtils.hasText(username)) {
                logger.error("SmsCodeAuthenticationProvider.authenticate, phoneNumber=null");
                throw new UsernameNotFoundException("SmsCodeAuthenticationProvider.authenticate, phoneNumber is null");
            }
            UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(username);
            additionalAuthenticationChecks(userDetails, smsCodeAuthenticationToken);
            authentication = new SmsCodeAuthenticationToken(userDetails, smsCodeAuthenticationToken.getCredentials(), userDetails.getAuthorities());
            smsCodeAuthenticationToken.setDetails(userDetails);
            return authentication;
        } catch(UsernameNotFoundException e0) {
            this.logger.debug("User '" + smsCodeAuthenticationToken.getPrincipal() + "' not found");
            if (this.hideUserNotFoundExceptions) {
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            throw e0;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    private void additionalAuthenticationChecks(UserDetails userDetails, SmsCodeAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            if (!this.smsCodeService.check(authentication.getName(), authentication.getCredentials())) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
