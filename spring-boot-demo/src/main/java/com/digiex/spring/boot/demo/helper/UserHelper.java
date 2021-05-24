package com.digiex.spring.boot.demo.helper;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.util.AppUtil;
import com.digiex.spring.boot.demo.common.util.UniqueID;
import com.digiex.spring.boot.demo.controller.model.request.CreateUserRequest;
import com.digiex.spring.boot.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author DiGiEx
 */
@Component
public class UserHelper {

    final PasswordEncoder passwordEncoder;

    public UserHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create new user record
     * @param createUserRequest
     * @return
     */
    public User createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setId(UniqueID.getUUID());
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setLang((!"".equals(createUserRequest.getLang())) ? createUserRequest.getLang() : "en");
        String passwordSalt = AppUtil.generateSalt();
        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordEncoder.encode(createUserRequest.getPasswordHash().concat(passwordSalt)));
        user.setStatus(AppStatus.ACTIVE);
        return user;
    }
}
