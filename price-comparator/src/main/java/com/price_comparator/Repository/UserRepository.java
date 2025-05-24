package com.price_comparator.Repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.price_comparator.Domain.Discount;
import com.price_comparator.Domain.Product;
import com.price_comparator.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

}

