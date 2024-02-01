package com.main.service;

import com.main.vo.ConversationHistory;
import com.main.vo.ResponseBody;

import java.util.List;

public interface LineService {

    void sendLineMessage();

    void saveCallBack(ResponseBody responseBody);

    List<ConversationHistory> getConversationHistory(String userId);
}
