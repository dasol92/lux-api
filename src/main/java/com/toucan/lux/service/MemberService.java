package com.toucan.lux.service;

import com.toucan.lux.domain.Member;
import com.toucan.lux.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
        return memberRepository.findByEmail(email);
    }


}
