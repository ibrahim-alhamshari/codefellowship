package com.example.codefellowship.appSecurity;

import com.example.codefellowship.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.codefellowship.repository.ApplicationUserRepo;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepo.findByUsername(username);
        // Error handling ... the user is equal to null (doesn't exist in the database)
        if(user == null){
            throw new UsernameNotFoundException("The user "+ username + " does not exist");
        }
        return user;
    }
}
