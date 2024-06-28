package com.toucan.lux.service;

import com.toucan.lux.domain.Member;
import com.toucan.lux.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }




}
