package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceBookMessageRequestBody {

    private Recipient recipient;
    @JsonProperty("messaging_type")
    private String messaging_type;
    private String tag;
    private FaceBookMessage message;
}
