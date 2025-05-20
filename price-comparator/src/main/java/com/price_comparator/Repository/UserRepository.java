package com.price_comparator.Repository;

import com.price_comparator.Domain.Discount;
import com.price_comparator.Domain.Product;
import com.price_comparator.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Additional query methods if needed
}

