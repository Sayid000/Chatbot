package com.main.controller;

import com.main.service.LineService;
import com.main.vo.ResponseBody;
import com.main.vo.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class SendController {

    private final LineService lineService;

    @PostMapping
    public void sendMessage(@RequestBody SendMessage sendMessage) throws URISyntaxException {
        sendMessage.getTypeList().forEach(type -> {
            switch (type) {
                case SendMessage.LINE:
                    lineService.sendLineMessage();
                case SendMessage.FACEBOOK:

                case SendMessage.WHATSAPP:
            }
        });
    }

    @PostMapping("/callback")
    public void callback(@RequestBody ResponseBody responseBody) {
    }
}
