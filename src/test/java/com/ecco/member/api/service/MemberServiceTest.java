package com.ecco.member.api.service;

import com.ecco.member.api.repository.MemberRepository;
import com.ecco.member.api.dto.JoinMemberRequest;
import com.ecco.member.api.dto.MemberResponse;
import com.ecco.member.domain.Member;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        memberService = new MemberService(memberRepository);
    }

    @Test
    void join_success() {
        JoinMemberRequest req = new JoinMemberRequest(
                "test@email.com",
                "Test User",
                "Seoul"
        );

        when(memberRepository.findByEmail(req.getEmail()))
                .thenReturn(null);

        Member saved = new Member(req.getEmail(), req.getName(), req.getAddress());
        ReflectionTestUtils.setField(saved, "id", 1L);
        when(memberRepository.save(any(Member.class)))
                .thenReturn(saved);

        MemberResponse res = memberService.join(req);

        assertEquals(1L, res.getId());
        assertEquals(req.getEmail(), res.getEmail());

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void join_fail_duplicateEmail() {
        JoinMemberRequest req = new JoinMemberRequest(
                "dup@test.com",
                "User",
                "Seoul"
        );

        when(memberRepository.findByEmail(req.getEmail()))
                .thenReturn(new Member("dup@test.com", "OldUser", "Busan"));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> memberService.join(req));

        assertEquals("이미 가입된 이메일입니다: " + req.getEmail(), exception.getMessage());

        verify(memberRepository, times(0)).save(any());
    }

    @Test
    void getMember_success() {
        Member member = new Member("email@test.com", "Name", "Seoul");
        ReflectionTestUtils.setField(member, "id", 10L);

        when(memberRepository.findById(10L))
                .thenReturn(java.util.Optional.of(member));

        MemberResponse res = memberService.getMember(10L);

        assertEquals(10L, res.getId());
        assertEquals("email@test.com", res.getEmail());
    }

    @Test
    void getMember_notFound() {
        when(memberRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> memberService.getMember(999L));
    }
}