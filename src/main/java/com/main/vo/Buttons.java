package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Buttons {

    private String type;
    private String title;
    private String payload;
    private String url;
}
