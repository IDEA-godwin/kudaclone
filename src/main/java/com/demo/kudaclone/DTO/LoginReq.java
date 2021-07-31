package com.demo.kudaclone.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginReq {

    @NotNull(message = "field is required")
    private String login;

    @NotNull(message = "field is required")
    private String password;
}
