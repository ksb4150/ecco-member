package com.ecco.member.member.api.dto;
public class JoinMemberRequest {
    private String email;
    private String name;
    private String address;

    public JoinMemberRequest() {
    }

    public JoinMemberRequest(String email, String name, String address) {
        this.email = email;
        this.name = name;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}