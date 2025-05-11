package com.foodwaste.service;

import java.util.List;

import com.foodwaste.entity.Donation;

public interface DonationService {
    Donation createDonation(Donation donation, String donorEmail);
    List<Donation> getAvailableDonations();
    Donation claimDonation(Long donationId, String ngoEmail);
}