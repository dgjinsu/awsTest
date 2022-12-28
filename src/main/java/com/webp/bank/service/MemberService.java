package com.webp.bank.service;

import com.webp.bank.entity.Member;
import com.webp.bank.entity.QA;
import com.webp.bank.repository.MemberRepository;
import com.webp.bank.repository.QARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final QARepository qaRepository;


    @Autowired
    public MemberService(MemberRepository memberRepository, QARepository qaRepository) {
        this.memberRepository = memberRepository;
        this.qaRepository = qaRepository;
    }

    public long memberCount() {
        //memberRepository.countMember() / memberRepository.count() 다 됨
        return memberRepository.count();
    }

    public Boolean memberInsert(Member member) {
        if(memberRepository.existsById(member.getId())) {
            return false;
        }
        else {
            memberRepository.save(member);
            return true;
        }
    }

    public Optional<Member> findOne(String id) {
         return memberRepository.findById(id);
    }

    public int updateMyinfo(String id, String pw, String name, String phone) {
        return memberRepository.updateMyinfo(id, pw, name, phone);
    }

    public List<Member> memberList() {
        List<Member> memberList = memberRepository.findAll();
        return memberList;
    }

    public int questionCount(String id) {
        int count = qaRepository.countQA(id);
        return count;
    }

    public List<Member> getVipList() {
        return memberRepository.findVipList();
    }
}
