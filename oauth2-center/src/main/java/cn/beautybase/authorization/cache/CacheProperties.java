package cn.beautybase.authorization.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author Yann
 * @date 2019-01-23
 */
@ConfigurationProperties(prefix = "cache")
@Data
public class CacheProperties {
    private Long defaultExpiration;
    private Map<String, Long> expireMap;
}