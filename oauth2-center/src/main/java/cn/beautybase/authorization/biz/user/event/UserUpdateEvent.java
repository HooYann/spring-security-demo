package cn.beautybase.authorization.biz.user.event;

import cn.beautybase.authorization.biz.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class UserUpdateEvent extends ApplicationEvent {

    @Getter
    private User user;

    public UserUpdateEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
