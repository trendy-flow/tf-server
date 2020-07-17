package com.trendyflow.authserver.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service ("UserInformationService")
public class UserInformationService implements UserDetailsService {

    @Resource (name="UserDao")
    private UserDao UserDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = UserDao.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("wrongId");
        }
        return makeLoginUser(user);
    }

    public UserInformation makeLoginUser(User user) {

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