package com.trendyflow.authserver.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

public class UserController {

    @Resource(name="UserInformationService")
    private UserInformationService userInformationService;


    // 회원가입
    @PostMapping(value = "/join")
    public ResponseEntity<UserInformation> join(@RequestBody JoinUser joinUser, HttpServletResponse response) {
        // TODO : null 값 체크 외 Exception 처리
        UserInformation userInformation = userInformationService.makeUser(joinUser);
        return ResponseEntity.status(HttpStatus.OK).body(userInformation);
    }

    // 로그인
    @PostMapping(value = "/sign_in")
    public ResponseEntity<UserInformation> emailLogin(@RequestBody JoinUser joinUser, HttpServletResponse response) {
        // TODO : null 값 체크 외 Exception 처리
        UserInformation userInformation = userInformationService.loadUserByUsername(joinUser.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userInformation);
    }
}