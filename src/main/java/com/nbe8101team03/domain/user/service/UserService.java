package com.nbe8101team03.domain.user.service;

import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.repository.UserRepository;
import com.nbe8101team03.global.exception.errorCode.UserErrorCode;
import com.nbe8101team03.global.exception.exception.UserException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(String email, String address, int zipcode){

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserException(UserErrorCode.ALREADY_USED_USER_EMAIL,
                    "이미 사용 중인 이메일 :"+email);
        }

        User newUser = User.builder()
                .email(email)
                .address(address)
                .zipcode(zipcode)
                .build();

        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserException(UserErrorCode.USER_NOT_FOUND,
                                "유저가 존재하지 않습니다. userId:"+id));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND,
                        "유저가 존재하지 않습니다. email:"+ email));
    }


    @Transactional
    public void modify(User user, String email, String address, int zipcode) {
        user.modify(email,address,zipcode);
    }

    @Transactional
    public void delete(User user){
        userRepository.delete(user);
    }
}
