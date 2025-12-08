package com.ecco.member.api.controller;

import com.ecco.member.api.dto.MemberResponse;
import com.ecco.member.api.service.MemberService;
import com.ecco.member.api.dto.JoinMemberRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public MemberResponse join(@RequestBody JoinMemberRequest request) {
        return memberService.join(request);
    }

    @GetMapping("/{memberId}")
    public MemberResponse getMember(@PathVariable Long memberId) {
        return memberService.getMember(memberId);
    }

}
