package com.dafpie.repository;

import com.dafpie.entity.NgoProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NgoProfileRepository extends JpaRepository<NgoProfile, Long> {
    Optional<NgoProfile> findByUserId(Long userId);
}
