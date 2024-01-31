package com.main.service;

import com.main.vo.ResponseBody;

public interface LineService {

    void sendLineMessage();

    void saveCallBack(ResponseBody responseBody);
}
