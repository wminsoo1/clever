package clever.cloning.service;

import clever.cloning.model.User;
import clever.cloning.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public String generateTokenWithTimestamp(String username) {
        return tokenRepository.generateTokenWithTimestamp(username);
    }

    public boolean isTokenNeedRefresh(String token) {
        return tokenRepository.isTokenNeedRefresh(token);
    }

    public String generateAndRefreshToken(User user) {
        // Check if the user already has a token
        if (user.getToken() == null || isTokenNeedRefresh(user.getToken())) {
            // Generate and save a new token for the user
            String token = generateTokenWithTimestamp(user.getUsername());
            log.info("generateAndRefreshToken: {}", token);
            user.setToken(token);
            return token;
        } else {
            // Return the existing token
            return user.getToken();
        }
    }
}
