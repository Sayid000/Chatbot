package com.main.service.impl;

import com.main.entity.Message;
import com.main.entity.User;
import com.main.properties.INFOProperties;
import com.main.properties.ImageProperties;
import com.main.properties.URLProperties;
import com.main.repository.MessageRepository;
import com.main.repository.UserRepository;
import com.main.service.LineService;
import com.main.vo.*;
import com.microsoft.sqlserver.jdbc.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public void sendLineMessage() {
        LineRequestBody lineRequestBody = getLineRequestBody();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeadersWithBearerToken();
        HttpEntity<LineRequestBody> request = new HttpEntity<>(lineRequestBody, headers);
        restTemplate.exchange(URLProperties.LINE_PUSH, HttpMethod.POST, request, LineRequestBody.class);
    }

    public static HttpHeaders createHeadersWithBearerToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(INFOProperties.LINE_CHANNEL_ACCESS_TOKEN);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public void saveCallBack(ResponseBody responseBody) {
        HttpHeaders headers = createHeadersWithBearerToken();
        if (responseBody == null || responseBody.getEvents().isEmpty()) {
            return;
        }
        HttpEntity<String> request = new HttpEntity<>(responseBody.getEvents().getFirst().getSource().getUserId(), headers);
        UserProfile responseEntity = new RestTemplate().exchange(URLProperties.LINE_PROFILE, HttpMethod.GET, request, UserProfile.class, Map.of("userId",
                responseBody.getEvents().getFirst().getSource().getUserId())).getBody();

        assert responseEntity != null;
        boolean isUserIdExists = userRepository.existsByUserId(responseEntity.getUserId());
        User user;
        if (!isUserIdExists) {
            user = new User();
            user.setUserId(responseEntity.getUserId());
            user.setUserName(responseEntity.getDisplayName());
            user.setPlatform("line");
            userRepository.save(user);
        } else {
            user = userRepository.findByUserId(responseEntity.getUserId())
                    .orElseThrow();
        }

        List<Message> messageList = new ArrayList<>();
        responseBody.getEvents().forEach(event -> {
            if (event.getMessage() == null) {
                return;
            }
            Message message = new Message();
            message.setUser(user);
            message.setType(event.getMessage().getType());
            message.setContent(event.getMessage().getText());
            Instant instant = Instant.ofEpochMilli(event.getTimestamp());
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            message.setCreateDateTime(localDateTime);
            messageList.add(message);
        });
        messageRepository.saveAll(messageList);
    }

    @Override
    public List<ConversationHistory> getConversationHistory(String userId) {
        List<Message> messageList;
        if (StringUtils.isEmpty(userId)) {
            messageList = messageRepository.findAll();
        } else {
            messageList = messageRepository.findByUser_UserId(userId);
        }
        return messageList.stream().map(message -> ConversationHistory.builder()
                .userId(message.getUser().getUserId())
                .userName(message.getUser().getUserName())
                .platform(message.getUser().getPlatform())
                .type(message.getType())
                .content(message.getContent())
                .build()).collect(Collectors.toList());
    }

    private static LineRequestBody getLineRequestBody() {
        LineRequestBody lineRequestBody = new LineRequestBody();
        lineRequestBody.setTo(INFOProperties.LINE_USER_ID);

        List<LineMessage> lineMessages = new ArrayList<>() {{
            add(new LineMessage("text", "測試文字訊息"));
            add(new LineMessage("image", ImageProperties.PUSH_IMAGE, ImageProperties.PUSH_IMAGE));
            add(new LineMessage("template", "This is a buttons altText",
                    Template.builder()
                            .type("buttons")
                            .thumbnailImageUrl(ImageProperties.TEMPLATE_IMAGE)
                            .imageAspectRatio("rectangle")
                            .imageSize("cover")
                            .imageBackgroundColor("#FFFFFF")
                            .title("大標題")
                            .text("小標題")
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