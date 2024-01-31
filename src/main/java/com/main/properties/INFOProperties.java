package com.main.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("classpath:info.properties")
@ConfigurationProperties
public class INFOProperties {

    public static String CHANNEL_ACCESS_TOKEN;
    public static String USER_ID;

    public void setChannelAccessToken(String channelAccessToken) {
        INFOProperties.CHANNEL_ACCESS_TOKEN = channelAccessToken;
    }

    public void setUserID(String userID) {
        INFOProperties.USER_ID = userID;
    }
}