package com.main.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody {

    private String destination;
    private List<Event> events;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Event {

        private String type;
        private Message message;
        private String webhookEventId;
        private DeliveryContext deliveryContext;
        private long timestamp;
        private Source source;
        private String replyToken;
        private String mode;

        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Message {

            private String type;
            private String id;
            private String quoteToken;
            private String text;
        }

        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class DeliveryContext {

            private boolean isRedelivery;
        }

        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Source {
            
            private String type;
            private String userId;
        }
    }
}
