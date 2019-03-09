package cn.beautybase.authorization.biz.user.service;

import cn.beautybase.authorization.biz.user.event.UserUpdateEvent;
import org.springframework.context.event.EventListener;

public interface UserListenService {

    @EventListener(classes = UserUpdateEvent.class)
    void listenUserUpdateEvent(UserUpdateEvent event);

}
