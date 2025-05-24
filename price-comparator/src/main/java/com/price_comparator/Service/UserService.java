package com.price_comparator.Service;

import com.price_comparator.Domain.User;

public interface UserService {
    User getUserById(Integer id);
    User createUser(User user);
}

