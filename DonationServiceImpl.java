package com.foodwaste.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodwaste.entity.Donation;
import com.foodwaste.entity.User;
import com.foodwaste.repo.DonationRepository;
import com.foodwaste.repo.UserRepository;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired private DonationRepository donationRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public Donation createDonation(Donation donation, String donorEmail) {
        User donor = userRepository.findByEmail(donorEmail)
                                   .orElseThrow(() -> new RuntimeException("Donor not found"));
        donation.setDonor(donor);
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getAvailableDonations() {
        return donationRepository.findByClaimedFalse();
    }

    @Override
    public Donation claimDonation(Long donationId, String ngoEmail) {
        Donation donation = donationRepository.findById(donationId)
                                              .orElseThrow(() -> new RuntimeException("Donation not found"));
        if (donation.isClaimed()) {
            throw new RuntimeException("Donation already claimed");
        }
        User ngo = userRepository.findByEmail(ngoEmail)
                                 .orElseThrow(() -> new RuntimeException("NGO not found"));
        donation.setClaimed(true);
        donation.setClaimedBy(ngo);
        return donationRepository.save(donation);
    }
}