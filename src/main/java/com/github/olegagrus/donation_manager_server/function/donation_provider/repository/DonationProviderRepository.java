package com.github.olegagrus.donation_manager_server.function.donation_provider.repository;

import com.github.olegagrus.donation_manager_server.model.DonationProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationProviderRepository extends JpaRepository<DonationProvider, Long> {

    DonationProvider findByName(String name);

}
