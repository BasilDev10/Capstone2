package com.example.capstone.Repository;

import com.example.capstone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id);

    List<User> findUserByGroupSavingAccountId(Integer id);
}