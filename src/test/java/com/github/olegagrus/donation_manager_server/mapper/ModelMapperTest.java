package com.github.olegagrus.donation_manager_server.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olegagrus.donation_manager_server.configuration.mapper.ModelMapperConfiguration;
import com.github.olegagrus.donation_manager_server.dto.UserDto;
import com.github.olegagrus.donation_manager_server.entity.Donation;
import com.github.olegagrus.donation_manager_server.entity.StreamLabsDonation;
import static org.junit.jupiter.api.Assertions.*;

import com.github.olegagrus.donation_manager_server.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;

public class ModelMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapperConfiguration().modelMapper();
    }

    /*@Test
    public void test() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"data\":[{\"donation_id\":128648459,\"created_at\":1586539380,\"currency\":\"USD\",\"amount\":\"15.0000000000\",\"name\":\"donator2\",\"message\":\"Take my money\",\"email\":\"u6K5uMAlaf\"},{\"donation_id\":128648435,\"created_at\":1586539380,\"currency\":\"USD\",\"amount\":\"10.0000000000\",\"name\":\"donator1\",\"message\":\"Hello\",\"email\":\"jOwnNilu4p\"}]}";

        System.out.println(Arrays.toString(mapper.readValue(mapper.readTree(json).get("data").toString(), StreamLabsDonation[].class)));

    }*/

    @Test
    public void StreamLabsDonationToDonationTest() {

        StreamLabsDonation streamLabsDonation = StreamLabsDonation.builder()
                .donation_id(12345)
                .amount(99.99)
                .created_at(123456)
                .currency("UAH")
                .email("some_email@gmail.com")
                .message("some text")
                .name("user")
                .build();

        Donation expectedDonation = Donation.builder()
                .id(12345)
                .amount(99.99)
                .created_at(123456)
                .currency("UAH")
                .message("some text")
                .username("user")
                .build();

        Donation actualDonation = modelMapper.map(streamLabsDonation, Donation.class);

        assertEquals(expectedDonation, actualDonation);
    }

    @Test
    public void DonationToStreamLabsDonationTest() {

        StreamLabsDonation expectedStreamLabsDonation = StreamLabsDonation.builder()
                .donation_id(12345)
                .amount(99.99)
                .created_at(123456)
                .currency("UAH")
                .email(null)
                .message("some text")
                .name("user")
                .build();

        Donation donation = Donation.builder()
                .id(12345)
                .amount(99.99)
                .created_at(123456)
                .currency("UAH")
                .message("some text")
                .username("user")
                .build();

        StreamLabsDonation actualStreamLabsDonation = modelMapper.map(donation, StreamLabsDonation.class);

        assertEquals(expectedStreamLabsDonation, actualStreamLabsDonation);
    }

    @Test
    public void UserDtoToUserTest() {

        User expectedUser = User.builder()
                .id(1)
                .email("email")
                .fullName("fullname")
                .password("password")
                .birthDate(LocalDate.of(1999, 12, 29))
                .build();

        UserDto userDto = UserDto.builder()
                .id(1)
                .email("email")
                .fullName("fullname")
                .password("password")
                .birthDate(LocalDate.of(1999, 12, 29))
                .build();

        User actualUser = modelMapper.map(userDto, User.class);

        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getBirthDate(), actualUser.getBirthDate());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getFullName(), actualUser.getFullName());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());

    }

    @Test
    public void UserToUserDtoTest() {

        UserDto expectedUserDto = UserDto.builder()
                .id(1)
                .email("email")
                .fullName("fullname")
                .password("")
                .birthDate(LocalDate.of(1999, 12, 29))
                .build();

        User user = User.builder()
                .id(1)
                .email("email")
                .fullName("fullname")
                .password("password")
                .birthDate(LocalDate.of(1999, 12, 29))
                .build();

        UserDto actualUserDto = modelMapper.map(user, UserDto.class);

        assertEquals(expectedUserDto.getId(), actualUserDto.getId());
        assertEquals(expectedUserDto.getBirthDate(), actualUserDto.getBirthDate());
        assertEquals(expectedUserDto.getEmail(), actualUserDto.getEmail());
        assertEquals(expectedUserDto.getFullName(), actualUserDto.getFullName());
        assertEquals(expectedUserDto.getPassword(), actualUserDto.getPassword());

    }

}
