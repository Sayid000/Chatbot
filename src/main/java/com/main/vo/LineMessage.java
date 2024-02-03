package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineMessage {

    private String type;
    private String text;
    private String originalContentUrl;
    private String previewImageUrl;
    private String altText;
    private Template template;

    public LineMessage(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public LineMessage(String type, String originalContentUrl, String previewImageUrl) {
        this.type = type;
        this.originalContentUrl = originalContentUrl;
        this.previewImageUrl = previewImageUrl;
    }

    public LineMessage(String type, String altText, Template template) {
        this.type = type;
        this.altText = altText;
        this.template = template;
    }
}
