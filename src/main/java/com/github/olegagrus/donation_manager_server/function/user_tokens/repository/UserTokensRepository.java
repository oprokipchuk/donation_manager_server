package com.github.olegagrus.donation_manager_server.function.user_tokens.repository;

import com.github.olegagrus.donation_manager_server.model.DonationProvider;
import com.github.olegagrus.donation_manager_server.model.User;
import com.github.olegagrus.donation_manager_server.model.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {

    Optional<UserTokens> findByUserAndDonationProvider(User user, DonationProvider donationProvider);

    List<UserTokens> findAllByUser(User user);

}
