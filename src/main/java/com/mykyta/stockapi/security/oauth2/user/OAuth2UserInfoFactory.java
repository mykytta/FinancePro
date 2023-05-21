package com.mykyta.stockapi.security.oauth2.user;


import com.mykyta.stockapi.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch(registrationId.toLowerCase()) {
            case "google":
                return new GoogleOAuth2UserInfo(attributes);
            case "github":
                return new GithubOAuth2UserInfo(attributes);
            default:
                throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}