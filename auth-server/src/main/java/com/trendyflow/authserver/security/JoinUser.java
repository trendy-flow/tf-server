package com.trendyflow.authserver.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
* 회원가입 , 로그인 DTO
* */

@Getter
@Setter
@Data
public class JoinUser {

    private String username;
    private String password;

}
