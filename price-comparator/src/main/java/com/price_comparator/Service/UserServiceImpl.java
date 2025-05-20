package com.price_comparator.Service;

import com.price_comparator.DTO.UserDTO;
import com.price_comparator.Repository.UserRepository;
import com.price_comparator.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getName()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
