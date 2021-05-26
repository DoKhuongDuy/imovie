package com.ptit.service.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ptit.service.security.Claims;
import com.ptit.service.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String BEARER = "Bearer";
    public static final String USER_ATTR = "user";

    private JWTUtils jwtUtil;

    private API[] skipAuthAPIs = new API[] {
            API.with("^/error$"),
            API.with("/user/login"),
            API.with("/user/register"),
            API.with("^/movies.*"),
            API.with("^/test.*"),
    };

    @Autowired
    public AuthInterceptor(JWTUtils jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isSkipAuthAPI(request)) {
            return true;
        }
        String token = getJWTToken(request);
        if (token.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println("{\"message\": \"missing access token\"}");
            return false;
        }
        DecodedJWT decodedJWT = jwtUtil.validateJWT(token);
        if (decodedJWT == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println("{\"message\": \"invalid or expired access token\"}");
            return false;
        }
        request.setAttribute(USER_ATTR, decodedJWT);
        return true;
    }

    /**
     * @param request
     * @return
     */
    public String getJWTToken(HttpServletRequest request) {
        String authorizationVal = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationVal == null ||
                authorizationVal.length() <= BEARER.length() + 1 ||
                !authorizationVal.startsWith(BEARER)
        ) {
            return "";
        }
        return authorizationVal.substring(BEARER.length() + 1);
    }

    /**
     * @param request
     * @return
     */
    public Claims getClaims(HttpServletRequest request) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = (DecodedJWT) request.getAttribute(USER_ATTR);
        } catch (Exception e) {
            return null;
        }
        if (decodedJWT == null) {
            return null;
        }

        return Claims.from(decodedJWT);
    }

    public String getAccountID(HttpServletRequest request) {
        String token = getJWTToken(request);
        if (token.isEmpty()) {
            return null;
        }
        DecodedJWT decodedJWT = jwtUtil.validateJWT(token);
        if (decodedJWT == null) {
            return "";
        }
        Claims claims = Claims.from(decodedJWT);
        return claims.getAccountID();
    }

    private boolean isSkipAuthAPI(HttpServletRequest request) {
        for (API skipAuthAPI : this.skipAuthAPIs) {
            if (skipAuthAPI.isSkipRequest(request)) {
                return true;
            }
        }
        return false;
    }

    private static class API {
        private final Pattern pathPattern;
        private final HttpMethod[] methods;

        private API(Pattern pathPattern, HttpMethod[] methods) {
            this.pathPattern = pathPattern;
            this.methods = methods;
        }

        /**
         * @param pathRegex
         * @param methods
         * @return
         */
        public static API with(String pathRegex, HttpMethod... methods) {
            return new API(Pattern.compile(pathRegex), methods);
        }

        /**
         * @param pathPattern
         * @param methods
         * @return
         */
        public static API with(Pattern pathPattern, HttpMethod... methods) {
            return new API(pathPattern, methods);
        }

        /**
         * @param request
         * @return
         */
        public boolean isSkipRequest(HttpServletRequest request) {
            return isSkipPath(request.getRequestURI()) && isSkipMethod(request.getMethod());
        }

        private boolean isSkipPath(String path) {
            return this.pathPattern.matcher(path).matches();
        }

        private boolean isSkipMethod(String method) {
            if (this.methods == null || this.methods.length == 0) {
                return true;
            }
            for (HttpMethod httpmethod : this.methods) {
                if (httpmethod.matches(method)) {
                    return true;
                }
            }
            return false;
        }
    }
}
