package com.ecco.member.api.controller;

import com.ecco.member.api.dto.JoinMemberRequest;
import com.ecco.member.api.repository.MemberRepository;
import com.ecco.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void join_success() throws Exception{
        JoinMemberRequest request = new JoinMemberRequest(
        "integration@email.com",
        "Integration User",
        "Pangyo"
        );

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(
            post("/api/v1/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.email").value(request.getEmail()));

        Member saved = memberRepository.findByEmail(request.getEmail());
        assertNotNull(saved);
        assertEquals(request.getName(), saved.getName());
    }

}
