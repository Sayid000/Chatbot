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
public class Payload {

    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_TEMPLATE = "template";

    private String url;
    private String is_reusable;
    private String template_type;
    private String text;
    private List<Buttons> buttons;
}
