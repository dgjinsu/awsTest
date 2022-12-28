package com.webp.bank.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Member {
    @Id
    private String id;
    String pw;
    private String name;
    private String phone;
    private Integer balance;
    @CreationTimestamp
    public LocalDateTime rdate;

    public static Member createMember(String id, String pw, String name, String phone) {
        Member member = new Member();
        member.id = id;
        member.pw=pw;
        member.name=name;
        member.phone=phone;
        member.balance=1000;
        return member;
    }

}