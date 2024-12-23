package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else{
            throw new DataNotFoundException("user not found");
        }
    }

    public SiteUser getUserByEmail(String email) {
        Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
        if(siteUser.isPresent()){
            return siteUser.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }

    public boolean existsUser(String username) {
        return userRepository.existsByUsername(username);
    }

    public void changePassword(SiteUser user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean validatePassword(SiteUser user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
