package com.github.olegagrus.donation_manager_server.configuration.mapper;

import com.github.olegagrus.donation_manager_server.dto.UserDto;
import com.github.olegagrus.donation_manager_server.entity.Donation;
import com.github.olegagrus.donation_manager_server.entity.StreamLabsDonation;
import com.github.olegagrus.donation_manager_server.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureMapper(modelMapper.getConfiguration());
        configureMappings(modelMapper);
        return modelMapper;
    }

    private void configureMapper(org.modelmapper.config.Configuration configuration) {
        configuration
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    private void configureMappings(ModelMapper modelMapper) {
        StreamLabsDonationAndDonation(modelMapper);
        UserAndUserDto(modelMapper);
    }

    private void StreamLabsDonationAndDonation(ModelMapper modelMapper) {
        modelMapper.typeMap(StreamLabsDonation.class, Donation.class).addMappings(new PropertyMap<StreamLabsDonation, Donation>() {
            @Override
            protected void configure() {
                map().setId(source.getDonation_id());
                map().setUsername(source.getName());
            }
        });
        modelMapper.typeMap(Donation.class, StreamLabsDonation.class).addMappings(new PropertyMap<Donation, StreamLabsDonation>() {
            @Override
            protected void configure() {
                map().setDonation_id(source.getId());
                map().setName(source.getUsername());
            }
        });
    }

    private void UserAndUserDto(ModelMapper modelMapper) {
        modelMapper.typeMap(User.class, UserDto.class).addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                map().setPassword("");
            }
        });
    }

}
