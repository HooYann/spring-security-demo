package com.example.demo.mode1.config;

import com.example.demo.mode1.biz.user.service.UserSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CustomizedUsersConnectRepository implements UsersConnectionRepository {

    @Autowired
    private UserSocialService userSocialService;

    private final ConnectionFactoryLocator connectionFactoryLocator;
    private ConnectionSignUp connectionSignUp;

    public CustomizedUsersConnectRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

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
        return null;
    }
}
