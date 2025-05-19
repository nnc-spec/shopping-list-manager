package com.alephalpha.shopping_list_manager.repository;

import com.alephalpha.shopping_list_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

