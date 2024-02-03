package com.main.controller;

import com.main.service.FaceBookMessageService;
import com.main.service.LineService;
import com.main.vo.ConversationHistory;
import com.main.vo.ResponseBody;
import com.main.vo.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SendController {

    private final LineService lineService;
    private final FaceBookMessageService faceBookMessageService;

    @PostMapping
    public void sendMessage(@RequestBody SendMessage sendMessage) {
        sendMessage.getTypeList().forEach(type -> {
            switch (type) {
                case SendMessage.LINE:
                    lineService.sendLineMessage();
                case SendMessage.FACEBOOK:
                    faceBookMessageService.sendFaceBookMessage();
                case SendMessage.WHATSAPP:
            }
        });
    }

    @PostMapping("/callback")
    public void callback(@RequestBody ResponseBody responseBody) {
        lineService.saveCallBack(responseBody);
    }

    @GetMapping("/webhooks")
    public ResponseEntity<String> webhook(@RequestParam("hub.challenge") String challenge) {
        return ResponseEntity.ok(challenge);
    }

    @GetMapping
    public ResponseEntity<List<ConversationHistory>> getConversationHistory(@RequestParam String userId) {
        return ResponseEntity.ok().body(lineService.getConversationHistory(userId));
    }
}
