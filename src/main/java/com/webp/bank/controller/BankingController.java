package com.webp.bank.controller;

import com.webp.bank.entity.Bank;
import com.webp.bank.repository.BankRepository;
import com.webp.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BankingController {
    private final BankService bankService;
    private final BankRepository bankRepository;

    @Autowired
    public BankingController(BankService bankService, BankRepository bankRepository) {
        this.bankService = bankService;
        this.bankRepository = bankRepository;
    }

    @GetMapping("myhistory")
    public String history(HttpSession session, Model model) {
        String id = (String)session.getAttribute("id");
        List<Bank> list = bankService.myhistory(id);
        String balance = bankService.findBalance(id);
        model.addAttribute("id", id);
        model.addAttribute("balance", balance);
        model.addAttribute("b", list);

        return "banking/myhistory";
    }

    @GetMapping("/deposit")
    public String deposit(HttpSession session, Model mo){
        mo.addAttribute("id", session.getAttribute("id"));
        mo.addAttribute("word", "입");
        mo.addAttribute("color","rgb(255, 255, 128)");
        mo.addAttribute("url", "/dewi?choice=1");
        return "banking/dewi";
    }
    @GetMapping("/withdrawal") public String withdrawal(HttpSession session, Model mo){
        mo.addAttribute("id", session.getAttribute("id"));
        mo.addAttribute("word", "출");
        mo.addAttribute("color","#E0F8F7");
        mo.addAttribute("url", "/dewi?choice=-1");
        return "banking/dewi";
    }

    @PostMapping("/dewi")
    public String depositPost(HttpSession session, int choice, int money, Model mo){
        String id = (String)session.getAttribute("id");
        bankService.dewi(id, money, choice);
        mo.addAttribute("msg", money + "원이 " + (choice==1?"입금":"출금") +" 완료 되었습니다. (거래 내역 화면으로 이동)");
        mo.addAttribute("url", "/myhistory");
        return "popup";
    }

    @GetMapping("/transfer")
    public String transfer(HttpSession session, Model mo){
        mo.addAttribute("id", session.getAttribute("id"));
        return "banking/transfer";
    }

    @PostMapping("/transfer")
    public String transferNew(HttpSession session, int money, String tid, Model mo){
        String id = (String)session.getAttribute("id");
        if(id.equals(tid)) {
            mo.addAttribute("msg", "고객님 본인한테는 이체 불가합니다.");
            mo.addAttribute("url", "back");
        }
        else if( bankService.transfer(id, money, tid) ) {
            mo.addAttribute("msg", tid+"님께 "+money +"원이 이체 완료 되었습니다. (거래 내역 화면으로 이동)");
            mo.addAttribute("url", "/myhistory");
        }
        else {
            mo.addAttribute("msg", "미등록 수신인 아이디입니다. 수신인 아이디를 확인해 주세요.");
            mo.addAttribute("url", "back");
        }
        return "popup";
    }

    @GetMapping("/bank/list")
    public String bankList(HttpSession session, Model mo) {
        List<Bank> list = bankService.bankList();
        int count = bankService.findCount();
        String sum = bankService.findSum();
        mo.addAttribute("count",count); mo.addAttribute("sum",sum);
        mo.addAttribute("list",list);
        mo.addAttribute("id", session.getAttribute("id"));
        return "banking/bankList";
    }
    @ResponseBody
    @GetMapping("/aa")
    public int aa() {
        int a = bankRepository.findByIdConnect("admin");
        return a;
    }
}
