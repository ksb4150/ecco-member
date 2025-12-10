package com.ecco.member.api.service;

import com.ecco.member.api.dto.JoinMemberRequest;
import com.ecco.member.api.dto.MemberResponse;
import com.ecco.member.api.repository.MemberRepository;
import com.ecco.member.domain.Member;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse join(JoinMemberRequest request) {
        if(memberRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다: " + request.getEmail());
        }

        Member newMember = new Member(request.getEmail(), request.getName(), request.getAddress());

        Member saveMember = memberRepository.save(newMember);

        return new MemberResponse(
            saveMember.getId(),
            saveMember.getEmail(),
            saveMember.getName()
        );
    }

    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException("해당 ID의 회원을 찾을 수 없습니다."));
        return new MemberResponse(
            member.getId(),
            member.getEmail(),
            member.getName()
        );
    }
}
