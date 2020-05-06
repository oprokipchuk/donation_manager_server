package com.github.olegagrus.donation_manager_server.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"name", "authUrl", "tokenUrl", "donationsUrl"})
@Table(name = "donation_provider")
public class DonationProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length = 60)
    private String name;

    @Column(length = 100)
    private String authUrl;

    @Column(length = 100)
    private String tokenUrl;

    @Column(length = 100)
    private String donationsUrl;

}
