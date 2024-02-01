package com.main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userId", unique = true, nullable = false)
    private String userId;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "platform")
    private String platform;
}
