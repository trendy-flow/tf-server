package com.trendyflow.authserver.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.trendyflow.authserver.exception.AlreadyExistsException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service ("UserInformationService")
public class UserInformationService implements UserDetailsService {

    @Resource (name="UserDao")
    private UserDao UserDao;

    @Transactional
    public UserInformation makeUser(JoinUser joinUser) {
        // DB에 없는 닉네임일 경우 생성
        if (UserDao.findByUsername(joinUser.getUsername()) == null){

            User newUser = new User();
            PasswordEncoder passwordEncoder;
            passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            newUser.setUsername(joinUser.getUsername());
            newUser.setPassword(passwordEncoder.encode(joinUser.getPassword()));
            newUser.setUserType(0);
            newUser.setDate(new Date());
            UserDao.save(newUser);

            return makeLoginUser(newUser);
        }
        else{
            String message = "해당 닉네임(" + user.getUsername() + ")을 가진 유저가 이미 존재합니다";
            throw new AlreadyExistsException(message);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 이름으로 찾은 ... 회원 정보를 user 값
        User user = UserDao.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("wrongId");
        }
        return makeLoginUser(user);
    }

    public UserInformation makeLoginUser(User user) {
        // 회원 정보를 담아 엔티티
        UserInformation loginUser  = new UserInformation();

        List<GrantedAuthority> Authoritylist = new ArrayList<>();
        switch(user.getUserType()) {
            case 0 :
                // admin
                Authoritylist.add(new SimpleGrantedAuthority("ADMIN"));
            case 1 :
                // user
                Authoritylist.add(new SimpleGrantedAuthority("USER"));
                break;
        }

        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setAuthorities(Authoritylist);


        return loginUser;
    }
}