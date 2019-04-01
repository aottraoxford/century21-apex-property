package com.century21.apexproperty.service.authorize;

import com.century21.apexproperty.repository.api_signin.SignInRepo;
import com.century21.apexproperty.repository.api_social_signin.SocialSignInRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service(value = "userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SignInRepo signInRepo;
    @Autowired
    private SocialSignInRepo socialSignInRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern=Pattern.compile(EMAIL_REGEX,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(email);
        if(!matcher.matches()) {
            SocialAccount socialAccount=socialSignInRepo.socialAccount(email);
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
