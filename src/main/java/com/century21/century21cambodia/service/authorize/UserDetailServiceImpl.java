package com.century21.century21cambodia.service.authorize;

import com.century21.century21cambodia.repository.signin.SignInRepo;
import com.century21.century21cambodia.repository.social_signin.SocialSignInRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SignInRepo signInRepo;
    @Autowired
    private SocialSignInRepo socialSignInRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(email.contains(",")) {
            String s[] = email.split(",");
            SocialAccount socialAccount=socialSignInRepo.socialAccount(s[1],s[0]);
            List<Authority> authorities=socialAccount.getAuthorities();
            User user=new User(socialAccount.getEmail(),socialAccount.getSocialId(),authorities);
            return user;
        }else {
            UserAccount userAccount = signInRepo.signIn(email);
            List<Authority> authorities = userAccount.getAuthorities();
            User user = new User(userAccount.getEmail(), userAccount.getPassword(), authorities);
            return user;
        }
    }
}
