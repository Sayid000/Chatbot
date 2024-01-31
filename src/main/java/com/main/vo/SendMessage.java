package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendMessage {

    public static final String LINE = "line";
    public static final String FACEBOOK = "facebook";
    public static final String WHATSAPP = "whatsApp";

    private List<String> typeList;
}
