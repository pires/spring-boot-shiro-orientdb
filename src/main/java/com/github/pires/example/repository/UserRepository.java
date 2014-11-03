package com.github.pires.example.repository;

import com.github.pires.example.model.User;
import org.springframework.data.orient.object.repository.OrientObjectRepository;

/**
 * DAO for {@link User}.
 */
public interface UserRepository extends
    OrientObjectRepository<User> {

  User findByEmail(String email);
  User findByEmailAndActive(String email, boolean active);

}
