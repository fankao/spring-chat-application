package com.chat.app.repository;

import com.chat.app.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

}
