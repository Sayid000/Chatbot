package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Actions {
    private String type;
    private String label;
    private String text;
    private String data;
    private String uri;
    private String title;
}