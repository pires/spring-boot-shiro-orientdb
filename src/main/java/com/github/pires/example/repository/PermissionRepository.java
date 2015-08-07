package com.github.pires.example.repository;

import com.github.pires.example.model.Permission;
import org.springframework.data.orient.object.repository.OrientObjectRepository;

/**
 * DAO for {@link Permission}.
 */
public interface PermissionRepository extends OrientObjectRepository<Permission> {

}
