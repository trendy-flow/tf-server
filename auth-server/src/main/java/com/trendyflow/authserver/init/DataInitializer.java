package com.trendyflow.authserver.init;

import java.util.Date;

import javax.annotation.Resource;

import com.trendyflow.authserver.security.User;
import com.trendyflow.authserver.security.UserDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * H2 초기계정 세팅
 */
@Component
public class DataInitializer implements ApplicationRunner {

    @Resource (name="UserDao")
    private UserDao UserDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User newUser = new User();
        PasswordEncoder passwordEncoder;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        newUser.setUsername("SU");
        newUser.setPassword(passwordEncoder.encode("aaa"));
        newUser.setUserType(0);
        newUser.setDate(new Date());
        UserDao.save(newUser);
    }

}