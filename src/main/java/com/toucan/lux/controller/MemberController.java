package com.toucan.lux.controller;

import com.toucan.lux.domain.Member;
import com.toucan.lux.dto.SignInReq;
import com.toucan.lux.dto.SignInRes;
import com.toucan.lux.dto.SignUpReq;
import com.toucan.lux.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpReq signUpReq) {
        memberService.signUp(signUpReq);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<SignInRes> signIn(@RequestBody SignInReq signInReq) {
        SignInRes signInRes = memberService.signIn(signInReq.getEmail(), signInReq.getPassword());
        return ResponseEntity.ok(signInRes);
    }
}