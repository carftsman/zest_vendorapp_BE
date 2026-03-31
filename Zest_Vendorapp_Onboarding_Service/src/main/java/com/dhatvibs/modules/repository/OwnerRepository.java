package com.dhatvibs.modules.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.modules.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    Optional<Owner> findByPhone(String phone);
}