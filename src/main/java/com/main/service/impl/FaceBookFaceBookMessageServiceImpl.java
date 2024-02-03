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