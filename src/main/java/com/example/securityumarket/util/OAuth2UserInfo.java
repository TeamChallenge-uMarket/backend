package com.example.securityumarket.util;

import java.util.Map;

public class OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getNickname() {
        return (String) attributes.get("nickname");
    }

    public String getPicture() {
        return (String) attributes.get("picture");
    }

    public String getPhone() {
        return (String) attributes.get("phone");
    }
}