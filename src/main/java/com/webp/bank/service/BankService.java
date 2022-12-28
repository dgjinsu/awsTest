package com.webp.bank.service;

import com.webp.bank.entity.Bank;
import com.webp.bank.repository.BankRepository;
import com.webp.bank.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BankService(BankRepository bankRepository, MemberRepository memberRepository) {
        this.bankRepository = bankRepository;
        this.memberRepository = memberRepository;
    }

    public List<Bank> myhistory(String id) {
        return bankRepository.findByIdOrderByTdateDesc(id);
    }

    public String findBalance(String id) {
        Integer balance = memberRepository.findBalance(id);
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(balance);
    }

    @Transactional
    public void dewi(String id, int money, int choice) {
        /* 입출금 */
        Bank b = new Bank();
        b.setId(id);
        b.setDe(choice==1? money: 0);
        b.setWi(choice==-1? money: 0);
        b.setTcode((byte) 0);
        bankRepository.save(b);
        memberRepository.updateBalance(id, choice*money); }

    @Transactional
    public boolean transfer(String id, int money, String tid) { /* 이체*/
        if( !memberRepository.existsById(tid) )
            return false;
        Bank b = new Bank();
        b.id = id; /* 보내는사람 */
        b.de = 0;
        b.wi = money;
        b.tcode = 1; /* 이체보냄 */
        b.tid = tid; //상대방 아이디
        bankRepository.save(b);
        memberRepository.updateBalance(id, -money);

        b = new Bank(); /* new 안 하면 bank.no가 같아서 insert아니고 update됨 */
        b.id = tid; /* 받는 사람 */
        b.de = money;
        b.wi = 0;
        b.tcode = 2; /* 이체받음 */
        b.tid = id; //상대방 아이디
        bankRepository.save(b);
        memberRepository.updateBalance(tid, money);
        return true;
    }

    public List<Bank> bankList() {
        return bankRepository.findAllOrderByIdTdateDesc();
    }
    public int findCount() {
        return memberRepository.findCount();
    }
    public String findSum() {
        int sum = memberRepository.findSum();
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(sum);
    }

}
