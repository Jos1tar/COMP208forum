package ac.liverpool.forum.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    // 使用线程安全的Set存储黑名单令牌
    private final Set<String> blacklistedTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * 添加令牌到黑名单
     * @param token JWT令牌
     */
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * 检查令牌是否在黑名单中
     * @param token JWT令牌
     * @return 是否在黑名单中
     */
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
