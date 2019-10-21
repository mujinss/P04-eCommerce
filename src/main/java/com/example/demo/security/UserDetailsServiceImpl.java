package com.example.demo.security;

import com.example.demo.model.persistence.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import com.example.demo.model.persistence.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;

/*    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(appUser.getUsername(), appUser.getPassword(), Collections.emptyList());
    }
}
