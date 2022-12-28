package com.webp.bank.controller;

import com.webp.bank.entity.Member;
import com.webp.bank.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("login")
    public String login(Model model) {
        long memberCount = memberService.memberCount();
        model.addAttribute("memberCount", memberCount);

        return "login/login";
    }

    @GetMapping("member/register")
    public String memberRegister() {
        return "login/memberRegister";
    }

    @GetMapping("member/insert")
    public String memberInsert(String id, String pw, String name, String phone, Model model) {
        Member member = Member.createMember(id, pw, name, phone);
        if( memberService.memberInsert(member) ) {
            model.addAttribute("msg", id+"님, 반갑습니다!! (로그인 화면으로 이동)");
            model.addAttribute("url", "/login");
        } else {
            model.addAttribute("msg", id+"는 이미 사용되고 있는 아이디입니다.");
            model.addAttribute("url", "back");
        } return "popup";
    }

    @GetMapping("login/check")
    public String loginCheck(String id, HttpSession session, Model model) {
        if(memberService.findOne(id).isEmpty()) {
            model.addAttribute("msg", id + "는 미등록 아이디입니다. 확인 후 로그인 부탁드립니다.");
            model.addAttribute("url", "/login");
            return "popup";
        }
        else {
            session.setAttribute("id", id);
            return "redirect:/menu";
        }
    }

    @GetMapping("menu")
    public String menu(Model model, HttpSession session) {
        model.addAttribute("id", session.getAttribute("id"));
        if(session.getAttribute("id") == null) { //id값이 null 이면 login 창으로 이동
            model.addAttribute("msg", "로그인 후 이용해 주세요.");
            model.addAttribute("url", "/login");
            return "popup";
        }
        return "menu";
    }

    @GetMapping("logout")
    public String logout(HttpSession session, Model model) {
        model.addAttribute("id", session.getAttribute("Id"));
        return "login/logout";
    }

    @GetMapping("/myinfo")
    public String myinfoForm(Model model, HttpSession httpSession) {
        String id = (String)httpSession.getAttribute("id");
        Member member = memberService.findOne(id).get();
        model.addAttribute("id", id);
        model.addAttribute("m", member);
        DecimalFormat df = new DecimalFormat("###,###");
        model.addAttribute("won", df.format(member.getBalance()));
        int questionCount = memberService.questionCount(id);
        model.addAttribute("count", questionCount);
        return "member/myinfo";
    }

    @GetMapping("/myinfo/update")
    public String myinfoUpdate(HttpSession httpSession, String pw, String name, String phone, Model model,
                               String h_pw, String h_name, String h_phone) {
        String id = (String) httpSession.getAttribute("id");

        if(pw.equals(h_pw) && name.equals(h_name) && phone.equals(h_phone)) {
            model.addAttribute("msg", "변경된 정보가 없습니다");
            model.addAttribute("url", "back");
        }
        else if(memberService.updateMyinfo(id, pw, name, phone) == 0) {
            model.addAttribute("msg", "정보 변경 실패, 고객센터로 문의하세요.");
        }
        else {
            model.addAttribute("msg", id+"님의 정보가 변경되었습니다");
            model.addAttribute("url", "/myinfo");
        }
        return "popup";
    }

    @GetMapping("/member/list")
    public String memberList(HttpSession session, Model model) {

        if(session.getAttribute("id") == null) { //null 값일 때 체크
            model.addAttribute("msg", "로그인 먼저 해라");
            model.addAttribute("url", "/login");
            return "popup";
        }
        else if(!session.getAttribute("id").equals("admin")) { //admin 아이디가 아닐 때 체크
            model.addAttribute("msg", "관리자만 이용 가능한 페이지 입니다");
            model.addAttribute("url", "/menu");
        }

        List<Member> list = memberService.memberList();
        model.addAttribute("list",list);
        return "member/memberList";
    }

    @GetMapping("/member/viplist")
    public String vipList(Model model) {
        List<Member> listVip = memberService.getVipList();
        model.addAttribute("list", listVip);
        return "member/vipList";
    }
}
