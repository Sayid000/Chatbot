package com.main.service.impl;

import com.main.properties.INFOProperties;
import com.main.properties.ImageProperties;
import com.main.properties.URLProperties;
import com.main.repository.MessageRepository;
import com.main.repository.UserRepository;
import com.main.service.FaceBookMessageService;
import com.main.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FaceBookFaceBookMessageServiceImpl implements FaceBookMessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public void sendFaceBookMessage() {
        FaceBookMessageRequestBody faceBookMessageRequestBodyByText = getFaceBookMessageRequestBody();
        FaceBookMessage faceBookMessageByText = getFaceBookMessageByText();
        faceBookMessageRequestBodyByText.setMessage(faceBookMessageByText);
        send(faceBookMessageRequestBodyByText);

        FaceBookMessageRequestBody faceBookMessageRequestBodyByImage = getFaceBookMessageRequestBody();
        FaceBookMessage faceBookMessageByImage = getFaceBookMessageByImage();
        faceBookMessageRequestBodyByImage.setMessage(faceBookMessageByImage);
        send(faceBookMessageRequestBodyByImage);

        FaceBookMessageRequestBody faceBookMessageRequestBodyByTemplate = getFaceBookMessageRequestBody();
        FaceBookMessage faceBookMessageByTemplate = getFaceBookMessageByTemplate();
        faceBookMessageRequestBodyByTemplate.setMessage(faceBookMessageByTemplate);
        send(faceBookMessageRequestBodyByTemplate);
    }

    public void send(FaceBookMessageRequestBody faceBookMessageRequestBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeadersWithBearerToken();
        HttpEntity<FaceBookMessageRequestBody> request = new HttpEntity<>(faceBookMessageRequestBody, headers);
        restTemplate.exchange(URLProperties.MESSAGE_PUSH, HttpMethod.POST, request, FaceBookMessageRequestBody.class);
    }

    public static HttpHeaders createHeadersWithBearerToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(INFOProperties.MESSAGE_PAGE_ACCESS_TOKEN);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

//    @Override
//    public void saveCallBack(ResponseBody responseBody) {
//        HttpHeaders headers = createHeadersWithBearerToken();
//        if (responseBody == null || responseBody.getEvents().isEmpty()) {
//            return;
//        }
//        HttpEntity<String> request = new HttpEntity<>(responseBody.getEvents().getFirst().getSource().getUserId(), headers);
//        UserProfile responseEntity = new RestTemplate().exchange(URLProperties.PROFILE, HttpMethod.GET, request, UserProfile.class, Map.of("userId",
//                responseBody.getEvents().getFirst().getSource().getUserId())).getBody();
//
//        assert responseEntity != null;
//        boolean isUserIdExists = userRepository.existsByUserId(responseEntity.getUserId());
//        User user;
//        if (!isUserIdExists) {
//            user = new User();
//            user.setUserId(responseEntity.getUserId());
//            user.setUserName(responseEntity.getDisplayName());
//            user.setPlatform("line");
//            userRepository.save(user);
//        } else {
//            user = userRepository.findByUserId(responseEntity.getUserId())
//                    .orElseThrow();
//        }
//
//        List<Message> messageList = new ArrayList<>();
//        responseBody.getEvents().forEach(event -> {
//            if (event.getMessage() == null) {
//                return;
//            }
//            Message message = new Message();
//            message.setUser(user);
//            message.setType(event.getMessage().getType());
//            message.setContent(event.getMessage().getText());
//            Instant instant = Instant.ofEpochMilli(event.getTimestamp());
//            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
//            message.setCreateDateTime(localDateTime);
//            messageList.add(message);
//        });
//        messageRepository.saveAll(messageList);
//    }
//
//    @Override
//    public List<ConversationHistory> getConversationHistory(String userId) {
//        List<Message> messageList;
//        if (StringUtils.isEmpty(userId)) {
//            messageList = messageRepository.findAll();
//        } else {
//            messageList = messageRepository.findByUser_UserId(userId);
//        }
//        return messageList.stream().map(message -> ConversationHistory.builder()
//                .userId(message.getUser().getUserId())
//                .userName(message.getUser().getUserName())
//                .platform(message.getUser().getPlatform())
//                .type(message.getType())
//                .content(message.getContent())
//                .build()).collect(Collectors.toList());
//    }

    private static FaceBookMessageRequestBody getFaceBookMessageRequestBody() {
        FaceBookMessageRequestBody faceBookMessageRequestBody = new FaceBookMessageRequestBody();
        faceBookMessageRequestBody.setRecipient(new Recipient(INFOProperties.MESSAGE_PSID));
        faceBookMessageRequestBody.setMessaging_type("MESSAGE_TAG");
        faceBookMessageRequestBody.setTag("CONFIRMED_EVENT_UPDATE");
        return faceBookMessageRequestBody;
    }

    private static FaceBookMessage getFaceBookMessageByText() {
        FaceBookMessage faceBookMessage = new FaceBookMessage();
        faceBookMessage.setText("測試文字訊息");
        return faceBookMessage;
    }

    private static FaceBookMessage getFaceBookMessageByImage() {
        FaceBookMessage faceBookMessage = new FaceBookMessage();
        faceBookMessage.setAttachment(Attachment.builder()
                .type("image")
                .payload(Payload.builder()
                        .url(ImageProperties.PNG)
                        .is_reusable("true")
                        .build())
                .build());
        return faceBookMessage;
    }

    private static FaceBookMessage getFaceBookMessageByTemplate() {
        FaceBookMessage faceBookMessage = new FaceBookMessage();
        faceBookMessage.setAttachment(Attachment.builder()
                .type("template")
                .payload(Payload.builder()
                        .template_type("button")
                        .text("標題")
                        .buttons(new ArrayList<>() {{
                            add(Buttons.builder().type("postback").title("請點我").payload("DEVELOPER_DEFINED_PAYLOAD").build());
                            add(Buttons.builder().type("web_url").title("開啟連結").url(ImageProperties.PNG).build());
                        }})
                        .build())
                .build());
        return faceBookMessage;
    }
}