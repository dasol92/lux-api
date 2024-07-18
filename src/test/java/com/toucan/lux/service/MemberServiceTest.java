package com.toucan.lux.service;

import com.toucan.lux.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("멤버 등록 성공시")
    @Test
    @Transactional
    void test() {
        // given
        Member member1 = Member.builder()
                .name("김서영")
                .build();

        Member member2 = Member.builder()
                .name("김다솔")
                .build();

        // when
        memberService.addMember(member1);
        memberService.addMember(member2);

        // then
        List<Member> allMembers = memberService.getAllMembers();
        Assertions.assertThat(allMembers).hasSize(2)
                .containsExactlyInAnyOrder(member1, member2);
    }

}