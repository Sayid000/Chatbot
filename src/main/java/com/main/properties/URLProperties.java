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

    public static String LINE_PUSH;
    public static String LINE_REPLY;
    public static String LINE_PROFILE;
    public static String MESSAGE_PUSH;

    public void setLinePush(String linePush) {
        URLProperties.LINE_PUSH = linePush;
    }

    public void setLineReply(String lineReply) {
        URLProperties.LINE_REPLY = lineReply;
    }

    public void setLineProfile(String lineProfile) {
        URLProperties.LINE_PROFILE = lineProfile;
    }

    public void setMessagePush(String messagePush) {
        URLProperties.MESSAGE_PUSH = messagePush;
    }
}