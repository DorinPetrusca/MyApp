package md.dorin.proiect1.service;

import md.dorin.proiect1.dao.UserDAO;
import md.dorin.proiect1.entity.UserEntity;
import md.dorin.proiect1.entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserService implements UserDetailsService {

    private final UserDAO userDao;

    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Lazy
    @Autowired
    public void setPasswordEncoder(final BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto user) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setUsername(validateUsername(user.getUsername()));
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(validateAndGetUserRole(user));
        userDao.save(userEntity);
    }

    private String validateUsername(final String username) {
        if (userDao.existsByUsername(username)) {
            throw new RuntimeException("User already registered!");
        }
        return username;
    }

    private String validateAndGetUserRole(UserDto user) {
        if (user.getRole() == null) {
            return "ROLE_USER";
        } else if (!(user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_USER"))) {
            throw new RuntimeException("Invalid role provided!");
        }
        return user.getRole();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByUsername(username);
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole()))
        );
    }
}
