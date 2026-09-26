package com.learning.souvenirstoreproject.configuration;

import com.learning.souvenirstoreproject.repository.InvalidatedTokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class BlacklistTokenValidator implements OAuth2TokenValidator<Jwt> {
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        String jti = jwt.getId();

        if (jti != null && invalidatedTokenRepository.existsInvalidatedTokenById(jti)) {
            OAuth2Error oAuth2Error = new OAuth2Error(
                    "token_revoked",
                    "Token has been revoked ",
                    null);
            return OAuth2TokenValidatorResult.failure(oAuth2Error);

        }

        return OAuth2TokenValidatorResult.success();
    }
}
