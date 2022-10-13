package com.example.smallcase.security;


import com.example.smallcase.model.ApplicationUser;
import com.example.smallcase.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailService implements UserDetailsService {
    @Autowired
    private ApplicationUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUserName(userName);
        if(user==null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserDetailsImplementation(user);
    }
}


