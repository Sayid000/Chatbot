package com.main.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("classpath:Image.properties")
@ConfigurationProperties
public class ImageProperties {

    public static String PUSH_IMAGE;
    public static String TEMPLATE_IMAGE;
    public static String PNG;

    public void setPushImage(String pushImage) {
        ImageProperties.PUSH_IMAGE = pushImage;
    }

    public void setTemplateImage(String templateImage) {
        ImageProperties.TEMPLATE_IMAGE = templateImage;
    }

    public void setPNG(String png) {
        ImageProperties.PNG = png;
    }
}