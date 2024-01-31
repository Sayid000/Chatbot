package com.main.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "LineMessageLog")
@Getter
@Setter
public class LineMessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eventType", nullable = false)
    private String eventType;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "messageId")
    private String messageId;

    @Column(name = "messageText")
    private String messageText;

    @Column(name = "quoteToken")
    private String quoteToken;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    @Column(name = "webhookEventId", nullable = false)
    private String webhookEventId;

    @Column(name = "isRedelivery", nullable = false)
    private Boolean isRedelivery;

    @Column(name = "replyToken")
    private String replyToken;

    @Column(name = "mode")
    private String mode;
}