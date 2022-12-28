package com.awstest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final MemberRepository memberRepository;

    @Autowired
    public MainController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/save")
    public String a() {
        Member member = new Member();
        member.name = "김진수";
        member.age = 23;
        memberRepository.save(member);
        return "김진수 레코드 저장";
    }
}
