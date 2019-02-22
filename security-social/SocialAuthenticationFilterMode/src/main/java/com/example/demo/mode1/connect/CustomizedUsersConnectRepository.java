package com.example.demo.mode1.connect;

import com.example.demo.mode1.biz.user.service.UserSocialService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CustomizedUsersConnectRepository implements UsersConnectionRepository {

    @Autowired
    private UserSocialService userSocialService;

    @Setter
    private ConnectionSignUp connectionSignUp;
    @Setter
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();

        //List<String> localUserIds = this.jdbcTemplate.queryForList("select userId from " + this.tablePrefix + "UserConnection where providerId = ? and providerUserId = ?", String.class, new Object[]{key.getProviderId(), key.getProviderUserId()});

        List<String> localUserIds = userSocialService.listUserIdByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());

        if (localUserIds.size() == 0 && this.connectionSignUp != null) {
            String newUserId = this.connectionSignUp.execute(connection);
            if (newUserId != null) {
                this.createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }

        return localUserIds;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return null;
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new CustomizedConnectionRepository(userId, userSocialService, this.connectionFactoryLocator);
    }
}
