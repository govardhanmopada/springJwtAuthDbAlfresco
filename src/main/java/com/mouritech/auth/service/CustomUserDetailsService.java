package com.mouritech.auth.service;

import com.mouritech.auth.config.CustomUserDetails;
import com.mouritech.auth.dao.User;
import com.mouritech.auth.dto.UserAuthDto;
import com.mouritech.auth.dto.UserAuthMainDto;
import com.mouritech.auth.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserAuthDto> user = userRepository.findByUsername(username);
        if(user ==null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        UserAuthMainDto userMain = new UserAuthMainDto();
        userMain.setUserName(user.get(0).getUserName());
        userMain.setUserPassword(user.get(0).getUserPassword());
        List<String> roles = new ArrayList<>();
        user.stream().forEach( userAuthDto -> {
            roles.add(userAuthDto.getRoleName());
                });
        userMain.setRole(roles);

        return new CustomUserDetails(userMain);
    }
}
