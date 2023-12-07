package com.zhugalcf.kameleoon.repository;

import com.zhugalcf.kameleoon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
