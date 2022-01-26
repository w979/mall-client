package com.wry.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String account;

    private String password;

    private String email;

    private String avatar;

    private Integer score;

    private Date regtime;

    private String activecode;

    private Integer status;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", score=" + score +
                ", regtime=" + regtime +
                ", activecode='" + activecode + '\'' +
                ", status=" + status +
                '}';
    }
}