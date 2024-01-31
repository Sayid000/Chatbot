package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile {
    
    private String userId;
    private String displayName;
    private String pictureUrl;
    private String language;
}
