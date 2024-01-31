package com.main.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("classpath:url.properties")
@ConfigurationProperties
public class URLProperties {

    public static String PUSH;
    public static String REPLY;
    public static String PROFILE;

    public void setPush(String push) {
        URLProperties.PUSH = push;
    }

    public void setReply(String reply) {
        URLProperties.REPLY = reply;
    }

    public void setProfile(String profile) {
        URLProperties.PROFILE = profile;
    }
}