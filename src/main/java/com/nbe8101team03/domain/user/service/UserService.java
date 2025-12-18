package com.nbe8101team03.domain.user.service;

import com.nbe8101team03.domain.orders.repository.OrderRepository;
import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.repository.UserRepository;
import com.nbe8101team03.global.exception.errorCode.UserErrorCode;
import com.nbe8101team03.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

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
    public User findById(Long id) {
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
    public void deactivateUser(User user) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND,
//                        "UserDeactivate Error",
//                        "user not found" ));
        if (user.isActive()) {
            user.deactivate();
        }
    }

    @Transactional
    public void hardDelete(User user){
        //  주문 중인 유저인지 확인 => 주문 중이라면 삭제되지 않도록.
        if(orderRepository.existsByUser(user)) {
            throw new UserException(UserErrorCode.NOT_DELETE_USER);
        }
        userRepository.delete(user);
    }
}
