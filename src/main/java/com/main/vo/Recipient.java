package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipient {

    private String id;

    public Recipient(String id) {
        this.id = id;
    }
}
