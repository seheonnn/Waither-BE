package com.waither.security.oauth.handler;

import com.waither.security.jwt.JwtTokenProvider;
import com.waither.security.oauth.cookie.CookieAuthorizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //    @Value("${app.oauth2.authorizedRedirectUri}")
//    private String redirectUri;
    private final JwtTokenProvider tokenProvider;
    private final CookieAuthorizationRepository authorizationRequestRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("OAuth2 SuccessHandler - on() : " +targetUrl);

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue);
//
//        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            throw new IllegalArgumentException("redirect URIs are not matched");
//        }
//        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        // JWT 생성
        String accessToken = tokenProvider.createAccessToken(authentication);
        log.info("OAuth2 SuccessHandler - determineTargetUrl: " + accessToken);

        tokenProvider.createRefreshToken(authentication, response);

//        return UriComponentsBuilder.fromUriString(targetUrl)
        return UriComponentsBuilder.fromUriString("/token")
                .queryParam("accessToken", accessToken)
                .build().toUriString();
    }

//    private boolean isAuthorizedRedirectUri(String uri) {
//        URI clientRedirectUri = URI.create(uri);
//        URI authorizedUri = URI.create(redirectUri);
//
//        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                && authorizedUri.getPort() == clientRedirectUri.getPort()) {
//            return true;
//        }
//        return false;
//    }
}
