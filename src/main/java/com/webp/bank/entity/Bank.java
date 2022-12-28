package com.webp.bank.entity;

import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer no;
    public String id;
    @CreationTimestamp
    public LocalDateTime tdate;
    public Integer de; //입금액
    public Integer wi; //출금액
    public Byte tcode; // 거래코드 0:일반, 1:보냄, 2:받음
    public String tid; // tcode가 1,2일 때 상대방 id(null 가능)


}
