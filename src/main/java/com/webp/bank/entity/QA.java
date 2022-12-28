package com.webp.bank.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class QA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer no;

    public String id;

    @CreationTimestamp
    public LocalDateTime qdate;

    public String question;

    public LocalDateTime adate;

    public String answer;
}
