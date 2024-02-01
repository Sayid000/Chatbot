package com.main.service.impl;

import com.main.entity.LineMessageLog;
import com.main.properties.INFOProperties;
import com.main.properties.ImageProperties;
import com.main.properties.URLProperties;
import com.main.repository.LineMessageLogRepository;
import com.main.service.LineService;
import com.main.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {

    private final LineMessageLogRepository lineMessageLogRepository;

    @Override
    public void sendLineMessage() {
        LineRequestBody lineRequestBody = getLineRequestBody();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeadersWithBearerToken();
        HttpEntity<LineRequestBody> request = new HttpEntity<>(lineRequestBody, headers);
        restTemplate.exchange(URLProperties.PUSH, HttpMethod.POST, request, LineRequestBody.class);
    }

    public static HttpHeaders createHeadersWithBearerToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(INFOProperties.CHANNEL_ACCESS_TOKEN);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public void saveCallBack(ResponseBody responseBody) {
        List<LineMessageLog> aa = lineMessageLogRepository.findAll();
        HttpHeaders headers = createHeadersWithBearerToken();
        responseBody.getEvents().forEach(event -> {
            HttpEntity<String> request = new HttpEntity<>(event.getSource().getUserId(), headers);
            ResponseEntity<UserProfile> responseEntity = new RestTemplate().exchange(URLProperties.PROFILE, HttpMethod.GET, request, UserProfile.class, Map.of("userId",
                    event.getSource().getUserId()));

        });
    }

    private static LineRequestBody getLineRequestBody() {
        LineRequestBody lineRequestBody = new LineRequestBody();
        lineRequestBody.setTo(INFOProperties.USER_ID);

        List<LineMessage> lineMessages = new ArrayList<>() {{
            add(new LineMessage(LineMessage.TYPE_TEXT, "測試文字訊息"));
            add(new LineMessage(LineMessage.TYPE_IMAGE, ImageProperties.PUSH_IMAGE, ImageProperties.PUSH_IMAGE));
            add(new LineMessage(LineMessage.TYPE_TEMPLATE, "This is a buttons altText",
                    Template.builder()
                            .type("buttons")
                            .thumbnailImageUrl(ImageProperties.TEMPLATE_IMAGE)
                            .imageAspectRatio("rectangle")
                            .imageSize("cover")
                            .imageBackgroundColor("#FFFFFF")
                            .title("請填寫個人資料")
                            .text("請選擇你要輸入的資料項目")
                            .actions(new ArrayList<>() {{
                                add(Actions.builder().type("message").label("請按我").text("你按到我了").build());
                                add(Actions.builder().type("postback").label("點擊觸發").data("action=buy&itemid=123").build());
                                add(Actions.builder().type("uri").label("開啟連結").uri(ImageProperties.TEMPLATE_IMAGE).build());
                            }}).build()));
        }};
        lineRequestBody.setMessages(lineMessages);
        return lineRequestBody;
    }
}