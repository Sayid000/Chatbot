package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Template {
    private String type;
    private String thumbnailImageUrl;
    private String imageAspectRatio;
    private String imageSize;
    private String imageBackgroundColor;
    private String title;
    private String text;
    private List<Actions> actions;
}
