package com.foodwaste.controller;

import com.foodwaste.entity.*;
import com.foodwaste.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired private DonationRepository donationRepo;
    @Autowired private UserRepository userRepo;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('DONOR')")
    public ResponseEntity<?> createDonation(@RequestBody Donation donation, Authentication auth) {
        User donor = userRepo.findByEmail(auth.getName()).orElseThrow();
        donation.setDonor(donor);
        donationRepo.save(donation);
        return ResponseEntity.ok("Donation created");
    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority('NGO')")
    public List<Donation> getAvailableDonations() {
        return donationRepo.findByClaimedFalse();
    }

    @PostMapping("/claim/{id}")
    @PreAuthorize("hasAuthority('NGO')")
    public ResponseEntity<?> claimDonation(@PathVariable Long id, Authentication auth) {
        Donation donation = donationRepo.findById(id).orElseThrow();
        if (donation.isClaimed()) return ResponseEntity.badRequest().body("Already claimed");

        User ngo = userRepo.findByEmail(auth.getName()).orElseThrow();
        donation.setClaimed(true);
        donation.setClaimedBy(ngo);
        donationRepo.save(donation);
        return ResponseEntity.ok("Claimed successfully");
    }
}
