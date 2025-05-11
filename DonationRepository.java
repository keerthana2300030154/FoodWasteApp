package com.foodwaste.repo;

import com.foodwaste.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByClaimedFalse();
}
