package com.webp.bank.controller;

import com.webp.bank.entity.QA;
import com.webp.bank.repository.QARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class QAController {
    private final QARepository qaRepository;

    @Autowired
    public QAController(QARepository qaRepository) {
        this.qaRepository = qaRepository;
    }

    @GetMapping("/qa/question")
    public String question(HttpSession session, Model mo){
        String id = (String)session.getAttribute("id");
        if(id==null){
            id="고객";
        }
        List<QA> list = qaRepository.findAll();
        mo.addAttribute("id",id);
        mo.addAttribute("list",list);
        return "qa/question";
    }
    @PostMapping("/qa/question")
    public String insertQuestion(HttpSession session, String question, Model mo){
        String id = (String)session.getAttribute("id");

        QA q = new QA();
        if(id==null){
            id="고객";
        }
        q.id = id; q.question = question; q.answer = "(답변준비중)";
        qaRepository.save(q);
        mo.addAttribute("msg", id+"님의 질문이 등록되었습니다.");
        mo.addAttribute("url", "/qa/question");
        return "popup";
    }

    @GetMapping("/qa/answer")
    public String answer(HttpSession session, Model mo){
        String id = (String)session.getAttribute("id");
        List<QA> list = qaRepository.findAll();
        mo.addAttribute("id",id);
        mo.addAttribute("list",list);
        return "qa/qaAnswer";
    }
    @PostMapping("/qa/answer")
    public String updateAnswer(int no, String answer, Model mo){
        if(!qaRepository.existsById(no)) {
            mo.addAttribute("msg",
                    no+"번은 존재하지 않는 질문번호입니다. 확인 부탁드립니다!");
            mo.addAttribute("url", "back");
        } else {
            qaRepository.updateAnswer(no, answer);
            /* 리파지토리에 추상메소드를 작성해 보세요 */
            mo.addAttribute("msg", "답변이 등록되었습니다.");
            mo.addAttribute("url", "/qa/answer");
        }
        return "popup";
    }

    @GetMapping("test2")
    public String m() {
        return "test_";

    }
}
