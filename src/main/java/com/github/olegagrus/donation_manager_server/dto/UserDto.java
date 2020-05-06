package com.github.olegagrus.donation_manager_server.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"email", "fullName", "birthDate", "password"})
public class UserDto {

    private long id;

    private String email;

    private String fullName;

    private LocalDate birthDate;

    private String password;
}
