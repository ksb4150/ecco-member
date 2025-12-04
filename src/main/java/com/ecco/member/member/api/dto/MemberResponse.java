package com.ecco.member.member.api.dto;

public class MemberResponse {
    private Long id;
    private String email;
    private String name;

    public MemberResponse() {
    }

    public MemberResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}