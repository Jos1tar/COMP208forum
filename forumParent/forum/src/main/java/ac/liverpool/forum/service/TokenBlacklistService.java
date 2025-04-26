package ac.liverpool.forum.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    // Use thread safe Set to store blacklist tokens
    private final Set<String> blacklistedTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * Add token to blacklist
     * @param token
     */
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * Check if the token is on the blacklist

     */
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
