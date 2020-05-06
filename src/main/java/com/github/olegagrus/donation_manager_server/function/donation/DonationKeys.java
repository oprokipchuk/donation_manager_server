package com.github.olegagrus.donation_manager_server.function.donation;

import com.github.olegagrus.donation_manager_server.entity.StreamLabsDonation;

public interface DonationKeys {

    String GET_DONATIONS_PARAMETER_ACCESS_TOKEN = "access_token";

    Class<StreamLabsDonation[]> PROVIDER_STREAMLABS_ARRAY = StreamLabsDonation[].class;

}
