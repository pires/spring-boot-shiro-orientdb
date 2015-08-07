package com.github.pires.example.repository;

import com.github.pires.example.model.Role;
import org.springframework.data.orient.object.repository.OrientObjectRepository;

/**
 * DAO for {@link Role}.
 */
public interface RoleRepository extends OrientObjectRepository<Role> {

}
