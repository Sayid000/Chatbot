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

    public static String LINE_CHANNEL_ACCESS_TOKEN;
    public static String LINE_USER_ID;
    public static String MESSAGE_PAGE_ACCESS_TOKEN;
    public static String MESSAGE_PSID;

    public void setLineChannelAccessToken(String lineChannelAccessToken) {
        INFOProperties.LINE_CHANNEL_ACCESS_TOKEN = lineChannelAccessToken;
    }

    public void setLineUserID(String lineUserID) {
        INFOProperties.LINE_USER_ID = lineUserID;
    }

    public void setMessagePageAccessToken(String messagePageAccessToken) {
        INFOProperties.MESSAGE_PAGE_ACCESS_TOKEN = messagePageAccessToken;
    }

    public void setMessagePSID(String MessagePSID) {
        INFOProperties.MESSAGE_PSID = MessagePSID;
    }
}