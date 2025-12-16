package com.nbe8101team03.domain.user.service;

import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.repository.UserRepository;
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
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
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
