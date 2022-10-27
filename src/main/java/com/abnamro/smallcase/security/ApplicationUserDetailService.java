package com.abnamro.smallcase.security;


import com.abnamro.smallcase.repository.ApplicationUserRepository;
import com.abnamro.smallcase.model.ApplicationUser;
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


