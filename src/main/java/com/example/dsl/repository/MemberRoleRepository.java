package com.example.dsl.repository;

import com.example.dsl.entity.Member;
import com.example.dsl.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long>{
}
