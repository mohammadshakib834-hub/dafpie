package com.dafpie.repository;

import com.dafpie.entity.Donation;
import com.dafpie.enums.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);
    List<Donation> findByStatus(DonationStatus status);
    List<Donation> findByAcceptedByNgoId(Long ngoId);
    List<Donation> findByCityIgnoreCaseAndStatus(String city, DonationStatus status);
}
