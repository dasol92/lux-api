package com.toucan.lux.service;

import com.toucan.lux.domain.Member;
import com.toucan.lux.dto.SignInRes;
import com.toucan.lux.dto.SignUpReq;
import com.toucan.lux.enums.Role;
import com.toucan.lux.jwt.JwtUtil;
import com.toucan.lux.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> addMembers(List<Member> members) {
        return memberRepository.saveAll(members);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }


    public void signUp(SignUpReq signUpReq) {

        // 이메일 중복 검사
        if (memberRepository.existsByEmail(signUpReq.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = Member.builder()
                .email(signUpReq.getEmail())
                .password(passwordEncoder.encode(signUpReq.getPassword()))
                .name(signUpReq.getName())
                .role(Role.USER)  // 기본 역할 설정
                .build();

        memberRepository.save(member);
    }

    public SignInRes signIn(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(member.getEmail(), member.getRole().toString(), 1000L * 60 * 1);
        String refreshToken = jwtUtil.createRefreshToken(member.getEmail(), member.getRole().toString(), 1000L * 60 * 60);
        return SignInRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
