package com.example.demo.mode1.connect;

import com.example.demo.mode1.biz.user.entity.UserSocial;
import com.example.demo.mode1.biz.user.service.UserSocialService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.social.connect.*;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class CustomizedConnectionRepository implements ConnectionRepository {

    private final String userId;
    private UserSocialService userSocialService;
    private final ConnectionFactoryLocator connectionFactoryLocator;

    public CustomizedConnectionRepository(
            String userId,
            UserSocialService userSocialService,
            ConnectionFactoryLocator connectionFactoryLocator) {
        this.userId = userId;
        this.userSocialService = userSocialService;
        this.connectionFactoryLocator = connectionFactoryLocator;
    }


    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        System.out.println("findAllConnections");
        return null;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {
        System.out.println("findConnections");
        return null;
    }

    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        System.out.println("findConnections");
        return null;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
        System.out.println("findConnectionsToUsers");
        return null;
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        System.out.println("getConnection");

        UserSocial userSocial = userSocialService.getByProviderIdAndProviderUserId(connectionKey.getProviderId(), connectionKey.getProviderUserId());
        if(userSocial == null) {
            throw new NoSuchConnectionException(connectionKey);
        }

        OAuth2Connection<?> connection = new OAuth2Connection<>(
                userSocial.getProviderId(), userSocial.getProviderUserId(),
                null, null, null, null, null);

        return connection;
    }

    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        System.out.println("getConnection");
        return (Connection<A>) this.getConnection(new ConnectionKey(this.getProviderId(apiType), providerUserId));
    }

    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        System.out.println("getPrimaryConnection");
        return null;
    }

    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        System.out.println("findPrimaryConnection");
        return null;
    }

    @Override
    public void addConnection(Connection<?> connection) {
        System.out.println("addConnection");
        userSocialService.add(Long.valueOf(this.userId), connection.getKey().getProviderId(), connection.getKey().getProviderUserId());
    }

    @Override
    public void updateConnection(Connection<?> connection) {
        System.out.println("updateConnection");
        //
        //Cache cache = cacheManager.getCache(CACHE_CONNECTION_REPO);
        //cache.put(getCacheKey(connection.getKey().getProviderId(), connection.getKey().getProviderUserId()), connection);
    }

    @Override
    public void removeConnections(String providerId) {
        System.out.println("removeConnections");
        throw new RuntimeException("禁止操作");
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        System.out.println("removeConnection");
        userSocialService.removeByProviderIdAndProviderUserId(connectionKey.getProviderId(), connectionKey.getProviderUserId());
        //Cache cache = cacheManager.getCache(CACHE_CONNECTION_REPO);
        //cache.evict(getCacheKey(connectionKey.getProviderId(), connectionKey.getProviderUserId()));
    }

    private <A> String getProviderId(Class<A> apiType) {
        return this.connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }
}
