package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ConversationHistory {

    public String userId;
    public String userName;
    public String platform;
    public String type;
    public String content;
}
