package com.donalola.api.authentication.util;

import com.donalola.api.config.jwt.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenExtractorUtil {

    private final List<TokenExtractor> tokenExtractorList;
    private final List<String> presentOnList;

    public TokenExtractorUtil(List<TokenExtractor> tokenExtractorList, JwtConfig jwtConfig) {
        this.presentOnList = jwtConfig.getMustBePresentIn();
        this.tokenExtractorList = tokenExtractorList
                .parallelStream()
                .filter(tokenExtractor -> tokenExtractor.extractApplies(this.presentOnList)).collect(Collectors.toList());
        ;
    }

    public String extract(final HttpServletRequest request) {
        String token = StringUtils.EMPTY;
        for (TokenExtractor tokenExtractor : this.tokenExtractorList) {
            token = tokenExtractor.extract(request);
            if (StringUtils.isNotEmpty(token)) {
                if (log.isDebugEnabled()) {
                    log.debug("Token found: " + token);
                }
                break;
            }
        }

        if (StringUtils.isEmpty(token)) {
            if (log.isDebugEnabled()) {
                log.debug("Authorization header no found or blank!");
            }
            throw new AuthenticationServiceException("Authorization header and cookie are blank!");
        }
        return token;
    }

}
