package com.nbe8101team03.domain.user.service;

import com.nbe8101team03.domain.orders.dto.UserOrderItemResponse;
import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
import com.nbe8101team03.domain.product.entity.Product;
import com.nbe8101team03.domain.product.repository.ProductRepository;
import com.nbe8101team03.domain.user.dto.UserTotalRes;
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
    private final ProductRepository productRepository;

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
    public void delete(User user){
        //  주문 중인 유저인지 확인 => 주문 중이라면 삭제되지 않도록.
        if(orderRepository.existsByUser(user)) {
            throw new UserException(UserErrorCode.NOT_DELETE_USER);
        }
        userRepository.delete(user);
    }

    // 유저의 아이디, 이메일, 주소, 우편번호, 총소비금액을 출력.
    @Transactional(readOnly = true)
    public UserTotalRes totalinfo(String email) {
        int totalspent = 0;
        User user = findByEmail(email);

        List<UserOrderItemResponse> orders =
        orderRepository.findAllByUserOrderByOrderDateDesc(user)
                .stream()
                .map(UserOrderItemResponse::from)
                .toList();
        // 해당 유저가 가진 모든 오더 가져와서 -> 리스트로 묶음

        for (UserOrderItemResponse order : orders) {
            // 리스트로 묶은 오더를 순회
            // -> 해당 오더의 totalPrice를 totalspent에 더함
            totalspent += order.totalPrice();
        }


        return new UserTotalRes(user, totalspent);
    }

}
