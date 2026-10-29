package com.dafpie.repository;

import com.dafpie.entity.PickupRequest;
import com.dafpie.enums.PickupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickupRepository extends JpaRepository<PickupRequest, Long> {
    List<PickupRequest> findByNgoId(Long ngoId);
    List<PickupRequest> findByVolunteerId(Long volunteerId);
    List<PickupRequest> findByStatus(PickupStatus status);
}
