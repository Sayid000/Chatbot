package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Attachment {

    private String type;
    private String text;
    private Payload payload;
}
