package com.price_comparator.Service;

import com.price_comparator.Domain.User;
import com.price_comparator.Repository.UserRepository;
import com.price_comparator.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .map(user -> new User(user.getId(), user.getName()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user) {
      return  userRepository.save(user);
    }
}